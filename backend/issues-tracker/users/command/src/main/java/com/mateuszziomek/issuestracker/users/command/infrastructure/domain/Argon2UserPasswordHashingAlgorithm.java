package com.mateuszziomek.issuestracker.users.command.infrastructure.domain;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserHashedPassword;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserPasswordHashingAlgorithm;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserPlainPassword;
import org.springframework.stereotype.Service;

@Service
public class Argon2UserPasswordHashingAlgorithm implements UserPasswordHashingAlgorithm {
    private final Argon2 argon2 = Argon2Factory.create();

    @Override
    public UserHashedPassword hash(UserPlainPassword plainPassword) {
        return new UserHashedPassword(argon2.hash(22, 65536, 1, plainPassword.text().toCharArray()));
    }
}
