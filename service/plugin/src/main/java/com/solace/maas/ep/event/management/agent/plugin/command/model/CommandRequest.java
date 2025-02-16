package com.solace.maas.ep.event.management.agent.plugin.command.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommandRequest {
    private String correlationId;
    private String context;
    private String serviceId;
    private List<CommandBundle> commandBundles;
}
