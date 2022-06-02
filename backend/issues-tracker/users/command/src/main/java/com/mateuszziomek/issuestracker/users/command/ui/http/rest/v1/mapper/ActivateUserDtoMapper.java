package com.mateuszziomek.issuestracker.users.command.ui.http.rest.v1.mapper;

import com.mateuszziomek.issuestracker.users.command.ui.http.rest.v1.dto.ActivateUserDto;
import com.mateuszziomek.issuestracker.users.command.application.command.ActivateUserCommand;
import com.mateuszziomek.issuestracker.users.command.application.command.ActivateUserCommand.ActivateUserCommandBuilder;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.validation.RestValidationErrorsMapper;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.validation.RestValidationException;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ActivateUserDtoMapper {
    private ActivateUserDtoMapper() {}

    /**
     * @throws RestValidationException if dto is not valid
     */
    public static ActivateUserCommand toCommand(UUID accountId, ActivateUserDto dto) {
        var builder = ActivateUserCommand
                .builder()
                .userId(accountId)
                .userActivationToken(dto.activationToken());

        var validationErrors = builder.validate();
        if (!validationErrors.isEmpty()) {
            throw new RestValidationException(toDtoErrors(validationErrors));
        }

        return builder.build();
    }

    public static Map<String, Set<String>> toDtoErrors(
            Set<ConstraintViolation<ActivateUserCommandBuilder>> builderErrors
    ) {
        var keyMap = new HashMap<String, String>();

        keyMap.put(
                ActivateUserCommandBuilder.USER_ACTIVATION_TOKEN_FIELD_NAME,
                ActivateUserDto.USER_ACTIVATION_TOKEN_FIELD_NAME
        );

        return RestValidationErrorsMapper.toDtoErrors(builderErrors, keyMap);
    }
}
