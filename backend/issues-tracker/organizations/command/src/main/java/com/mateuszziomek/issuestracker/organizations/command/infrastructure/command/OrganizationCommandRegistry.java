package com.mateuszziomek.issuestracker.organizations.command.infrastructure.command;

import com.mateuszziomek.cqrs.command.dispatcher.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.issuestracker.organizations.command.application.command.CreateOrganizationCommand;
import com.mateuszziomek.issuestracker.organizations.command.application.command.CreateOrganizationProjectCommand;
import com.mateuszziomek.issuestracker.organizations.command.application.command.InviteOrganizationMemberCommand;
import com.mateuszziomek.issuestracker.organizations.command.application.command.JoinOrganizationMemberCommand;
import com.mateuszziomek.issuestracker.organizations.command.application.command.handler.CreateOrganizationCommandHandler;
import com.mateuszziomek.issuestracker.organizations.command.application.command.handler.CreateOrganizationProjectCommandHandler;
import com.mateuszziomek.issuestracker.organizations.command.application.command.handler.InviteOrganizationMemberCommandHandler;
import com.mateuszziomek.issuestracker.organizations.command.application.command.handler.JoinOrganizationMemberCommandHandler;
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
