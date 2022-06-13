package com.mateuszziomek.issuestracker.organizations.command.infrastructure.projection;

import com.mateuszziomek.issuestracker.organizations.command.projection.Member;
import com.mateuszziomek.issuestracker.organizations.command.projection.MemberRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MongoMemberRepository extends MemberRepository, MongoRepository<Member, UUID> { }
