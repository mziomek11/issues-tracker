package com.mateuszziomek.issuestracker.organizations.command.infrastructure.command;

import com.mateuszziomek.cqrs.command.dispatcher.CommandDispatcher;
import com.mateuszziomek.cqrs.command.dispatcher.DefaultCommandDispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrganizationCommandConfiguration {
    @Bean
    public CommandDispatcher commandDispatcher() {
        return new DefaultCommandDispatcher();
    }
}
