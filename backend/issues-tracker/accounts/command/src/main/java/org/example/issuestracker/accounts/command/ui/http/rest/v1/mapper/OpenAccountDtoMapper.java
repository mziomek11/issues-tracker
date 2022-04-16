package org.example.issuestracker.accounts.command.ui.http.rest.v1.mapper;

import org.example.issuestracker.accounts.command.application.command.OpenAccountCommand;
import org.example.issuestracker.accounts.command.application.command.OpenAccountCommand.OpenAccountCommandBuilder;
import org.example.issuestracker.accounts.command.ui.http.rest.v1.dto.OpenAccountDto;
import org.example.rest.v1.RestValidationErrorsMapper;
import org.example.rest.v1.RestValidationException;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class OpenAccountDtoMapper {
    private OpenAccountDtoMapper() {}

    /**
     * @throws RestValidationException if dto is not valid
     */
    public static OpenAccountCommand toCommand(UUID accountId, OpenAccountDto dto) {
        var builder = OpenAccountCommand
                .builder()
                .accountId(accountId)
                .accountEmail(dto.accountEmail())
                .accountPlainPassword(dto.accountPlainPassword());

        var validationErrors = builder.validate();
        if (!validationErrors.isEmpty()) {
            throw new RestValidationException(toDtoErrors(validationErrors));
        }

        return builder.build();
    }

    public static Map<String, Set<String>> toDtoErrors(
            Set<ConstraintViolation<OpenAccountCommand.OpenAccountCommandBuilder>> builderErrors
    ) {
        var keyMap = new HashMap<String, String>();

        keyMap.put(
                OpenAccountCommandBuilder.ACCOUNT_EMAIL_FIELD_NAME,
                OpenAccountDto.ACCOUNT_EMAIL_FIELD_NAME
        );

        keyMap.put(
                OpenAccountCommandBuilder.ACCOUNT_PLAIN_PASSWORD_FIELD_NAME,
                OpenAccountDto.ACCOUNT_PLAIN_PASSWORD_FIELD_NAME
        );

        return RestValidationErrorsMapper.toDtoErrors(builderErrors, keyMap);
    }
}
