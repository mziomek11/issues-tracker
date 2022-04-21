package org.example.issuestracker.issues.command.ui.http.rest.v1;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.dispatcher.CommandDispatcher;
import org.example.issuestracker.issues.command.application.command.*;
import org.example.issuestracker.issues.command.application.command.gateway.organization.exception.IssueCreatorIsNotMemberOfProjectException;
import org.example.issuestracker.issues.command.application.command.gateway.organization.exception.OrganizationNotFoundException;
import org.example.issuestracker.issues.command.application.command.gateway.organization.exception.ProjectNotFoundException;
import org.example.issuestracker.issues.command.application.command.handler.*;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentContentSetException;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentHiddenException;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentNotFoundException;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentWithIdExistsException;
import org.example.issuestracker.issues.command.domain.issue.exception.*;
import org.example.issuestracker.issues.command.domain.vote.exception.VoteAlreadyExistsException;
import org.example.issuestracker.issues.command.ui.http.rest.v1.dto.*;
import org.example.issuestracker.issues.command.ui.http.rest.v1.mapper.*;
import org.example.rest.v1.RestValidationException;
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
     * @throws IssueCreatorIsNotMemberOfProjectException see {@link OpenIssueCommandHandler#handle(OpenIssueCommand)}
     * @throws OrganizationNotFoundException see {@link OpenIssueCommandHandler#handle(OpenIssueCommand)}
     * @throws ProjectNotFoundException see {@link OpenIssueCommandHandler#handle(OpenIssueCommand)}
     * @throws RestValidationException see {@link OpenIssueDtoMapper#toCommand(UUID, UUID, UUID, UUID, OpenIssueDto)}
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
                organizationId,
                projectId,
                UUID.randomUUID(),
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
     * @throws RestValidationException see {@link CloseIssueDtoMapper#toCommand(UUID)}
     */
    @DeleteMapping("/organizations/{organizationId}/projects/{projectId}/issues/{issueId}")
    public ResponseEntity closeIssue(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId,
            @PathVariable UUID issueId
    ) {
        var closeIssueCommand = CloseIssueDtoMapper.toCommand(issueId);

        commandDispatcher.dispatch(closeIssueCommand);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * @throws IssueClosedException see {@link RenameIssueCommandHandler#handle(RenameIssueCommand)}
     * @throws IssueNameSetException see {@link RenameIssueCommandHandler#handle(RenameIssueCommand)}
     * @throws IssueNotFoundException see {@link RenameIssueCommandHandler#handle(RenameIssueCommand)}
     * @throws RestValidationException see {@link RenameIssueDtoMapper#toCommand(UUID, RenameIssueDto)}
     */
    @PatchMapping("/organizations/{organizationId}/projects/{projectId}/issues/{issueId}/name")
    public ResponseEntity renameIssue(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId,
            @PathVariable UUID issueId,
            @RequestBody RenameIssueDto renameIssueDto
    ) {
        var renameIssueCommand = RenameIssueDtoMapper.toCommand(issueId, renameIssueDto);

        commandDispatcher.dispatch(renameIssueCommand);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * @throws IssueClosedException see {@link ChangeIssueTypeCommandHandler#handle(ChangeIssueTypeCommand)}
     * @throws IssueNotFoundException see {@link ChangeIssueTypeCommandHandler#handle(ChangeIssueTypeCommand)}
     * @throws IssueTypeSetException see {@link ChangeIssueTypeCommandHandler#handle(ChangeIssueTypeCommand)}
     * @throws RestValidationException see {@link ChangeIssueTypeDtoMapper#toCommand(UUID, ChangeIssueTypeDto)}
     */
    @PatchMapping("/organizations/{organizationId}/projects/{projectId}/issues/{issueId}/type")
    public ResponseEntity changeIssueType(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId,
            @PathVariable UUID issueId,
            @RequestBody ChangeIssueTypeDto changeIssueTypeDto
    ) {
        var changeIssueTypeCommand = ChangeIssueTypeDtoMapper.toCommand(issueId, changeIssueTypeDto);

        commandDispatcher.dispatch(changeIssueTypeCommand);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * @throws IssueClosedException see {@link ChangeIssueContentCommandHandler#handle(ChangeIssueContentCommand)}
     * @throws IssueContentSetException see {@link ChangeIssueContentCommandHandler#handle(ChangeIssueContentCommand)}
     * @throws IssueNotFoundException see {@link ChangeIssueContentCommandHandler#handle(ChangeIssueContentCommand)}
     * @throws RestValidationException see {@link ChangeIssueContentDtoMapper#toCommand(UUID, ChangeIssueContentDto)}
     */
    @PatchMapping("/organizations/{organizationId}/projects/{projectId}/issues/{issueId}/content")
    public ResponseEntity changeIssueContent(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId,
            @PathVariable UUID issueId,
            @RequestBody ChangeIssueContentDto changeIssueContentDto
    ) {
        var changeIssueContentCommand = ChangeIssueContentDtoMapper.toCommand(issueId, changeIssueContentDto);

        commandDispatcher.dispatch(changeIssueContentCommand);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * @throws IssueClosedException see {@link VoteIssueCommandHandler#handle(VoteIssueCommand)}
     * @throws IssueNotFoundException see {@link VoteIssueCommandHandler#handle(VoteIssueCommand)}
     * @throws RestValidationException see {@link VoteIssueDtoMapper#toCommand(UUID, VoteIssueDto)}
     * @throws VoteAlreadyExistsException see {@link VoteIssueCommandHandler#handle(VoteIssueCommand)}
     */
    @PostMapping("/organizations/{organizationId}/projects/{projectId}/issues/{issueId}/votes")
    public ResponseEntity voteIssue(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId,
            @PathVariable UUID issueId,
            @RequestBody VoteIssueDto voteIssueDto
    ) {
        var voteIssueCommand = VoteIssueDtoMapper.toCommand(issueId, voteIssueDto);

        commandDispatcher.dispatch(voteIssueCommand);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * @throws CommentWithIdExistsException see {@link CommentIssueCommandHandler#handle(CommentIssueCommand)}
     * @throws IssueClosedException see {@link CommentIssueCommandHandler#handle(CommentIssueCommand)}
     * @throws IssueNotFoundException see {@link CommentIssueCommandHandler#handle(CommentIssueCommand)}
     * @throws RestValidationException see {@link CommentIssueDtoMapper#toCommand(UUID, UUID, CommentIssueDto)}
     */
    @PostMapping("/organizations/{organizationId}/projects/{projectId}/issues/{issueId}/comments")
    public ResponseEntity<UUID> commentIssue(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId,
            @PathVariable UUID issueId,
            @RequestBody CommentIssueDto commentIssueDto
    ) {
        var commentId = UUID.randomUUID();
        var commentIssueCommand = CommentIssueDtoMapper.toCommand(issueId, commentId, commentIssueDto);

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
     * @throws RestValidationException see {@link HideIssueCommentDtoMapper#toCommand(UUID, UUID)}
     */
    @DeleteMapping("/organizations/{organizationId}/projects/{projectId}/issues/{issueId}/comments/{commentId}")
    public ResponseEntity hideIssueComment(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId,
            @PathVariable UUID issueId,
            @PathVariable UUID commentId
    ) {
        var hideIssueCommentCommand = HideIssueCommentDtoMapper.toCommand(issueId, commentId);

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
     * @throws RestValidationException see {@link ChangeIssueCommentContentDtoMapper#toCommand(UUID, UUID, ChangeIssueCommentContentDto)}
     */
    @DeleteMapping("/organizations/{organizationId}/projects/{projectId}/issues/{issueId}/comments/{commentId}/content")
    public ResponseEntity changeIssueCommentContent(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId,
            @PathVariable UUID issueId,
            @PathVariable UUID commentId,
            @RequestBody ChangeIssueCommentContentDto changeIssueCommentContentDto
    ) {
        var changeIssueCommentContentCommand = ChangeIssueCommentContentDtoMapper.toCommand(
                issueId,
                commentId,
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
     * @throws RestValidationException see {@link VoteIssueCommentDtoMapper#toCommand(UUID, UUID, VoteIssueCommentDto)}
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
        var voteIssueCommentCommand = VoteIssueCommentDtoMapper.toCommand(
                issueId,
                commentId,
                voteIssueCommentDto
        );

        commandDispatcher.dispatch(voteIssueCommentCommand);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
