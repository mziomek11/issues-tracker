package com.mateuszziomek.issuestracker.users.command.domain.user;

public interface UserPasswordHashingAlgorithm {
    UserHashedPassword hash(UserPlainPassword password);
}
