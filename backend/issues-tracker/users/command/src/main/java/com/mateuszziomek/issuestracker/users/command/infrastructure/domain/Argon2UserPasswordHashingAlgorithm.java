package com.mateuszziomek.issuestracker.users.command.infrastructure.domain;

import com.mateuszziomek.issuestracker.users.command.domain.user.UserHashedPassword;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserPasswordHashingAlgorithm;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserPlainPassword;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Argon2UserPasswordHashingAlgorithm implements UserPasswordHashingAlgorithm {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserHashedPassword hash(UserPlainPassword plainPassword) {
        return new UserHashedPassword(passwordEncoder.encode(plainPassword.text()));
    }
}
