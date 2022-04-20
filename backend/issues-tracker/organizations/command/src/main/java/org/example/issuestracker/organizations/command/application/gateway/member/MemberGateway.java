package org.example.issuestracker.organizations.command.application.gateway.member;

import org.example.issuestracker.organizations.command.application.gateway.member.exception.MemberNotFoundException;
import org.example.issuestracker.organizations.command.domain.member.MemberEmail;
import org.example.issuestracker.organizations.command.domain.member.MemberId;

public interface MemberGateway {
    /**
     * @throws MemberNotFoundException if member with given email does not exist
     */
    MemberId getMemberId(MemberEmail memberEmail);
}
