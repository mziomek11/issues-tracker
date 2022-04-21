package org.example.issuestracker.organizations.command.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.sourcinghandler.EventSourcingHandler;
import org.example.issuestracker.organizations.command.application.command.CreateOrganizationProjectCommand;
import org.example.issuestracker.organizations.command.domain.organization.Organization;
import org.example.issuestracker.organizations.command.domain.organization.OrganizationOwner;
import org.example.issuestracker.organizations.command.domain.organization.exception.OrganizationNotFoundException;
import org.example.issuestracker.organizations.command.domain.organization.exception.OrganizationOwnerNotValidException;
import org.example.issuestracker.organizations.command.domain.project.Project;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateOrganizationProjectCommandHandler implements CommandHandler<CreateOrganizationProjectCommand> {
    private final EventSourcingHandler<Organization> eventSourcingHandler;

    /**
     * @throws OrganizationNotFoundException if organization with given id does not exist
     * @throws OrganizationOwnerNotValidException see {@link Organization#addProject(OrganizationOwner, Project)}
     */
    @Override
    public void handle(CreateOrganizationProjectCommand command) {
        var organization = eventSourcingHandler
                .getById(command.getOrganizationId())
                .orElseThrow(() -> new OrganizationNotFoundException(command.getOrganizationId()));

        var organizationOwner = new OrganizationOwner(command.getOrganizationOwnerId());
        var project = new Project(command.getProjectId(), command.getProjectName());

        organization.addProject(organizationOwner, project);
        eventSourcingHandler.save(organization);
    }
}
