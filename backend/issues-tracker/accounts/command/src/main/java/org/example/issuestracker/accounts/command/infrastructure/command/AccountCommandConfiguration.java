package org.example.issuestracker.accounts.command.infrastructure.command;

import org.example.cqrs.command.dispatcher.CommandDispatcher;
import org.example.cqrs.command.dispatcher.DefaultCommandDispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountCommandConfiguration {
    @Bean
    public CommandDispatcher commandDispatcher() {
        return new DefaultCommandDispatcher();
    }
}
