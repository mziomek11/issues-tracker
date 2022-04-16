package org.example.issuestracker.accounts.command.ui.http.rest.v1.mapper;

import org.example.issuestracker.accounts.command.application.command.ActivateAccountCommand;
import org.example.issuestracker.accounts.command.application.command.ActivateAccountCommand.ActivateAccountCommandBuilder;
import org.example.issuestracker.accounts.command.ui.http.rest.v1.dto.ActivateAccountDto;
import org.example.rest.v1.RestValidationErrorsMapper;
import org.example.rest.v1.RestValidationException;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ActivateAccountDtoMapper {
    private ActivateAccountDtoMapper() {}

    /**
     * @throws RestValidationException if dto is not valid
     */
    public static ActivateAccountCommand toCommand(UUID accountId, ActivateAccountDto dto) {
        var builder = ActivateAccountCommand
                .builder()
                .accountId(accountId)
                .accountActivationToken(dto.accountActivationToken());

        var validationErrors = builder.validate();
        if (!validationErrors.isEmpty()) {
            throw new RestValidationException(toDtoErrors(validationErrors));
        }

        return builder.build();
    }

    public static Map<String, Set<String>> toDtoErrors(
            Set<ConstraintViolation<ActivateAccountCommandBuilder>> builderErrors
    ) {
        var keyMap = new HashMap<String, String>();

        keyMap.put(
                ActivateAccountCommandBuilder.ACCOUNT_ACTIVATION_TOKEN_FIELD_NAME,
                ActivateAccountDto.ACCOUNT_ACTIVATION_TOKEN_FIELD_NAME
        );

        return RestValidationErrorsMapper.toDtoErrors(builderErrors, keyMap);
    }
}
