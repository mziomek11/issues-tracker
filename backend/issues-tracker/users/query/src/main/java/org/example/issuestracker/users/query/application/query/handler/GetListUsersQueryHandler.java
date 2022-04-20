package org.example.issuestracker.users.query.application.query.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.query.QueryHandler;
import org.example.issuestracker.users.query.application.query.GetListUsersQuery;
import org.example.issuestracker.users.query.readmodel.listuser.ListUser;
import org.example.issuestracker.users.query.readmodel.listuser.ListUserFilter;
import org.example.issuestracker.users.query.readmodel.listuser.ListUserFinder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetListUsersQueryHandler implements QueryHandler<GetListUsersQuery, List<ListUser>> {
    private final ListUserFinder listUserFinder;

    @Override
    public List<ListUser> handle(GetListUsersQuery query) {
        var filter = ListUserFilter
                .builder()
                .email(query.getEmail())
                .build();

        return listUserFinder.findByFilter(filter);
    }
}
