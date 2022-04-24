package com.mateuszziomek.issuestracker.organizations.command.domain.organization;

import com.mateuszziomek.issuestracker.organizations.command.domain.member.Member;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberId;

public class OrganizationOwner extends Member {
    public OrganizationOwner(MemberId id) {
        super(id);
    }
}
