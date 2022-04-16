package org.example.issuestracker.accounts.command.infrastructure.domain;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.example.issuestracker.accounts.command.domain.account.AccountHashedPassword;
import org.example.issuestracker.accounts.command.domain.account.AccountPasswordHashingAlgorithm;
import org.example.issuestracker.accounts.command.domain.account.AccountPlainPassword;
import org.springframework.stereotype.Service;

@Service
public class Argon2AccountPasswordHashingAlgorithm implements AccountPasswordHashingAlgorithm {
    private final Argon2 argon2 = Argon2Factory.create();

    @Override
    public AccountHashedPassword hash(AccountPlainPassword plainPassword) {
        return new AccountHashedPassword(argon2.hash(22, 65536, 1, plainPassword.text().toCharArray()));
    }
}
