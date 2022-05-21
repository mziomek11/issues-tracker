package com.mateuszziomek.issuestracker.users.query.application.query.handler;

import com.mateuszziomek.cqrs.query.QueryHandler;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserRole;
import com.mateuszziomek.issuestracker.shared.infrastructure.security.exception.AccessDeniedException;
import com.mateuszziomek.issuestracker.shared.readmodel.ObjectId;
import com.mateuszziomek.issuestracker.users.query.application.query.GetUserIdFromJWTQuery;
import com.mateuszziomek.issuestracker.users.query.application.service.jwt.JWTService;
import com.mateuszziomek.issuestracker.users.query.application.service.jwt.exception.InvalidJWTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserIdFromJWTQueryHandler implements QueryHandler<GetUserIdFromJWTQuery, ObjectId> {
    private final JWTService jwtService;

    /**
     * @throws AccessDeniedException if user role is not {@link UserRole#SYSTEM}
     * @throws InvalidJWTException see {@link JWTService#getUserId(String)}
     */
    @Override
    public ObjectId handle(GetUserIdFromJWTQuery query) {
        if (!UserRole.SYSTEM.equals(query.getUserRole())) {
            throw new AccessDeniedException();
        }

        var userId = jwtService.getUserId(query.getJwt());

        return new ObjectId(userId);
    }
}
