package com.mateuszziomek.issuestracker.users.query.application.query;

import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserRole;
import com.mateuszziomek.issuestracker.shared.readmodel.user.ListUser;
import lombok.Builder;
import lombok.Getter;
import com.mateuszziomek.cqrs.query.Query;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserStatus;

import java.util.List;

@Getter
@Builder
public class GetListUsersQuery extends Query<List<ListUser>> {
    private final String email;
    private final UserStatus status;
    private final UserRole userRole;
}
