package com.mateuszziomek.issuestracker.organizations.query.infrastructure.domain;

import com.mateuszziomek.issuestracker.organizations.query.domain.member.Member;
import com.mateuszziomek.issuestracker.organizations.query.domain.member.MemberRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MongoMemberRepository extends ReactiveMongoRepository<Member, UUID>, MemberRepository { }
