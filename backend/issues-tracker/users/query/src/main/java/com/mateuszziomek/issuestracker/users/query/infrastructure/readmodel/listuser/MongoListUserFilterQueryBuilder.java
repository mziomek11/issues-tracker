package com.mateuszziomek.issuestracker.users.query.infrastructure.readmodel.listuser;

import com.mateuszziomek.issuestracker.users.query.domain.User;
import com.mateuszziomek.issuestracker.users.query.readmodel.listuser.ListUserFilterQueryBuilder;
import com.mateuszziomek.issuestracker.users.query.readmodel.listuser.ListUserMapper;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserStatus;
import com.mateuszziomek.issuestracker.shared.readmodel.user.ListUser;
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
