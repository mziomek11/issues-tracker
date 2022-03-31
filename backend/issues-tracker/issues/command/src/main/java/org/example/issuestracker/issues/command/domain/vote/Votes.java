package org.example.issuestracker.issues.command.domain.vote;

import org.example.issuestracker.issues.command.domain.vote.exception.VoteAlreadyExistsException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Votes {
    private final Set<Vote> voteSet;

    public Votes() {
        this.voteSet = new HashSet<>();
    }

    public Votes(Set<Vote> voteSet) {
        this.voteSet = voteSet;
    }

    /**
     * Adds vote to votes if vote with given voter id does not exist. Otherwise replaces
     * existing vote with given.
     *
     * @param vote to be added
     * @throws VoteAlreadyExistsException see {@link Votes#ensureCanAdd(Vote)}
     */
    public Votes add(Vote vote) {
        ensureCanAdd(vote);

        var newVotes = voteSet
                .stream()
                .filter(existingVote -> !existingVote.getVoterId().equals(vote.getVoterId()))
                .collect(Collectors.toSet());

        newVotes.add(vote);

        return new Votes(newVotes);
    }

    /**
     * Ensures that vote can be added to the votes
     *
     * @param vote to be added
     * @throws VoteAlreadyExistsException if vote with given voter id and type already exists
     */
    public void ensureCanAdd(Vote vote) {
        if (contains(vote)) {
            throw new VoteAlreadyExistsException(vote.getVoterId(), vote.getType());
        }
    }

    /**
     * Checks if vote with given voter id and type already exists
     *
     * @param vote to be checked
     */
    private boolean contains(Vote vote) {
        var optionalExistingVote = findVoteByVoterId(vote.getVoterId());

        if (optionalExistingVote.isEmpty()) {
            return false;
        }

        var existingVote = optionalExistingVote.get();

        return vote.hasTheSameTypeAs(existingVote);
    }

    private Optional<Vote> findVoteByVoterId(VoterId voterId) {
        return voteSet
                .stream()
                .filter(vote -> vote.getVoterId().equals(voterId))
                .findFirst();
    }
}
