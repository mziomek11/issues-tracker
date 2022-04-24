package com.mateuszziomek.issuestracker.users.query.ui.http.rest.v1.param;

import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserStatus;

public record GetListUsersParam(String email, UserStatus status) { }
