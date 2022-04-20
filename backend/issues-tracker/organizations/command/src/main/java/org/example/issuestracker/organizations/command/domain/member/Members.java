package org.example.issuestracker.organizations.command.domain.member;

import lombok.RequiredArgsConstructor;
import org.example.issuestracker.organizations.command.domain.member.exception.MemberAlreadyPresentException;
import org.example.issuestracker.organizations.command.domain.organization.OrganizationOwner;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class Members {
    private final Set<Member> memberSet;

    public Members(OrganizationOwner organizationOwner) {
        this.memberSet = new HashSet<>();
        this.memberSet.add(organizationOwner);
    }

    /**
     * Adds member to members
     *
     * @param member to be added
     * @throws MemberAlreadyPresentException see {@link #ensureCanAdd(Member)}
     */
    public Members add(Member member) {
        ensureCanAdd(member);

        var newMembersSet = new HashSet<>(memberSet);
        newMembersSet.add(member);

        return new Members(newMembersSet);
    }

    /**
     * Ensures that member can be added to members
     *
     * @param member to be added
     * @throws MemberAlreadyPresentException if member is already present
     */
    public void ensureCanAdd(Member member) {
        if (memberSet.contains(member)) {
            throw new MemberAlreadyPresentException(member.getMemberId());
        }
    }
}
