package org.example.issuestracker.users.command.infrastructure.domain;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.example.issuestracker.users.command.domain.account.UserHashedPassword;
import org.example.issuestracker.users.command.domain.account.UserPasswordHashingAlgorithm;
import org.example.issuestracker.users.command.domain.account.UserPlainPassword;
import org.springframework.stereotype.Service;

@Service
public class Argon2UserPasswordHashingAlgorithm implements UserPasswordHashingAlgorithm {
    private final Argon2 argon2 = Argon2Factory.create();

    @Override
    public UserHashedPassword hash(UserPlainPassword plainPassword) {
        return new UserHashedPassword(argon2.hash(22, 65536, 1, plainPassword.text().toCharArray()));
    }
}
