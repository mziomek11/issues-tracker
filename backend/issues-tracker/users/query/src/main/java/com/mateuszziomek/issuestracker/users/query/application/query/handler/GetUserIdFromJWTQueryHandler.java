package com.mateuszziomek.issuestracker.users.query.application.query.handler;

import com.mateuszziomek.cqrs.query.QueryHandler;
import com.mateuszziomek.issuestracker.shared.readmodel.ObjectId;
import com.mateuszziomek.issuestracker.users.query.application.query.GetUserIdFromJWTQuery;
import com.mateuszziomek.issuestracker.users.query.application.service.jwt.JWTService;
import com.mateuszziomek.issuestracker.users.query.application.service.jwt.exception.InvalidJWTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetUserIdFromJWTQueryHandler implements QueryHandler<GetUserIdFromJWTQuery, ObjectId> {
    private final JWTService jwtService;

    /**
     * @throws InvalidJWTException see {@link JWTService#getUserId(String)}
     */
    @Override
    public ObjectId handle(GetUserIdFromJWTQuery query) {
        var userId = jwtService.getUserId(query.getJwt());

        return new ObjectId(userId);
    }
}
