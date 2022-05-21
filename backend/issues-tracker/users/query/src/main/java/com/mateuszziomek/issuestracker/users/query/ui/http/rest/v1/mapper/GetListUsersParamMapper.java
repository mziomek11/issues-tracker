package com.mateuszziomek.issuestracker.users.query.ui.http.rest.v1.mapper;

import com.mateuszziomek.issuestracker.shared.ui.user.http.rest.v1.param.GetListUsersParam;
import com.mateuszziomek.issuestracker.users.query.application.query.GetListUsersQuery;

public class GetListUsersParamMapper {
    private GetListUsersParamMapper() {}

    public static GetListUsersQuery toQuery(GetListUsersParam param) {
        return GetListUsersQuery
                .builder()
                .email(param.getEmail())
                .status(param.getStatus())
                .build();
    }
}
