package org.example.issuestracker.users.query.infrastructure.domain;

import org.example.issuestracker.users.query.domain.User;
import org.example.issuestracker.users.query.domain.UserRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MongoUserRepository extends MongoRepository<User, UUID>, UserRepository { }
