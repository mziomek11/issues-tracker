package com.mateuszziomek.issuestracker.users.query.readmodel.listuser;

import com.mateuszziomek.issuestracker.shared.readmodel.user.ListUser;
import com.mateuszziomek.issuestracker.users.query.domain.User;

public class ListUserMapper {
    private ListUserMapper() {}

    public static ListUser fromModel(User user) {
        return ListUser
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }
}
