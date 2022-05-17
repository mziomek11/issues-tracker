package com.mateuszziomek.issuestracker.issues.query.domain;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface MemberRepository extends ReactiveCrudRepository<Member, UUID> { }
