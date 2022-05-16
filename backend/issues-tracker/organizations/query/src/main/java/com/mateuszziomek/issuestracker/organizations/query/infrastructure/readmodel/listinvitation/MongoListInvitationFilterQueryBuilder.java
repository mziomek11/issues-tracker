package com.mateuszziomek.issuestracker.organizations.query.infrastructure.readmodel.listinvitation;

import com.mateuszziomek.issuestracker.organizations.query.domain.Invitation;
import com.mateuszziomek.issuestracker.organizations.query.readmodel.listinvitation.ListInvitationFilterQueryBuilder;
import com.mateuszziomek.issuestracker.organizations.query.readmodel.listinvitation.ListInvitationMapper;
import com.mateuszziomek.issuestracker.shared.readmodel.ListInvitation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;

import java.util.UUID;

@RequiredArgsConstructor
public class MongoListInvitationFilterQueryBuilder implements ListInvitationFilterQueryBuilder {
    private final ReactiveMongoTemplate template;
    private final Query query = new Query();

    @Override
    public ListInvitationFilterQueryBuilder containsMemberWithId(UUID memberId) {
        query.addCriteria(Criteria.where("memberId").is(memberId));
        return this;
    }

    @Override
    public ListInvitationFilterQueryBuilder containsOrganizationWithId(UUID memberId) {
        query.addCriteria(Criteria.where("organizationId").is(memberId));
        return this;
    }

    @Override
    public Flux<ListInvitation> execute() {
        return template
                .find(query, Invitation.class)
                .map(ListInvitationMapper::fromModel);
    }
}
