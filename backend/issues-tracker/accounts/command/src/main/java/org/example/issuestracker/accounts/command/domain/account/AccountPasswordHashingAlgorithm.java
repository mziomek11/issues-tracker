package org.example.issuestracker.accounts.command.domain.account;

public interface AccountPasswordHashingAlgorithm {
    AccountHashedPassword hash(AccountPlainPassword password);
}
