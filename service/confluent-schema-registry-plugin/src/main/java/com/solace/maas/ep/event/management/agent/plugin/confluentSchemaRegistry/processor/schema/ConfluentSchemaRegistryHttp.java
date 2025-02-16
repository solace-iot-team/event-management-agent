package com.solace.maas.ep.event.management.agent.plugin.confluentSchemaRegistry.processor.schema;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solace.maas.ep.event.management.agent.plugin.confluentSchemaRegistry.exception.ClientException;
import com.solace.maas.ep.event.management.agent.plugin.confluentSchemaRegistry.processor.event.ConfluentSchemaRegistrySchemaEvent;
import com.solace.maas.ep.event.management.agent.plugin.jacoco.ExcludeFromJacocoGeneratedReport;
import com.solace.maas.ep.event.management.agent.plugin.util.UriUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@ExcludeFromJacocoGeneratedReport
@SuppressWarnings("CPD-START")
@Slf4j
@Getter
public class ConfluentSchemaRegistryHttp {
    private static final String GET_ALL_SCHEMAS = "/schemas";
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    public ConfluentSchemaRegistryHttp(HttpClient httpClient) {
        this.httpClient = httpClient;
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public List<ConfluentSchemaRegistrySchemaEvent> getSchemas() throws Exception {
        String response;
        response = getResponse(getUri(GET_ALL_SCHEMAS));
        return objectMapper.readValue(response, new TypeReference<>() {
        });
    }

    private URI getUri(String path) {
        return UriUtil.getURI(httpClient.getConnectionUrl() + path);
    }

    private String getResponse(URI uri) throws IOException {
        HttpGet getData = new HttpGet();
        getData.setURI(uri);
        try (CloseableHttpResponse response = httpClient.getWebClient().execute(getData)) {
            if (response.getStatusLine().getStatusCode() != HttpStatus.OK.value()) {
                log.error("Error response from the Confluent Schema registry {} returned with code {} and message '{}'",
                        httpClient.getConnectionUrl() + uri.getPath(),
                        response.getStatusLine().getStatusCode(),
                        response.getStatusLine().getReasonPhrase());
                throw new ClientException(String.format("Could not fulfill API call for path %s",
                        httpClient.getConnectionUrl() + uri.getPath()));
            }
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }
    }
}
