package com.mateuszziomek.issuestracker.shared.ui.user.http.rest.v1.param;

import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetListUsersParam {
    private String email;
    private UserStatus status;
}
