package com.mateuszziomek.issuestracker.users.command.infrastructure.command;

import com.mateuszziomek.cqrs.command.dispatcher.CommandDispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCommandConfiguration {
    @Bean
    public CommandDispatcher commandDispatcher() {
        return new CommandDispatcher();
    }
}
