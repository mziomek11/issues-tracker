package com.mateuszziomek.issuestracker.organizations.query.infrastructure.readmodel.invitation.list;

import com.mateuszziomek.issuestracker.organizations.query.readmodel.invitation.list.ListInvitationFilterQueryBuilder;
import com.mateuszziomek.issuestracker.organizations.query.readmodel.invitation.list.ListInvitationFilterQueryBuilderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MongoListInvitationFilterQueryBuilderFactory implements ListInvitationFilterQueryBuilderFactory {
    private final ReactiveMongoTemplate template;

    @Override
    public ListInvitationFilterQueryBuilder create() {
        return new MongoListInvitationFilterQueryBuilder(template);
    }
}
