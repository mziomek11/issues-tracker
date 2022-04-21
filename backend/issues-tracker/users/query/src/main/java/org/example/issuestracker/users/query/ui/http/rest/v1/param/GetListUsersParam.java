package org.example.issuestracker.users.query.ui.http.rest.v1.param;

import org.example.issuestracker.shared.domain.valueobject.UserStatus;

public record GetListUsersParam(String email, UserStatus status) { }
