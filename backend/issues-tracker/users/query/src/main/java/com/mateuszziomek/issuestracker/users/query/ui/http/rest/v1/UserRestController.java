package com.mateuszziomek.issuestracker.users.query.ui.http.rest.v1;

import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserRole;
import com.mateuszziomek.issuestracker.shared.infrastructure.security.SecurityHeaders;
import com.mateuszziomek.issuestracker.shared.infrastructure.security.exception.AccessDeniedException;
import com.mateuszziomek.issuestracker.shared.readmodel.ObjectId;
import com.mateuszziomek.issuestracker.shared.ui.user.http.rest.v1.param.GetListUsersParam;
import com.mateuszziomek.issuestracker.users.query.application.query.GetJWTQuery;
import com.mateuszziomek.issuestracker.users.query.application.query.GetUserIdFromJWTQuery;
import com.mateuszziomek.issuestracker.users.query.application.query.exception.InvalidCredentialsException;
import com.mateuszziomek.issuestracker.users.query.application.query.handler.GetJWTQueryHandler;
import com.mateuszziomek.issuestracker.users.query.application.query.handler.GetUserIdFromJWTQueryHandler;
import com.mateuszziomek.issuestracker.users.query.application.service.jwt.exception.InvalidJWTException;
import com.mateuszziomek.issuestracker.users.query.ui.http.rest.v1.dto.GetJWTDto;
import com.mateuszziomek.issuestracker.users.query.ui.http.rest.v1.mapper.GetJWTDtoMapper;
import com.mateuszziomek.issuestracker.users.query.ui.http.rest.v1.mapper.GetListUsersParamMapper;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.query.dispatcher.QueryDispatcher;
import com.mateuszziomek.issuestracker.shared.readmodel.user.ListUser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-management")
@RequiredArgsConstructor
public class UserRestController {
    private final QueryDispatcher queryDispatcher;

    /**
     * @throws AccessDeniedException if user is not {@link UserRole#SYSTEM}
     */
    @GetMapping("/users")
    public ResponseEntity<List<ListUser>> getListUsers(
            @RequestHeader(SecurityHeaders.ISSUES_TRACKER_USER_ROLE) UserRole userRole,
            GetListUsersParam param
    ) {
        if (!UserRole.SYSTEM.equals(userRole)) {
            throw new AccessDeniedException();
        }

        var getListUsersQuery = GetListUsersParamMapper.toQuery(param);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(queryDispatcher.dispatch(getListUsersQuery));
    }

    /**
     * @throws InvalidCredentialsException see {@link GetJWTQueryHandler#handle(GetJWTQuery)}
     */
    @PostMapping("/users/authentication")
    public ResponseEntity<String> getJWT(@RequestBody GetJWTDto dto) {
        var getJWTQuery = GetJWTDtoMapper.toQuery(dto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(queryDispatcher.dispatch(getJWTQuery));
    }

    /**
     * @throws AccessDeniedException if user is not {@link UserRole#SYSTEM}
     * @throws InvalidJWTException see {@link GetUserIdFromJWTQueryHandler#handle(GetUserIdFromJWTQuery)}
     */
    @GetMapping("/users/id")
    public ResponseEntity<ObjectId> getUserIdFromJWT(
            @RequestHeader(SecurityHeaders.ISSUES_TRACKER_USER_ROLE) UserRole userRole,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader
    ) {
        if (!UserRole.SYSTEM.equals(userRole)) {
            throw new AccessDeniedException();
        }

        var jwt = authHeader.replace("Bearer ", "");
        var getUserIdFromJWTQuery = new GetUserIdFromJWTQuery(jwt);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(queryDispatcher.dispatch(getUserIdFromJWTQuery));
    }
}
