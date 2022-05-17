package com.mateuszziomek.issuestracker.issues.query.domain;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface IssueRepository extends ReactiveCrudRepository<Issue, UUID> { }
