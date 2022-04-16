package org.example.issuestracker.accounts.command.infrastructure.domain;

import org.example.issuestracker.accounts.command.domain.account.AccountHashedPassword;
import org.example.issuestracker.accounts.command.domain.account.AccountPasswordHashingAlgorithm;
import org.example.issuestracker.accounts.command.domain.account.AccountPlainPassword;
import org.springframework.stereotype.Service;

@Service
public class Argon2AccountPasswordHashingAlgorithm implements AccountPasswordHashingAlgorithm {
    @Override
    public AccountHashedPassword hash(AccountPlainPassword password) {
        // @TODO implement
        return new AccountHashedPassword("hashed" + password.text());
    }
}
