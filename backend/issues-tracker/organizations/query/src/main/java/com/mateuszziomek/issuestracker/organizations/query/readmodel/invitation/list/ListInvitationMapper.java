package com.mateuszziomek.issuestracker.organizations.query.readmodel.invitation.list;

import com.mateuszziomek.issuestracker.organizations.query.domain.Invitation;
import com.mateuszziomek.issuestracker.shared.readmodel.invitation.ListInvitation;

public class ListInvitationMapper {
    private ListInvitationMapper() {}

    public static ListInvitation fromModel(Invitation invitation) {
        return ListInvitation
                .builder()
                .member(mapMember(invitation))
                .organization(mapOrganization(invitation))
                .build();
    }

    private static ListInvitation.Member mapMember(Invitation invitation) {
        return ListInvitation.Member
                .builder()
                .id(invitation.getMemberId())
                .build();
    }

    private static ListInvitation.Organization mapOrganization(Invitation invitation) {
        return ListInvitation.Organization
                .builder()
                .id(invitation.getOrganizationId())
                .name(invitation.getOrganizationName())
                .build();
    }
}
