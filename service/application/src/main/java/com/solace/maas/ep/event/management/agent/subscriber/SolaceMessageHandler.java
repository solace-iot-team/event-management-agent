package com.solace.maas.ep.event.management.agent.subscriber;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.solace.maas.ep.event.management.agent.plugin.constants.RouteConstants;
import com.solace.maas.ep.event.management.agent.plugin.mop.EnumDeserializer;
import com.solace.maas.ep.event.management.agent.plugin.mop.MOPConstants;
import com.solace.maas.ep.event.management.agent.plugin.mop.MOPMessage;
import com.solace.maas.ep.event.management.agent.plugin.mop.MOPMessageType;
import com.solace.maas.ep.event.management.agent.plugin.mop.MOPProtocol;
import com.solace.maas.ep.event.management.agent.plugin.mop.MOPSvcType;
import com.solace.maas.ep.event.management.agent.plugin.mop.MOPUHFlag;
import com.solace.maas.ep.event.management.agent.util.string.ParseClassName;
import com.solace.messaging.receiver.InboundMessage;
import com.solace.messaging.receiver.MessageReceiver;
import lombok.extern.slf4j.Slf4j;
import org.jboss.logging.MDC;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class SolaceMessageHandler<T extends MOPMessage> implements MessageReceiver.MessageHandler {

    private static final SimpleModule module = new SimpleModule();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final Map<String, JavaType> cachedJSONDecoders = new HashMap();

    static {
        // Register all the enums we expect to cross versions and allow them to be mapped to null instead of throwing an exception if unknown
        module.addDeserializer(MOPMessageType.class, new EnumDeserializer<>(MOPMessageType.class));
        module.addDeserializer(MOPProtocol.class, new EnumDeserializer<>(MOPProtocol.class));
        module.addDeserializer(MOPSvcType.class, new EnumDeserializer<>(MOPSvcType.class));
        module.addDeserializer(MOPUHFlag.class, new EnumDeserializer<>(MOPUHFlag.class));

        objectMapper.registerModule(module);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    private final String topicString;

    public SolaceMessageHandler(String topicString, SolaceSubscriber solaceSubscriber) {
        this.topicString = topicString;
        solaceSubscriber.registerMessageHandler(this);
    }

    public String getTopicString() {
        return topicString;
    }

    @Override
    public void onMessage(InboundMessage inboundMessage) {
        String messageAsString = inboundMessage.getPayloadAsString();
        String mopMessageDecoder= inboundMessage.getProperty(MOPConstants.MOP_MSG_META_DECODER);
        try {
            JavaType messageClass = cachedJSONDecoders.get(mopMessageDecoder);
            T message = null;

            if (messageClass == null) {
                messageClass = getMessageDecoderJavaType(mopMessageDecoder);
                cachedJSONDecoders.put(mopMessageDecoder, messageClass);
            }

            String receivedClassName = messageClass.getRawClass().getSimpleName();

            if ("ScanCommandMessage".equals(receivedClassName) || "ScanDataImportMessage".equals(receivedClassName)) {
                Map<String, Object> map = objectMapper.readValue(messageAsString, Map.class);
                String scanId = (String) map.get("scanId");
                String traceId = (String) map.get("traceId");
                String actorId = (String) map.get("actorId");
                String messagingServiceId = (String) map.get("messagingServiceId");

                MDC.clear();
                MDC.put(RouteConstants.SCAN_ID, scanId);
                MDC.put(RouteConstants.TRACE_ID, traceId);
                MDC.put(RouteConstants.ACTOR_ID, actorId);
                MDC.put(RouteConstants.MESSAGING_SERVICE_ID, messagingServiceId);
            }

            message = (T) objectMapper.readValue(messageAsString, messageClass);
            log.trace("onMessage: {}\n{}", messageClass, messageAsString);
            log.trace("onMessage: {} {}", inboundMessage.getDestinationName(), message);
            receiveMessage(inboundMessage.getDestinationName(), message);
        } catch (JsonProcessingException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static JavaType getMessageDecoderJavaType(String mopMessageDecoderName) throws ClassNotFoundException {
        JavaType messageClass;
        if (mopMessageDecoderName.contains("<") && mopMessageDecoderName.contains(">")){
            Class parametrized = ParseClassName.getClass(mopMessageDecoderName);
            Class[] parameterClasses = ParseClassName.getParameterizedTypes(mopMessageDecoderName);
            messageClass = objectMapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
        } else {
            messageClass = objectMapper.getTypeFactory().constructFromCanonical(mopMessageDecoderName);
        }
        return messageClass;
    }


    public abstract void receiveMessage(String destinationName, T message);
}
