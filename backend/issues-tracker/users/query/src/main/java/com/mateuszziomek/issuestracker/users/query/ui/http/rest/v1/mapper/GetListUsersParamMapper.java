package com.mateuszziomek.issuestracker.users.query.ui.http.rest.v1.mapper;

import com.mateuszziomek.issuestracker.users.query.application.query.GetListUsersQuery;
import com.mateuszziomek.issuestracker.users.query.ui.http.rest.v1.param.GetListUsersParam;

public class GetListUsersParamMapper {
    private GetListUsersParamMapper() {}

    public static GetListUsersQuery toQuery(GetListUsersParam param) {
        return GetListUsersQuery
                .builder()
                .email(param.email())
                .status(param.status())
                .build();
    }
}
