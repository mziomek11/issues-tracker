package org.example.issuestracker.issues.command.ui.http.rest.v1;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandGateway;
import org.example.issuestracker.issues.command.application.command.*;
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
    private final CommandGateway commandGateway;

    /**
     * @throws RestValidationException see {@link OpenIssueDtoMapper#toCommand(UUID, OpenIssueDto)}
     */
    @PostMapping("/issues")
    public ResponseEntity<UUID> openIssue(@RequestBody OpenIssueDto openIssueDto) {
        var issueId = UUID.randomUUID();
        var openIssueCommand = OpenIssueDtoMapper.toCommand(issueId, openIssueDto);

        commandGateway.dispatch(openIssueCommand);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(issueId);
    }

    /**
     * @throws IssueClosedException see {@link CloseIssueCommandHandler#handle(CloseIssueCommand)}
     * @throws IssueNotFoundException see {@link CloseIssueCommandHandler#handle(CloseIssueCommand)}
     * @throws RestValidationException see {@link CloseIssueDtoMapper#toCommand(UUID)}
     */
    @DeleteMapping("/issues/{issueId}")
    public ResponseEntity closeIssue(@PathVariable UUID issueId) {
        var closeIssueCommand = CloseIssueDtoMapper.toCommand(issueId);

        commandGateway.dispatch(closeIssueCommand);

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
    @PatchMapping("/issues/{issueId}/name")
    public ResponseEntity renameIssue(
            @PathVariable UUID issueId,
            @RequestBody RenameIssueDto renameIssueDto
    ) {
        var renameIssueCommand = RenameIssueDtoMapper.toCommand(issueId, renameIssueDto);

        commandGateway.dispatch(renameIssueCommand);

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
    @PatchMapping("/issues/{issueId}/type")
    public ResponseEntity changeIssueType(
            @PathVariable UUID issueId,
            @RequestBody ChangeIssueTypeDto changeIssueTypeDto
    ) {
        var changeIssueTypeCommand = ChangeIssueTypeDtoMapper.toCommand(issueId, changeIssueTypeDto);

        commandGateway.dispatch(changeIssueTypeCommand);

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
    @PatchMapping("/issues/{issueId}/content")
    public ResponseEntity changeIssueContent(
            @PathVariable UUID issueId,
            @RequestBody ChangeIssueContentDto changeIssueContentDto
    ) {
        var changeIssueContentCommand = ChangeIssueContentDtoMapper.toCommand(issueId, changeIssueContentDto);

        commandGateway.dispatch(changeIssueContentCommand);

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
    @PostMapping("/issues/{issueId}/votes")
    public ResponseEntity voteIssue(
            @PathVariable UUID issueId,
            @RequestBody VoteIssueDto voteIssueDto
    ) {
        var voteIssueCommand = VoteIssueDtoMapper.toCommand(issueId, voteIssueDto);

        commandGateway.dispatch(voteIssueCommand);

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
    @PostMapping("/issues/{issueId}/comments")
    public ResponseEntity<UUID> commentIssue(
            @PathVariable UUID issueId,
            @RequestBody CommentIssueDto commentIssueDto
    ) {
        var commentId = UUID.randomUUID();
        var commentIssueCommand = CommentIssueDtoMapper.toCommand(issueId, commentId, commentIssueDto);

        commandGateway.dispatch(commentIssueCommand);

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
    @DeleteMapping("/issues/{issueId}/comments/${commentId}")
    public ResponseEntity hideIssueComment(
            @PathVariable UUID issueId,
            @PathVariable UUID commentId
    ) {
        var hideIssueCommentCommand = HideIssueCommentDtoMapper.toCommand(issueId, commentId);

        commandGateway.dispatch(hideIssueCommentCommand);

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
    @DeleteMapping("/issues/{issueId}/comments/${commentId}/content")
    public ResponseEntity changeIssueCommentContent(
            @PathVariable UUID issueId,
            @PathVariable UUID commentId,
            @RequestBody ChangeIssueCommentContentDto changeIssueCommentContentDto
    ) {
        var changeIssueCommentContentCommand = ChangeIssueCommentContentDtoMapper.toCommand(
                issueId,
                commentId,
                changeIssueCommentContentDto
        );

        commandGateway.dispatch(changeIssueCommentContentCommand);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
