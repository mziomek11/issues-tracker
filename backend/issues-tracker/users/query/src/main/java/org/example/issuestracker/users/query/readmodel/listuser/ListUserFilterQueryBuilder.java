package org.example.issuestracker.users.query.readmodel.listuser;

import java.util.List;

public interface ListUserFilterQueryBuilder {
    ListUserFilterQueryBuilder emailEquals(String email);
    List<ListUser> execute();
}
