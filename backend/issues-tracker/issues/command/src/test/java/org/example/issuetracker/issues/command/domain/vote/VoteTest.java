package org.example.issuetracker.issues.command.domain.vote;

import static org.assertj.core.api.Assertions.*;

import org.example.issuestracker.issues.command.domain.vote.Vote;
import org.example.issuestracker.issues.command.domain.vote.VoterId;
import org.example.issuestracker.issues.common.domain.vote.VoteType;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class VoteTest {
    @Test
    void voteTypesEqualityIsCorrectlyCalculated() {
        // Arrange
        var up1 = new Vote(new VoterId(UUID.randomUUID()), VoteType.UP);
        var up2 = new Vote(new VoterId(UUID.randomUUID()), VoteType.UP);
        var down1 = new Vote(new VoterId(UUID.randomUUID()), VoteType.DOWN);
        var down2 = new Vote(new VoterId(UUID.randomUUID()), VoteType.DOWN);

        // Assert
        assertThat(up1.hasTheSameTypeAs(up2)).isTrue();
        assertThat(down1.hasTheSameTypeAs(down2)).isTrue();
        assertThat(up1.hasTheSameTypeAs(down1)).isFalse();
        assertThat(down2.hasTheSameTypeAs(up2)).isFalse();
    }
}
