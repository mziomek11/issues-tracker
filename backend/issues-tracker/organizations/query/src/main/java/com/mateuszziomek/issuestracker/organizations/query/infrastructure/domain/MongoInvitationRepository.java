package com.mateuszziomek.issuestracker.organizations.query.infrastructure.domain;

import com.mateuszziomek.issuestracker.organizations.query.domain.invitation.Invitation;
import com.mateuszziomek.issuestracker.organizations.query.domain.invitation.InvitationRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MongoInvitationRepository extends ReactiveMongoRepository<Invitation, UUID>, InvitationRepository { }
