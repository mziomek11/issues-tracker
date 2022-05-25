package com.mateuszziomek.issuestracker.users.query.application.query.handler;

import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserRole;
import com.mateuszziomek.issuestracker.shared.infrastructure.security.exception.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.query.QueryHandler;
import com.mateuszziomek.issuestracker.users.query.application.query.GetListUsersQuery;
import com.mateuszziomek.issuestracker.shared.readmodel.user.ListUser;
import com.mateuszziomek.issuestracker.users.query.readmodel.listuser.ListUserFilter;
import com.mateuszziomek.issuestracker.users.query.readmodel.listuser.ListUserFinder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetListUsersQueryHandler implements QueryHandler<List<ListUser>, GetListUsersQuery> {
    private final ListUserFinder listUserFinder;

    /**
     * @throws AccessDeniedException if user role is not {@link UserRole#SYSTEM}
     */
    @Override
    public List<ListUser> handle(GetListUsersQuery query) {
        if (!UserRole.SYSTEM.equals(query.getUserRole())) {
            throw new AccessDeniedException();
        }

        var filter = ListUserFilter
                .builder()
                .email(query.getEmail())
                .status(query.getStatus())
                .build();

        return listUserFinder.findByFilter(filter);
    }
}
