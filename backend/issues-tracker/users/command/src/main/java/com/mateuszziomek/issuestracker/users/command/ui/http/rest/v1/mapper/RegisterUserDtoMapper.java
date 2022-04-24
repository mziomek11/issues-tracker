package com.mateuszziomek.issuestracker.users.command.ui.http.rest.v1.mapper;

import com.mateuszziomek.issuestracker.users.command.application.command.RegisterUserCommand;
import com.mateuszziomek.issuestracker.users.command.application.command.RegisterUserCommand.RegisterUserCommandBuilder;
import com.mateuszziomek.issuestracker.users.command.ui.http.rest.v1.dto.RegisterUserDto;
import com.mateuszziomek.rest.v1.RestValidationErrorsMapper;
import com.mateuszziomek.rest.v1.RestValidationException;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class RegisterUserDtoMapper {
    private RegisterUserDtoMapper() {}

    /**
     * @throws RestValidationException if dto is not valid
     */
    public static RegisterUserCommand toCommand(UUID userId, RegisterUserDto dto) {
        var builder = RegisterUserCommand
                .builder()
                .userId(userId)
                .userEmail(dto.email())
                .userPlainPassword(dto.password());

        var validationErrors = builder.validate();
        if (!validationErrors.isEmpty()) {
            throw new RestValidationException(toDtoErrors(validationErrors));
        }

        return builder.build();
    }

    public static Map<String, Set<String>> toDtoErrors(
            Set<ConstraintViolation<RegisterUserCommandBuilder>> builderErrors
    ) {
        var keyMap = new HashMap<String, String>();

        keyMap.put(
                RegisterUserCommandBuilder.USER_EMAIL_FIELD_NAME,
                RegisterUserDto.USER_EMAIL_FIELD_NAME
        );

        keyMap.put(
                RegisterUserCommandBuilder.USER_PLAIN_PASSWORD_FIELD_NAME,
                RegisterUserDto.USER_PLAIN_PASSWORD_FIELD_NAME
        );

        return RestValidationErrorsMapper.toDtoErrors(builderErrors, keyMap);
    }
}
