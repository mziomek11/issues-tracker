package com.mateuszziomek.issuestracker.organizations.command.application.command.handler;

import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.OrganizationOwner;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.command.CommandHandler;
import com.mateuszziomek.issuestracker.organizations.command.application.command.CreateOrganizationProjectCommand;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.Organization;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.exception.OrganizationOwnerNotValidException;
import com.mateuszziomek.issuestracker.organizations.command.domain.project.Project;
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
