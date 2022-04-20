package org.example.issuestracker.issues.command.infrastructure.command;

import org.example.cqrs.command.dispatcher.CommandDispatcher;
import org.example.cqrs.command.dispatcher.DefaultCommandDispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IssueCommandConfiguration {
    @Bean
    public CommandDispatcher commandDispatcher() {
        return new DefaultCommandDispatcher();
    }
}