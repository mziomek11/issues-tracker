package com.mateuszziomek.issuestracker.organizations.command.application.gateway.member;

import com.mateuszziomek.issuestracker.organizations.command.application.gateway.member.exception.MemberServiceNotAvailableException;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberId;
import com.mateuszziomek.issuestracker.organizations.command.application.gateway.member.exception.MemberNotFoundException;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberEmail;

public interface MemberGateway {
    /**
     * @throws MemberNotFoundException if member with given email does not exist
     * @throws MemberServiceNotAvailableException if member service is not available
     */
    MemberId getMemberId(MemberEmail memberEmail);
}
