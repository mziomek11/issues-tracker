package com.mateuszziomek.issuestracker.organizations.command.ui.http.rest.v1.mapper;

import com.mateuszziomek.issuestracker.organizations.command.application.command.InviteOrganizationMemberCommand;
import com.mateuszziomek.issuestracker.organizations.command.application.command.InviteOrganizationMemberCommand.InviteOrganizationMemberCommandBuilder;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.organization.InviteOrganizationMemberDto;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.validation.RestValidationErrorsMapper;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.validation.RestValidationException;

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
