package com.mateuszziomek.issuestracker.notifications.infrastructure.domain;

import com.mateuszziomek.issuestracker.notifications.domain.user.User;
import com.mateuszziomek.issuestracker.notifications.domain.user.UserRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MongoUserRepository extends ReactiveMongoRepository<User, UUID>, UserRepository { }