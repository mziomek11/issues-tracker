package org.example.issuestracker.users.command.domain.account;

public interface UserPasswordHashingAlgorithm {
    UserHashedPassword hash(UserPlainPassword password);
}
