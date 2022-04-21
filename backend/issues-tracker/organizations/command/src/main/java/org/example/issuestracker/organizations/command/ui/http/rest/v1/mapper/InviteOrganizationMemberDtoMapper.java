package org.example.issuestracker.organizations.command.ui.http.rest.v1.mapper;

import org.example.issuestracker.organizations.command.application.command.InviteOrganizationMemberCommand;
import org.example.issuestracker.organizations.command.application.command.InviteOrganizationMemberCommand.InviteOrganizationMemberCommandBuilder;
import org.example.issuestracker.organizations.command.ui.http.rest.v1.dto.InviteOrganizationMemberDto;
import org.example.rest.v1.RestValidationErrorsMapper;
import org.example.rest.v1.RestValidationException;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class InviteOrganizationMemberDtoMapper {
    private InviteOrganizationMemberDtoMapper() {}

    /**
     * @throws RestValidationException if dto is not valid
     */
    public static InviteOrganizationMemberCommand toCommand(
            UUID organizationId,
            UUID userId,
            InviteOrganizationMemberDto dto
    ) {
        var builder = InviteOrganizationMemberCommand
                .builder()
                .organizationId(organizationId)
                .organizationOwnerId(userId)
                .memberEmail(dto.email());

        var validationErrors = builder.validate();
        if (!validationErrors.isEmpty()) {
            throw new RestValidationException(toDtoErrors(validationErrors));
        }

        return builder.build();
    }

    public static Map<String, Set<String>> toDtoErrors(
            Set<ConstraintViolation<InviteOrganizationMemberCommandBuilder>> builderErrors
    ) {
        var keyMap = new HashMap<String, String>();

        keyMap.put(
                InviteOrganizationMemberCommandBuilder.MEMBER_EMAIL_FIELD_NAME,
                InviteOrganizationMemberDto.MEMBER_EMAIL_FIELD_NAME
        );

        return RestValidationErrorsMapper.toDtoErrors(builderErrors, keyMap);
    }
}
