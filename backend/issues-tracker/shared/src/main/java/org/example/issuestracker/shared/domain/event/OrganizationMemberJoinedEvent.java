package org.example.issuestracker.shared.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.cqrs.event.BaseEvent;
import org.example.cqrs.event.EventBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class OrganizationMemberJoinedEvent extends BaseEvent {
    private UUID memberId;

    public static OrganizationMemberJoinedEventBuilder builder() {
        return new OrganizationMemberJoinedEventBuilder();
    }

    private OrganizationMemberJoinedEvent(UUID organizationId, UUID memberId) {
        super(organizationId);

        this.memberId = memberId;
    }

    public static class OrganizationMemberJoinedEventBuilder
            extends EventBuilder<OrganizationMemberJoinedEventBuilder, OrganizationMemberJoinedEvent> {
        @NotNull
        private UUID organizationId;

        @NotNull
        private UUID memberId;

        public OrganizationMemberJoinedEventBuilder organizationId(UUID organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public OrganizationMemberJoinedEventBuilder memberId(UUID memberId) {
            this.memberId = memberId;
            return this;
        }

        @Override
        protected OrganizationMemberJoinedEvent create() {
            return new OrganizationMemberJoinedEvent(
                    organizationId,
                    memberId
            );
        }
    }
}
