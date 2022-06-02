package com.mateuszziomek.issuestracker.users.query.ui.http.rest.v1.mapper;

import com.mateuszziomek.issuestracker.users.query.application.query.GetJWTQuery;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.user.GetJWTDto;

public class GetJWTDtoMapper {
    private GetJWTDtoMapper() {}

    public static GetJWTQuery toQuery(GetJWTDto dto) {
        return GetJWTQuery
                .builder()
                .email(dto.email())
                .password(dto.password())
                .build();
    }
}
