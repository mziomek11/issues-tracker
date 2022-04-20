package org.example.issuestracker.organizations.command.domain.organization;

import org.example.issuestracker.organizations.command.domain.member.Member;
import org.example.issuestracker.organizations.command.domain.member.MemberId;

public class OrganizationOwner extends Member {
    public OrganizationOwner(MemberId id) {
        super(id);
    }
}
