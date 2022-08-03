package com.mateuszziomek.issuestracker.organizations.query.domain.member;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface MemberRepository extends ReactiveCrudRepository<Member, UUID> { }
