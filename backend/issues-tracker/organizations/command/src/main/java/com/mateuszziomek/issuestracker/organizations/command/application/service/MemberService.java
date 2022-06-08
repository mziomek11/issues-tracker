package com.mateuszziomek.issuestracker.organizations.command.application.service;

import com.mateuszziomek.issuestracker.organizations.command.application.service.exception.MemberNotFoundException;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberEmail;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberId;
import com.mateuszziomek.issuestracker.organizations.command.projection.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * @throws MemberNotFoundException if member with given email does not exist
     */
    public MemberId getMemberId(MemberEmail memberEmail) {
        return memberRepository
                .findByEmail(memberEmail.text())
                .map(member -> new MemberId(member.getId()))
                .orElseThrow(() -> new MemberNotFoundException(memberEmail));
    }
}
