package com.solace.maas.ep.event.management.agent.processor;

import com.solace.maas.ep.event.management.agent.repository.model.manualimport.ManualImportFilesEntity;
import com.solace.maas.ep.event.management.agent.scanManager.model.MetaInfFileDetailsBO;
import com.solace.maas.ep.event.management.agent.service.ManualImportFilesService;
import com.solace.maas.ep.event.management.agent.util.IDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.solace.maas.ep.event.management.agent.plugin.constants.RouteConstants.EVENT_MANAGEMENT_ID;
import static com.solace.maas.ep.event.management.agent.plugin.constants.RouteConstants.IMPORT_ID;
import static com.solace.maas.ep.event.management.agent.plugin.constants.RouteConstants.SCAN_ID;
import static com.solace.maas.ep.event.management.agent.plugin.constants.RouteConstants.SCHEDULE_ID;

@Slf4j
@Component
@ConditionalOnProperty(name = "event-portal.gateway.messaging.standalone", havingValue = "false")
public class ScanDataImportPersistFilePathsProcessor implements Processor {

    private final ManualImportFilesService manualImportFilesService;
    private final IDGenerator idGenerator;

    public ScanDataImportPersistFilePathsProcessor(ManualImportFilesService manualImportFilesService,
                                                   IDGenerator idGenerator) {
        this.manualImportFilesService = manualImportFilesService;
        this.idGenerator = idGenerator;
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        List<MetaInfFileDetailsBO> files = (List<MetaInfFileDetailsBO>) exchange.getIn().getBody();

        Map<String, Object> properties = exchange.getIn().getHeaders();

        String scanId = (String) properties.get(SCAN_ID);
        String scheduleId = (String) properties.get(SCHEDULE_ID);
        String emaId = (String) properties.get(EVENT_MANAGEMENT_ID);
        String importId = (String) exchange.getProperty(IMPORT_ID);

        List<ManualImportFilesEntity> manualImportFilesEntityList = files.stream()
                .map(file -> ManualImportFilesEntity.builder()
                        .id(idGenerator.generateRandomUniqueId())
                        .fileName(file.getFileName())
                        .dataEntityType(file.getDataEntityType())
                        .importId(importId)
                        .scheduleId(scheduleId)
                        .emaId(emaId)
                        .scanId(scanId)
                        .build())
                .collect(Collectors.toList());
        manualImportFilesService.saveAll(manualImportFilesEntityList);
        log.debug("saved manualImportFilesEntityList: {}", manualImportFilesEntityList);
    }
}
