package org.example.issuestracker.users.query.readmodel.listuser;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ListUserFilter {
    private final String email;
}
