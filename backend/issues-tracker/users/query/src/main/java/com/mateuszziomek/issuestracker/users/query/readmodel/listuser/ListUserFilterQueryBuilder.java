package com.mateuszziomek.issuestracker.users.query.readmodel.listuser;

import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserStatus;
import com.mateuszziomek.issuestracker.shared.readmodel.user.ListUser;

import java.util.List;

public interface ListUserFilterQueryBuilder {
    ListUserFilterQueryBuilder emailEquals(String email);
    ListUserFilterQueryBuilder statusEquals(UserStatus status);
    List<ListUser> execute();
}
