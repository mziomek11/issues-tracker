package com.mateuszziomek.issuestracker.users.query.application.query.handler;

import com.mateuszziomek.cqrs.query.QueryHandler;
import com.mateuszziomek.issuestracker.users.query.application.query.GetUserIdFromJWTQuery;
import com.mateuszziomek.issuestracker.users.query.application.service.jwt.JWTService;
import com.mateuszziomek.issuestracker.users.query.application.service.jwt.exception.InvalidJWTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetUserIdFromJWTQueryHandler implements QueryHandler<GetUserIdFromJWTQuery, UUID> {
    private final JWTService jwtService;

    /**
     * @throws InvalidJWTException see {@link JWTService#getUserId(String)}
     */
    @Override
    public UUID handle(GetUserIdFromJWTQuery query) {
        return jwtService.getUserId(query.getJwt());
    }
}
