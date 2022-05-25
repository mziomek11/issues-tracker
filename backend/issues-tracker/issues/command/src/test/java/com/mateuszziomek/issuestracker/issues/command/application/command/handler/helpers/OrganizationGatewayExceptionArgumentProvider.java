package com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers;

import static com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers.IssueCommandData.*;

import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationProjectNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationServiceUnavailableException;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class OrganizationGatewayExceptionArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(
                Arguments.of(new OrganizationNotFoundException(ORGANIZATION_ID)),
                Arguments.of(new OrganizationProjectNotFoundException(PROJECT_ID)),
                Arguments.of(new OrganizationMemberNotFoundException(MEMBER_ID)),
                Arguments.of(new OrganizationServiceUnavailableException())
        );
    }
}
