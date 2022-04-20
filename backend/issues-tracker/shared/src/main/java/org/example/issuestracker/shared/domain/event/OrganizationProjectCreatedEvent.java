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
public class OrganizationProjectCreatedEvent extends BaseEvent {
    private UUID projectId;
    private String projectName;

    public static OrganizationProjectCreatedEventBuilder builder() {
        return new OrganizationProjectCreatedEventBuilder();
    }

    private OrganizationProjectCreatedEvent(UUID organizationId, UUID projectId, String projectName) {
        super(organizationId);

        this.projectId = projectId;
        this.projectName = projectName;
    }

    public static class OrganizationProjectCreatedEventBuilder
            extends EventBuilder<OrganizationProjectCreatedEventBuilder, OrganizationProjectCreatedEvent> {
        @NotNull
        private UUID organizationId;

        @NotNull
        private UUID projectId;

        @NotBlank
        private String projectName;

        public OrganizationProjectCreatedEventBuilder organizationId(UUID organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public OrganizationProjectCreatedEventBuilder projectId(UUID projectId) {
            this.projectId = projectId;
            return this;
        }

        public OrganizationProjectCreatedEventBuilder projectName(String projectName) {
            this.projectName = projectName;
            return this;
        }

        @Override
        protected OrganizationProjectCreatedEvent create() {
            return new OrganizationProjectCreatedEvent(
                    organizationId,
                    projectId,
                    projectName
            );
        }
    }
}
