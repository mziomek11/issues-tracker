package org.example.issuestracker.users.query.readmodel.listuser;

import org.example.issuestracker.shared.readmodel.ListUser;
import org.example.issuestracker.users.query.domain.User;

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
