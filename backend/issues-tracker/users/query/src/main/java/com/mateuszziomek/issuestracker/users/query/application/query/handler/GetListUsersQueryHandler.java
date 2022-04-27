package com.mateuszziomek.issuestracker.users.query.application.query.handler;

import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.query.QueryHandler;
import com.mateuszziomek.issuestracker.users.query.application.query.GetListUsersQuery;
import com.mateuszziomek.issuestracker.shared.readmodel.ListUser;
import com.mateuszziomek.issuestracker.users.query.readmodel.listuser.ListUserFilter;
import com.mateuszziomek.issuestracker.users.query.readmodel.listuser.ListUserFinder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetListUsersQueryHandler implements QueryHandler<GetListUsersQuery, List<ListUser>> {
    private final ListUserFinder listUserFinder;

    @Override
    public List<ListUser> handle(GetListUsersQuery query) {
        var filter = ListUserFilter
                .builder()
                .email(query.getEmail())
                .status(query.getStatus())
                .build();

        return listUserFinder.findByFilter(filter);
    }
}
