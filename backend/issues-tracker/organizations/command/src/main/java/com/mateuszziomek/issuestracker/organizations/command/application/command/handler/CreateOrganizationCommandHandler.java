package com.mateuszziomek.issuestracker.organizations.command.application.command.handler;

import com.mateuszziomek.issuestracker.organizations.command.domain.organization.OrganizationOwner;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.command.CommandHandler;
import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.issuestracker.organizations.command.application.command.CreateOrganizationCommand;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.Organization;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateOrganizationCommandHandler implements CommandHandler<CreateOrganizationCommand> {
    private final EventSourcingHandler<Organization> eventSourcingHandler;

    @Override
    public void handle(CreateOrganizationCommand command) {
        var organization = Organization.create(
                command.getOrganizationId(),
                new OrganizationOwner(command.getOrganizationOwnerId()),
                command.getOrganizationName()
        );

        eventSourcingHandler.save(organization);
    }
}
