package com.mateuszziomek.issuestracker.issues.query.ui.http.rest.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/issue-management")
@RequiredArgsConstructor
public class IssueRestController {
    @GetMapping("/organizations/{organizationId}/projects/{projectId}/issues")
    public ResponseEntity<List<UUID>> getListIssues(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(List.of(UUID.randomUUID(), UUID.randomUUID()));
    }
}
