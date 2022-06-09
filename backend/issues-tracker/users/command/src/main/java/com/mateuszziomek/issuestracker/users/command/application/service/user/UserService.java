package com.mateuszziomek.issuestracker.users.command.application.service.user;

import com.mateuszziomek.issuestracker.users.command.application.service.user.exception.UserEmailUnavailableException;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserEmail;
import com.mateuszziomek.issuestracker.users.command.projection.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * @throws UserEmailUnavailableException if account with given email is already taken
     */
    public void ensureUserEmailIsAvailable(UserEmail userEmail) {
        if (userRepository.findByEmail(userEmail.text()).isPresent()) {
            throw new UserEmailUnavailableException(userEmail);
        }
    }
}
