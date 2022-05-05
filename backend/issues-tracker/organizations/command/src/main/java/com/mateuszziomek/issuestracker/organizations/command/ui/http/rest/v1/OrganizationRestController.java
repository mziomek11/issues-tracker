package com.mateuszziomek.issuestracker.organizations.command.ui.http.rest.v1;

import com.mateuszziomek.issuestracker.organizations.command.application.gateway.member.exception.MemberServiceUnavailableException;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.exception.MemberAlreadyPresentException;
import com.mateuszziomek.issuestracker.organizations.command.ui.http.rest.v1.dto.CreateOrganizationDto;
import com.mateuszziomek.issuestracker.organizations.command.ui.http.rest.v1.dto.CreateOrganizationProjectDto;
import com.mateuszziomek.issuestracker.organizations.command.ui.http.rest.v1.dto.InviteOrganizationMemberDto;
import com.mateuszziomek.issuestracker.organizations.command.ui.http.rest.v1.mapper.CreateOrganizationDtoMapper;
import com.mateuszziomek.issuestracker.organizations.command.ui.http.rest.v1.mapper.CreateOrganizationProjectDtoMapper;
import com.mateuszziomek.issuestracker.organizations.command.ui.http.rest.v1.mapper.InviteOrganizationMemberDtoMapper;
import com.mateuszziomek.issuestracker.shared.infrastructure.security.SecurityHeaders;
import com.mateuszziomek.issuestracker.shared.readmodel.ObjectId;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.command.dispatcher.CommandDispatcher;
import com.mateuszziomek.issuestracker.organizations.command.application.command.CreateOrganizationProjectCommand;
import com.mateuszziomek.issuestracker.organizations.command.application.command.InviteOrganizationMemberCommand;
import com.mateuszziomek.issuestracker.organizations.command.application.command.JoinOrganizationMemberCommand;
import com.mateuszziomek.issuestracker.organizations.command.application.command.handler.CreateOrganizationProjectCommandHandler;
import com.mateuszziomek.issuestracker.organizations.command.application.command.handler.InviteOrganizationMemberCommandHandler;
import com.mateuszziomek.issuestracker.organizations.command.application.command.handler.JoinOrganizationMemberCommandHandler;
import com.mateuszziomek.issuestracker.organizations.command.application.gateway.member.exception.MemberNotFoundException;
import com.mateuszziomek.issuestracker.organizations.command.domain.invitation.exception.InvitationAlreadyPresentException;
import com.mateuszziomek.issuestracker.organizations.command.domain.invitation.exception.InvitationNotFoundException;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.exception.OrganizationOwnerNotValidException;
import com.mateuszziomek.rest.v1.RestValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organization-management")
@RequiredArgsConstructor
public class OrganizationRestController {
    private final CommandDispatcher commandDispatcher;

    /**
     * @throws RestValidationException see {@link CreateOrganizationDtoMapper#toCommand(UUID, UUID, CreateOrganizationDto)}
     */
    @PostMapping("/organizations")
    public ResponseEntity<ObjectId> createOrganization(
            @RequestHeader(SecurityHeaders.ISSUES_TRACKER_USER_ID) UUID userId,
            @RequestBody CreateOrganizationDto createOrganizationDto
    ) {
        var organizationId = UUID.randomUUID();
        var registerUserCommand = CreateOrganizationDtoMapper.toCommand(
                organizationId,
                userId,
                createOrganizationDto
        );

        commandDispatcher.dispatch(registerUserCommand);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ObjectId(organizationId));
    }

    /**
     * @throws InvitationAlreadyPresentException see {@link InviteOrganizationMemberCommandHandler#handle(InviteOrganizationMemberCommand)}
     * @throws MemberAlreadyPresentException see {@link InviteOrganizationMemberCommandHandler#handle(InviteOrganizationMemberCommand)}
     * @throws MemberNotFoundException see {@link InviteOrganizationMemberCommandHandler#handle(InviteOrganizationMemberCommand)}
     * @throws MemberServiceUnavailableException see {@link InviteOrganizationMemberCommandHandler#handle(InviteOrganizationMemberCommand)}
     * @throws OrganizationNotFoundException see {@link InviteOrganizationMemberCommandHandler#handle(InviteOrganizationMemberCommand)}
     * @throws OrganizationOwnerNotValidException see {@link InviteOrganizationMemberCommandHandler#handle(InviteOrganizationMemberCommand)}
     * @throws RestValidationException see {@link InviteOrganizationMemberDtoMapper#toCommand(UUID, UUID, InviteOrganizationMemberDto)}
     */
    @PostMapping("/organizations/{organizationId}/invitations")
    public ResponseEntity inviteOrganizationMember(
            @RequestHeader(SecurityHeaders.ISSUES_TRACKER_USER_ID) UUID userId,
            @PathVariable UUID organizationId,
            @RequestBody InviteOrganizationMemberDto inviteOrganizationMemberDto
    ) {
        var inviteOrganizationMemberCommand = InviteOrganizationMemberDtoMapper.toCommand(
                organizationId,
                userId,
                inviteOrganizationMemberDto
        );

        commandDispatcher.dispatch(inviteOrganizationMemberCommand);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    /**
     * @throws InvitationNotFoundException see {@link JoinOrganizationMemberCommandHandler#handle(JoinOrganizationMemberCommand)}
     * @throws OrganizationNotFoundException see {@link JoinOrganizationMemberCommandHandler#handle(JoinOrganizationMemberCommand)}
     */
    @PostMapping("/organizations/{organizationId}/members")
    public ResponseEntity joinOrganizationMember(
            @RequestHeader(SecurityHeaders.ISSUES_TRACKER_USER_ID) UUID userId,
            @PathVariable UUID organizationId
    ) {
        var inviteOrganizationMemberCommand = JoinOrganizationMemberCommand
                .builder()
                .organizationId(organizationId)
                .memberId(userId)
                .build();

        commandDispatcher.dispatch(inviteOrganizationMemberCommand);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * @throws OrganizationNotFoundException see {@link CreateOrganizationProjectCommandHandler#handle(CreateOrganizationProjectCommand)}
     * @throws OrganizationOwnerNotValidException see {@link CreateOrganizationProjectCommandHandler#handle(CreateOrganizationProjectCommand)}
     * @throws RestValidationException see {@link CreateOrganizationProjectDtoMapper#toCommand(UUID, UUID, UUID, CreateOrganizationProjectDto)}
     */
    @PostMapping("/organizations/{organizationId}/projects")
    public ResponseEntity<ObjectId> createOrganizationProject(
            @RequestHeader(SecurityHeaders.ISSUES_TRACKER_USER_ID) UUID userId,
            @PathVariable UUID organizationId,
            @RequestBody CreateOrganizationProjectDto createOrganizationProjectDto
    ) {
        var projectId = UUID.randomUUID();
        var registerUserCommand = CreateOrganizationProjectDtoMapper.toCommand(
                organizationId,
                projectId,
                userId,
                createOrganizationProjectDto
        );

        commandDispatcher.dispatch(registerUserCommand);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ObjectId(projectId));
    }
}
