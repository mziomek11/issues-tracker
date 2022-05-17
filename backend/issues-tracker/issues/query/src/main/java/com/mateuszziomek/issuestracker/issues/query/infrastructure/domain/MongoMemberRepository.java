package com.mateuszziomek.issuestracker.issues.query.infrastructure.domain;

import com.mateuszziomek.issuestracker.issues.query.domain.Member;
import com.mateuszziomek.issuestracker.issues.query.domain.MemberRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MongoMemberRepository extends ReactiveMongoRepository<Member, UUID>, MemberRepository { }
