package com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1;

import com.mateuszziomek.issuestracker.issues.command.application.command.*;
import com.mateuszziomek.issuestracker.issues.command.application.command.handler.*;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationServiceUnavailableException;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.exception.CommentContentSetException;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.exception.CommentHiddenException;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.exception.CommentNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.exception.CommentWithIdExistsException;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.exception.*;
import com.mateuszziomek.issuestracker.issues.command.domain.vote.exception.VoteAlreadyExistsException;
import com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1.dto.*;
import com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1.mapper.*;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.command.dispatcher.CommandDispatcher;
import com.mateuszziomek.issuestracker.issues.command.application.command.*;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationProjectNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.command.handler.*;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.exception.*;
import com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1.dto.*;
import com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1.mapper.*;
import com.mateuszziomek.rest.v1.RestValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/issue-management")
@RequiredArgsConstructor
class IssueRestController {
    private final CommandDispatcher commandDispatcher;

    /**
     * @throws OrganizationMemberNotFoundException see {@link OpenIssueCommandHandler#handle(OpenIssueCommand)}
     * @throws OrganizationNotFoundException see {@link OpenIssueCommandHandler#handle(OpenIssueCommand)}
     * @throws OrganizationProjectNotFoundException see {@link OpenIssueCommandHandler#handle(OpenIssueCommand)}
     * @throws OrganizationServiceUnavailableException see {@link OpenIssueCommandHandler#handle(OpenIssueCommand)}
     * @throws RestValidationException see {@link OpenIssueDtoMapper#toCommand(UUID, IssueOrganizationDetails, OpenIssueDto)}
     */
    @PostMapping("/organizations/{organizationId}/projects/{projectId}/issues")
    public ResponseEntity<UUID> openIssue(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId,
            @RequestBody OpenIssueDto openIssueDto
    ) {
        var issueId = UUID.randomUUID();
        // @TODO pass user id from header
        var openIssueCommand = OpenIssueDtoMapper.toCommand(
                issueId,
                IssueOrganizationDetails.fromUUID(organizationId, projectId, UUID.randomUUID()),
                openIssueDto
        );

        commandDispatcher.dispatch(openIssueCommand);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(issueId);
    }

    /**
     * @throws IssueClosedException see {@link CloseIssueCommandHandler#handle(CloseIssueCommand)}
     * @throws IssueNotFoundException see {@link CloseIssueCommandHandler#handle(CloseIssueCommand)}
     * @throws OrganizationMemberNotFoundException see {@link CloseIssueCommandHandler#handle(CloseIssueCommand)}
     * @throws OrganizationNotFoundException see {@link CloseIssueCommandHandler#handle(CloseIssueCommand)}
     * @throws OrganizationProjectNotFoundException see {@link CloseIssueCommandHandler#handle(CloseIssueCommand)}
     * @throws OrganizationServiceUnavailableException see {@link CloseIssueCommandHandler#handle(CloseIssueCommand)}
     * @throws RestValidationException see {@link CloseIssueDtoMapper#toCommand(UUID, IssueOrganizationDetails)}
     */
    @DeleteMapping("/organizations/{organizationId}/projects/{projectId}/issues/{issueId}")
    public ResponseEntity closeIssue(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId,
            @PathVariable UUID issueId
    ) {
        // @TODO pass user id from header
        var closeIssueCommand = CloseIssueDtoMapper.toCommand(
                issueId,
                IssueOrganizationDetails.fromUUID(organizationId, projectId, UUID.randomUUID())
        );

        commandDispatcher.dispatch(closeIssueCommand);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * @throws IssueClosedException see {@link RenameIssueCommandHandler#handle(RenameIssueCommand)}
     * @throws IssueNameSetException see {@link RenameIssueCommandHandler#handle(RenameIssueCommand)}
     * @throws IssueNotFoundException see {@link RenameIssueCommandHandler#handle(RenameIssueCommand)}
     * @throws OrganizationMemberNotFoundException see {@link RenameIssueCommandHandler#handle(RenameIssueCommand)}
     * @throws OrganizationNotFoundException see {@link RenameIssueCommandHandler#handle(RenameIssueCommand)}
     * @throws OrganizationProjectNotFoundException see {@link RenameIssueCommandHandler#handle(RenameIssueCommand)}
     * @throws OrganizationServiceUnavailableException see {@link RenameIssueCommandHandler#handle(RenameIssueCommand)}
     * @throws RestValidationException see {@link RenameIssueDtoMapper#toCommand(UUID, IssueOrganizationDetails, RenameIssueDto)}
     */
    @PatchMapping("/organizations/{organizationId}/projects/{projectId}/issues/{issueId}/name")
    public ResponseEntity renameIssue(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId,
            @PathVariable UUID issueId,
            @RequestBody RenameIssueDto renameIssueDto
    ) {
        // @TODO pass user id from header
        var renameIssueCommand = RenameIssueDtoMapper.toCommand(
                issueId,
                IssueOrganizationDetails.fromUUID(organizationId, projectId, UUID.randomUUID()),
                renameIssueDto
        );

        commandDispatcher.dispatch(renameIssueCommand);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * @throws IssueClosedException see {@link ChangeIssueTypeCommandHandler#handle(ChangeIssueTypeCommand)}
     * @throws IssueNotFoundException see {@link ChangeIssueTypeCommandHandler#handle(ChangeIssueTypeCommand)}
     * @throws IssueTypeSetException see {@link ChangeIssueTypeCommandHandler#handle(ChangeIssueTypeCommand)}
     * @throws OrganizationMemberNotFoundException see {@link ChangeIssueTypeCommandHandler#handle(ChangeIssueTypeCommand)}
     * @throws OrganizationNotFoundException see {@link ChangeIssueTypeCommandHandler#handle(ChangeIssueTypeCommand)}
     * @throws OrganizationProjectNotFoundException see {@link ChangeIssueTypeCommandHandler#handle(ChangeIssueTypeCommand)}
     * @throws OrganizationServiceUnavailableException see {@link ChangeIssueTypeCommandHandler#handle(ChangeIssueTypeCommand)}
     * @throws RestValidationException see {@link ChangeIssueTypeDtoMapper#toCommand(UUID, IssueOrganizationDetails, ChangeIssueTypeDto)}
     */
    @PatchMapping("/organizations/{organizationId}/projects/{projectId}/issues/{issueId}/type")
    public ResponseEntity changeIssueType(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId,
            @PathVariable UUID issueId,
            @RequestBody ChangeIssueTypeDto changeIssueTypeDto
    ) {
        // @TODO pass user id from header
        var changeIssueTypeCommand = ChangeIssueTypeDtoMapper.toCommand(
                issueId,
                IssueOrganizationDetails.fromUUID(organizationId, projectId, UUID.randomUUID()),
                changeIssueTypeDto
        );

        commandDispatcher.dispatch(changeIssueTypeCommand);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * @throws IssueClosedException see {@link ChangeIssueContentCommandHandler#handle(ChangeIssueContentCommand)}
     * @throws IssueContentSetException see {@link ChangeIssueContentCommandHandler#handle(ChangeIssueContentCommand)}
     * @throws IssueNotFoundException see {@link ChangeIssueContentCommandHandler#handle(ChangeIssueContentCommand)}
     * @throws OrganizationMemberNotFoundException see {@link ChangeIssueContentCommandHandler#handle(ChangeIssueContentCommand)}
     * @throws OrganizationNotFoundException see {@link ChangeIssueContentCommandHandler#handle(ChangeIssueContentCommand)}
     * @throws OrganizationProjectNotFoundException see {@link ChangeIssueContentCommandHandler#handle(ChangeIssueContentCommand)}
     * @throws OrganizationServiceUnavailableException see {@link ChangeIssueContentCommandHandler#handle(ChangeIssueContentCommand)}
     * @throws RestValidationException see {@link ChangeIssueContentDtoMapper#toCommand(UUID, IssueOrganizationDetails, ChangeIssueContentDto)}
     */
    @PatchMapping("/organizations/{organizationId}/projects/{projectId}/issues/{issueId}/content")
    public ResponseEntity changeIssueContent(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId,
            @PathVariable UUID issueId,
            @RequestBody ChangeIssueContentDto changeIssueContentDto
    ) {
        // @TODO pass user id from header
        var changeIssueContentCommand = ChangeIssueContentDtoMapper.toCommand(
                issueId,
                IssueOrganizationDetails.fromUUID(organizationId, projectId, UUID.randomUUID()),
                changeIssueContentDto
        );

        commandDispatcher.dispatch(changeIssueContentCommand);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * @throws IssueClosedException see {@link VoteIssueCommandHandler#handle(VoteIssueCommand)}
     * @throws IssueNotFoundException see {@link VoteIssueCommandHandler#handle(VoteIssueCommand)}
     * @throws OrganizationMemberNotFoundException see {@link VoteIssueCommandHandler#handle(VoteIssueCommand)}
     * @throws OrganizationNotFoundException see {@link VoteIssueCommandHandler#handle(VoteIssueCommand)}
     * @throws OrganizationProjectNotFoundException see {@link VoteIssueCommandHandler#handle(VoteIssueCommand)}
     * @throws OrganizationServiceUnavailableException see {@link VoteIssueCommandHandler#handle(VoteIssueCommand)}
     * @throws RestValidationException see {@link VoteIssueDtoMapper#toCommand(UUID, IssueOrganizationDetails, VoteIssueDto)}
     * @throws VoteAlreadyExistsException see {@link VoteIssueCommandHandler#handle(VoteIssueCommand)}
     */
    @PostMapping("/organizations/{organizationId}/projects/{projectId}/issues/{issueId}/votes")
    public ResponseEntity voteIssue(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId,
            @PathVariable UUID issueId,
            @RequestBody VoteIssueDto voteIssueDto
    ) {
        // @TODO pass user id from header
        var voteIssueCommand = VoteIssueDtoMapper.toCommand(
                issueId,
                IssueOrganizationDetails.fromUUID(organizationId, projectId, UUID.randomUUID()),
                voteIssueDto
        );

        commandDispatcher.dispatch(voteIssueCommand);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * @throws CommentWithIdExistsException see {@link CommentIssueCommandHandler#handle(CommentIssueCommand)}
     * @throws IssueClosedException see {@link CommentIssueCommandHandler#handle(CommentIssueCommand)}
     * @throws IssueNotFoundException see {@link CommentIssueCommandHandler#handle(CommentIssueCommand)}
     * @throws OrganizationMemberNotFoundException see {@link CommentIssueCommandHandler#handle(CommentIssueCommand)}
     * @throws OrganizationNotFoundException see {@link CommentIssueCommandHandler#handle(CommentIssueCommand)}
     * @throws OrganizationProjectNotFoundException see {@link CommentIssueCommandHandler#handle(CommentIssueCommand)}
     * @throws OrganizationServiceUnavailableException see {@link CommentIssueCommandHandler#handle(CommentIssueCommand)}
     * @throws RestValidationException see {@link CommentIssueDtoMapper#toCommand(UUID, UUID, IssueOrganizationDetails, CommentIssueDto)}
     */
    @PostMapping("/organizations/{organizationId}/projects/{projectId}/issues/{issueId}/comments")
    public ResponseEntity<UUID> commentIssue(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId,
            @PathVariable UUID issueId,
            @RequestBody CommentIssueDto commentIssueDto
    ) {
        var commentId = UUID.randomUUID();
        // @TODO pass user id from header
        var commentIssueCommand = CommentIssueDtoMapper.toCommand(
                issueId,
                commentId,
                IssueOrganizationDetails.fromUUID(organizationId, projectId, UUID.randomUUID()),
                commentIssueDto
        );

        commandDispatcher.dispatch(commentIssueCommand);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentId);
    }

    /**
     * @throws CommentHiddenException see {@link HideIssueCommentCommandHandler#handle(HideIssueCommentCommand)}
     * @throws CommentNotFoundException see {@link HideIssueCommentCommandHandler#handle(HideIssueCommentCommand)}
     * @throws IssueClosedException see {@link HideIssueCommentCommandHandler#handle(HideIssueCommentCommand)}
     * @throws IssueNotFoundException see {@link HideIssueCommentCommandHandler#handle(HideIssueCommentCommand)}
     * @throws OrganizationMemberNotFoundException see {@link HideIssueCommentCommandHandler#handle(HideIssueCommentCommand)}
     * @throws OrganizationNotFoundException see {@link HideIssueCommentCommandHandler#handle(HideIssueCommentCommand)}
     * @throws OrganizationProjectNotFoundException see {@link HideIssueCommentCommandHandler#handle(HideIssueCommentCommand)}
     * @throws OrganizationServiceUnavailableException see {@link HideIssueCommentCommandHandler#handle(HideIssueCommentCommand)}
     * @throws RestValidationException see {@link HideIssueCommentDtoMapper#toCommand(UUID, UUID, IssueOrganizationDetails)}
     */
    @DeleteMapping("/organizations/{organizationId}/projects/{projectId}/issues/{issueId}/comments/{commentId}")
    public ResponseEntity hideIssueComment(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId,
            @PathVariable UUID issueId,
            @PathVariable UUID commentId
    ) {
        // @TODO pass user id from header
        var hideIssueCommentCommand = HideIssueCommentDtoMapper.toCommand(
                issueId,
                commentId,
                IssueOrganizationDetails.fromUUID(organizationId, projectId, UUID.randomUUID())
        );

        commandDispatcher.dispatch(hideIssueCommentCommand);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * @throws CommentContentSetException see {@link ChangeIssueCommentContentCommandHandler#handle(ChangeIssueCommentContentCommand)}
     * @throws CommentNotFoundException see {@link ChangeIssueCommentContentCommandHandler#handle(ChangeIssueCommentContentCommand)}
     * @throws IssueClosedException see {@link ChangeIssueCommentContentCommandHandler#handle(ChangeIssueCommentContentCommand)}
     * @throws IssueNotFoundException see {@link ChangeIssueCommentContentCommandHandler#handle(ChangeIssueCommentContentCommand)}
     * @throws OrganizationMemberNotFoundException see {@link ChangeIssueCommentContentCommandHandler#handle(ChangeIssueCommentContentCommand)}
     * @throws OrganizationNotFoundException see {@link ChangeIssueCommentContentCommandHandler#handle(ChangeIssueCommentContentCommand)}
     * @throws OrganizationProjectNotFoundException see {@link ChangeIssueCommentContentCommandHandler#handle(ChangeIssueCommentContentCommand)}
     * @throws OrganizationServiceUnavailableException see {@link ChangeIssueCommentContentCommandHandler#handle(ChangeIssueCommentContentCommand)}
     * @throws RestValidationException see {@link ChangeIssueCommentContentDtoMapper#toCommand(UUID, UUID, IssueOrganizationDetails, ChangeIssueCommentContentDto)}
     */
    @DeleteMapping("/organizations/{organizationId}/projects/{projectId}/issues/{issueId}/comments/{commentId}/content")
    public ResponseEntity changeIssueCommentContent(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId,
            @PathVariable UUID issueId,
            @PathVariable UUID commentId,
            @RequestBody ChangeIssueCommentContentDto changeIssueCommentContentDto
    ) {
        // @TODO pass user id from header
        var changeIssueCommentContentCommand = ChangeIssueCommentContentDtoMapper.toCommand(
                issueId,
                commentId,
                IssueOrganizationDetails.fromUUID(organizationId, projectId, UUID.randomUUID()),
                changeIssueCommentContentDto
        );

        commandDispatcher.dispatch(changeIssueCommentContentCommand);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * @throws IssueClosedException see {@link VoteIssueCommandHandler#handle(VoteIssueCommand)}
     * @throws IssueNotFoundException see {@link VoteIssueCommandHandler#handle(VoteIssueCommand)}
     * @throws OrganizationMemberNotFoundException see {@link VoteIssueCommandHandler#handle(VoteIssueCommand)}
     * @throws OrganizationNotFoundException see {@link VoteIssueCommandHandler#handle(VoteIssueCommand)}
     * @throws OrganizationProjectNotFoundException see {@link VoteIssueCommandHandler#handle(VoteIssueCommand)}
     * @throws OrganizationServiceUnavailableException see {@link VoteIssueCommandHandler#handle(VoteIssueCommand)}
     * @throws RestValidationException see {@link VoteIssueCommentDtoMapper#toCommand(UUID, UUID, IssueOrganizationDetails, VoteIssueCommentDto)}
     * @throws VoteAlreadyExistsException see {@link VoteIssueCommandHandler#handle(VoteIssueCommand)}
     */
    @PostMapping("/organizations/{organizationId}/projects/{projectId}/issues/{issueId}/comments/{commentId}/votes")
    public ResponseEntity voteIssueComment(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId,
            @PathVariable UUID issueId,
            @PathVariable UUID commentId,
            @RequestBody VoteIssueCommentDto voteIssueCommentDto
    ) {
        // @TODO pass user id from header
        var voteIssueCommentCommand = VoteIssueCommentDtoMapper.toCommand(
                issueId,
                commentId,
                IssueOrganizationDetails.fromUUID(organizationId, projectId, UUID.randomUUID()),
                voteIssueCommentDto
        );

        commandDispatcher.dispatch(voteIssueCommentCommand);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
