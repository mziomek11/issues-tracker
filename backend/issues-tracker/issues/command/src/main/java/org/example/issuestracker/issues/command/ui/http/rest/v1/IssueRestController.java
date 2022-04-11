package org.example.issuestracker.issues.command.ui.http.rest.v1;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandGateway;
import org.example.issuestracker.issues.command.application.command.ChangeIssueContentCommand;
import org.example.issuestracker.issues.command.application.command.ChangeIssueTypeCommand;
import org.example.issuestracker.issues.command.application.command.CloseIssueCommand;
import org.example.issuestracker.issues.command.application.command.handler.ChangeIssueContentCommandHandler;
import org.example.issuestracker.issues.command.application.command.handler.ChangeIssueTypeCommandHandler;
import org.example.issuestracker.issues.command.application.command.handler.CloseIssueCommandHandler;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueClosedException;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueContentSetException;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueNotFoundException;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueTypeSetException;
import org.example.issuestracker.issues.command.ui.http.rest.v1.dto.ChangeIssueContentDto;
import org.example.issuestracker.issues.command.ui.http.rest.v1.dto.ChangeIssueTypeDto;
import org.example.issuestracker.issues.command.ui.http.rest.v1.dto.OpenIssueDto;
import org.example.issuestracker.issues.command.ui.http.rest.v1.mapper.ChangeIssueContentDtoMapper;
import org.example.issuestracker.issues.command.ui.http.rest.v1.mapper.ChangeIssueTypeDtoMapper;
import org.example.issuestracker.issues.command.ui.http.rest.v1.mapper.CloseIssueDtoMapper;
import org.example.issuestracker.issues.command.ui.http.rest.v1.mapper.OpenIssueDtoMapper;
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
     * @throws RestValidationException see {@link ChangeIssueContentDtoMapper#toCommand(UUID, ChangeIssueContentDto)}
     * @throws IssueClosedException see {@link ChangeIssueContentCommandHandler#handle(ChangeIssueContentCommand)}
     * @throws IssueContentSetException see {@link ChangeIssueContentCommandHandler#handle(ChangeIssueContentCommand)}
     * @throws IssueNotFoundException see {@link ChangeIssueContentCommandHandler#handle(ChangeIssueContentCommand)}
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
}