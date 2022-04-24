package com.mateuszziomek.issuestracker.shared.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import com.mateuszziomek.cqrs.event.BaseEvent;
import com.mateuszziomek.cqrs.event.EventBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class OrganizationMemberInvitedEvent extends BaseEvent {
    private UUID memberId;

    public static OrganizationMemberInvitedEventBuilder builder() {
        return new OrganizationMemberInvitedEventBuilder();
    }

    private OrganizationMemberInvitedEvent(UUID organizationId, UUID memberId) {
        super(organizationId);

        this.memberId = memberId;
    }

    public static class OrganizationMemberInvitedEventBuilder
            extends EventBuilder<OrganizationMemberInvitedEventBuilder, OrganizationMemberInvitedEvent> {
        @NotNull
        private UUID organizationId;

        @NotNull
        private UUID memberId;

        public OrganizationMemberInvitedEventBuilder organizationId(UUID organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public OrganizationMemberInvitedEventBuilder memberId(UUID memberId) {
            this.memberId = memberId;
            return this;
        }

        @Override
        protected OrganizationMemberInvitedEvent create() {
            return new OrganizationMemberInvitedEvent(
                    organizationId,
                    memberId
            );
        }
    }
}
