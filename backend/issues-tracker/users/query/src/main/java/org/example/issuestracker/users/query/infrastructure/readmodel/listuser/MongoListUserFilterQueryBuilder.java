package org.example.issuestracker.users.query.infrastructure.readmodel.listuser;

import lombok.RequiredArgsConstructor;
import org.example.issuestracker.shared.domain.valueobject.UserStatus;
import org.example.issuestracker.users.query.domain.User;
import org.example.issuestracker.users.query.readmodel.listuser.ListUser;
import org.example.issuestracker.users.query.readmodel.listuser.ListUserFilterQueryBuilder;
import org.example.issuestracker.users.query.readmodel.listuser.ListUserMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@RequiredArgsConstructor
public class MongoListUserFilterQueryBuilder implements ListUserFilterQueryBuilder {
    private final MongoTemplate template;
    private final Query query = new Query();

    @Override
    public ListUserFilterQueryBuilder emailEquals(String email) {
        query.addCriteria(Criteria.where("email").is(email));
        return this;
    }

    @Override
    public ListUserFilterQueryBuilder statusEquals(UserStatus status) {
        query.addCriteria(Criteria.where("status").is(status));
        return this;
    }

    @Override
    public List<ListUser> execute() {
        query.fields().include("email");

        return template
                .find(query, User.class)
                .stream()
                .map(ListUserMapper::fromModel)
                .toList();
    }
}
