package com.mateuszziomek.issuestracker.issues.query.domain;

import com.mateuszziomek.issuestracker.shared.domain.valueobject.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Vote {
    private UUID memberId;
    private VoteType type;
}
