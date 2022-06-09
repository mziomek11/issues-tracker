package com.mateuszziomek.issuestracker.users.command.projection;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
