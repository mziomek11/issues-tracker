package org.example.issuestracker.issues.command.ui.http.rest.v1;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandGateway;
import org.example.issuestracker.issues.command.ui.http.rest.v1.dto.OpenIssueDto;
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
}
