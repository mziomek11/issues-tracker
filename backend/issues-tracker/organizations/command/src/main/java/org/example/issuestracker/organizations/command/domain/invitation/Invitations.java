package org.example.issuestracker.organizations.command.domain.invitation;

import lombok.RequiredArgsConstructor;
import org.example.issuestracker.organizations.command.domain.invitation.exception.InvitationAlreadyPresentException;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class Invitations {
    private final Set<Invitation> invitationSet;

    public Invitations() {
        this.invitationSet = new HashSet<>();
    }

    /**
     * Adds invitation to invitations
     *
     * @param invitation to be added
     * @throws InvitationAlreadyPresentException see {@link #ensureCanAdd(Invitation)}
     */
    public Invitations add(Invitation invitation) {
        ensureCanAdd(invitation);

        var newInvitationSet = new HashSet<>(invitationSet);
        newInvitationSet.add(invitation);

        return new Invitations(newInvitationSet);
    }

    /**
     * Ensures invitation can be added to invitations
     *
     * @param invitation to be added
     * @throws InvitationAlreadyPresentException if invitation is already present
     */
    public void ensureCanAdd(Invitation invitation) {
        if (invitationSet.contains(invitation)) {
            throw new InvitationAlreadyPresentException(invitation.getMember().getMemberId());
        }
    }
}
