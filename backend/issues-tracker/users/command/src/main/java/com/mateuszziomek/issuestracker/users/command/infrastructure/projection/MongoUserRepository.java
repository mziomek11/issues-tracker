package com.mateuszziomek.issuestracker.users.command.infrastructure.projection;

import com.mateuszziomek.issuestracker.users.command.projection.User;
import com.mateuszziomek.issuestracker.users.command.projection.UserRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MongoUserRepository extends UserRepository, MongoRepository<User, UUID> { }
