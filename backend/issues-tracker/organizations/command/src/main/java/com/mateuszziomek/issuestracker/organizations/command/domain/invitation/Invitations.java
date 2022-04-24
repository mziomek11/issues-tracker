package com.mateuszziomek.issuestracker.organizations.command.domain.invitation;

import com.mateuszziomek.issuestracker.organizations.command.domain.invitation.exception.InvitationAlreadyPresentException;
import com.mateuszziomek.issuestracker.organizations.command.domain.invitation.exception.InvitationNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class Invitations {
    private final Set<Invitation> invitationSet;

    public Invitations() {
        this.invitationSet = new HashSet<>();
    }

    /**
     * @throws InvitationAlreadyPresentException see {@link #ensureCanAdd(Invitation)}
     */
    public Invitations add(Invitation invitation) {
        ensureCanAdd(invitation);

        var newInvitationSet = new HashSet<>(invitationSet);
        newInvitationSet.add(invitation);

        return new Invitations(newInvitationSet);
    }

    /**
     * @throws InvitationAlreadyPresentException if invitation is already present
     */
    public void ensureCanAdd(Invitation invitation) {
        if (invitationSet.contains(invitation)) {
            throw new InvitationAlreadyPresentException(invitation.getMember().getId());
        }
    }

    /**
     * @throws InvitationNotFoundException see {@link #ensureCanRemove(Invitation)}
     */
    public Invitations remove(Invitation invitation) {
        ensureCanRemove(invitation);

        var newInvitationSet = new HashSet<>(invitationSet);
        newInvitationSet.remove(invitation);

        return new Invitations(newInvitationSet);
    }

    /**
     * @throws InvitationNotFoundException if invitation is not present in invitations
     */
    public void ensureCanRemove(Invitation invitation) {
        if (!invitationSet.contains(invitation)) {
            throw new InvitationNotFoundException(invitation.getMember().getId());
        }
    }
}
