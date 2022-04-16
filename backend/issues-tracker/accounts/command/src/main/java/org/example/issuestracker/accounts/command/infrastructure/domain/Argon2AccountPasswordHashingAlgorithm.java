package org.example.issuestracker.accounts.command.infrastructure.domain;

import org.example.issuestracker.accounts.command.domain.account.AccountHashedPassword;
import org.example.issuestracker.accounts.command.domain.account.AccountPasswordHashingAlgorithm;
import org.example.issuestracker.accounts.command.domain.account.AccountPlainPassword;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Argon2AccountPasswordHashingAlgorithm implements AccountPasswordHashingAlgorithm {
    private final Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder();

    @Override
    public AccountHashedPassword hash(AccountPlainPassword password) {
        return new AccountHashedPassword(argon2PasswordEncoder.encode(password.text()));
    }
}
