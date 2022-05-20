package com.mateuszziomek.issuestracker.issues.query.readmodel.issue.details;

import com.mateuszziomek.issuestracker.issues.query.domain.IssueRepository;
import com.mateuszziomek.issuestracker.shared.readmodel.issue.DetailsIssue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DetailsIssueFinder {
    private final IssueRepository issueRepository;

    public Mono<DetailsIssue> findById(UUID id) {
        return issueRepository
                .findById(id)
                .map(DetailsIssueMapper::fromModel);
    }
}
