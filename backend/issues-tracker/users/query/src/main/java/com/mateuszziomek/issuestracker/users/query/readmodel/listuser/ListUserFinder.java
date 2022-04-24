package com.mateuszziomek.issuestracker.users.query.readmodel.listuser;

import lombok.RequiredArgsConstructor;
import com.mateuszziomek.issuestracker.shared.readmodel.ListUser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListUserFinder {
    private final ListUserFilterQueryBuilderFactory queryBuilderFactory;

    public List<ListUser> findByFilter(ListUserFilter filter) {
        var qb = queryBuilderFactory.create();

        if (filter.getEmail() != null && !filter.getEmail().isBlank()) {
            qb.emailEquals(filter.getEmail());
        }

        if (filter.getStatus() != null) {
            qb.statusEquals(filter.getStatus());
        }

        return qb.execute();
    }
}
