package org.example.issuestracker.shared.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.cqrs.event.BaseEvent;
import org.example.cqrs.event.EventBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class OrganizationCreatedEvent extends BaseEvent {
    private UUID organizationOwnerId;
    private String organizationName;

    public static OrganizationCreatedEventBuilder builder() {
        return new OrganizationCreatedEventBuilder();
    }

    private OrganizationCreatedEvent(UUID organizationId, UUID organizationOwnerId, String organizationName) {
        super(organizationId);

        this.organizationOwnerId = organizationOwnerId;
        this.organizationName = organizationName;
    }

    public static class OrganizationCreatedEventBuilder
            extends EventBuilder<OrganizationCreatedEventBuilder, OrganizationCreatedEvent> {
        @NotNull
        private UUID organizationId;

        @NotNull
        private UUID organizationOwnerId;

        @NotBlank
        private String organizationName;

        public OrganizationCreatedEventBuilder organizationId(UUID organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public OrganizationCreatedEventBuilder organizationOwnerId(UUID organizationOwnerId) {
            this.organizationOwnerId = organizationOwnerId;
            return this;
        }

        public OrganizationCreatedEventBuilder organizationName(String organizationName) {
            this.organizationName = organizationName;
            return this;
        }

        @Override
        protected OrganizationCreatedEvent create() {
            return new OrganizationCreatedEvent(
                    organizationId,
                    organizationOwnerId,
                    organizationName
            );
        }
    }
}
