package com.mateuszziomek.issuestracker.users.query.application.query.handler;

import com.mateuszziomek.cqrs.query.QueryHandler;
import com.mateuszziomek.issuestracker.users.query.application.query.GetJWTQuery;
import com.mateuszziomek.issuestracker.users.query.application.query.exception.InvalidCredentialsException;
import com.mateuszziomek.issuestracker.users.query.application.service.jwt.JWTService;
import com.mateuszziomek.issuestracker.users.query.domain.PasswordVerifier;
import com.mateuszziomek.issuestracker.users.query.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetJWTQueryHandler implements QueryHandler<GetJWTQuery, String> {
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final PasswordVerifier passwordVerifier;

    /**
     * @throws InvalidCredentialsException if user with given email and password does not exist
     */
    @Override
    public String handle(GetJWTQuery query) {
        var user = userRepository
                .findByEmail(query.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        if (!user.isActivated()) {
            throw new InvalidCredentialsException();
        }

        if (!passwordVerifier.matches(query.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return jwtService.encode(user.getId());
    }
}
