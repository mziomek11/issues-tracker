package org.example.issuestracker.organizations.command.infrastructure.command;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.dispatcher.CommandDispatcher;
import org.example.issuestracker.organizations.command.application.command.CreateOrganizationCommand;
import org.example.issuestracker.organizations.command.application.command.CreateOrganizationProjectCommand;
import org.example.issuestracker.organizations.command.application.command.InviteOrganizationMemberCommand;
import org.example.issuestracker.organizations.command.application.command.JoinOrganizationMemberCommand;
import org.example.issuestracker.organizations.command.application.command.handler.CreateOrganizationCommandHandler;
import org.example.issuestracker.organizations.command.application.command.handler.CreateOrganizationProjectCommandHandler;
import org.example.issuestracker.organizations.command.application.command.handler.InviteOrganizationMemberCommandHandler;
import org.example.issuestracker.organizations.command.application.command.handler.JoinOrganizationMemberCommandHandler;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class OrganizationCommandRegistry {
    private final CommandDispatcher commandDispatcher;
    private final CreateOrganizationCommandHandler createOrganizationCommandHandler;
    private final CreateOrganizationProjectCommandHandler createOrganizationProjectCommandHandler;
    private final InviteOrganizationMemberCommandHandler inviteOrganizationMemberCommandHandler;
    private final JoinOrganizationMemberCommandHandler joinOrganizationMemberCommandHandler;

    @PostConstruct
    public void registerHandlers() {
        commandDispatcher.registerHandler(CreateOrganizationCommand.class, createOrganizationCommandHandler);
        commandDispatcher.registerHandler(CreateOrganizationProjectCommand.class, createOrganizationProjectCommandHandler);
        commandDispatcher.registerHandler(InviteOrganizationMemberCommand.class, inviteOrganizationMemberCommandHandler);
        commandDispatcher.registerHandler(JoinOrganizationMemberCommand.class, joinOrganizationMemberCommandHandler);
    }
}
