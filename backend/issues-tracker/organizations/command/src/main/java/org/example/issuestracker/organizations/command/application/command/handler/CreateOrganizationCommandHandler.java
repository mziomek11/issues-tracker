package org.example.issuestracker.organizations.command.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.sourcinghandler.EventSourcingHandler;
import org.example.issuestracker.organizations.command.application.command.CreateOrganizationCommand;
import org.example.issuestracker.organizations.command.domain.organization.Organization;
import org.example.issuestracker.organizations.command.domain.organization.OrganizationOwner;
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
