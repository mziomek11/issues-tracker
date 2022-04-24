package com.mateuszziomek.issuestracker.users.query.infrastructure.domain;

import com.mateuszziomek.issuestracker.users.query.domain.UserRepository;
import com.mateuszziomek.issuestracker.users.query.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MongoUserRepository extends MongoRepository<User, UUID>, UserRepository { }
