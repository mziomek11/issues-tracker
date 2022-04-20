package org.example.issuestracker.users.command.ui.http.rest.v1;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.dispatcher.CommandDispatcher;
import org.example.issuestracker.users.command.application.command.ActivateUserCommand;
import org.example.issuestracker.users.command.application.command.RegisterUserCommand;
import org.example.issuestracker.users.command.application.command.handler.ActivateUserCommandHandler;
import org.example.issuestracker.users.command.application.command.handler.RegisterUserCommandHandler;
import org.example.issuestracker.users.command.application.gateway.user.exception.UserEmailNotAvailableException;
import org.example.issuestracker.users.command.domain.user.exception.UserActivationTokenMismatchException;
import org.example.issuestracker.users.command.domain.user.exception.UserAlreadyActivatedException;
import org.example.issuestracker.users.command.domain.user.exception.UserNotFoundException;
import org.example.issuestracker.users.command.ui.http.rest.v1.dto.ActivateUserDto;
import org.example.issuestracker.users.command.ui.http.rest.v1.dto.RegisterUserDto;
import org.example.issuestracker.users.command.ui.http.rest.v1.mapper.ActivateUserDtoMapper;
import org.example.issuestracker.users.command.ui.http.rest.v1.mapper.RegisterUserDtoMapper;
import org.example.rest.v1.RestValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user-management")
@RequiredArgsConstructor
public class UserRestController {
    private final CommandDispatcher commandDispatcher;

    /**
     * @throws UserEmailNotAvailableException see {@link RegisterUserCommandHandler#handle(RegisterUserCommand)}
     * @throws RestValidationException see {@link RegisterUserDtoMapper#toCommand(UUID, RegisterUserDto)}
     */
    @PostMapping("/users")
    public ResponseEntity<UUID> registerUser(@RequestBody RegisterUserDto registerUserDto) {
        var accountId = UUID.randomUUID();
        var registerUserCommand = RegisterUserDtoMapper.toCommand(accountId, registerUserDto);

        commandDispatcher.dispatch(registerUserCommand);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountId);
    }

    /**
     * @throws UserActivationTokenMismatchException see {@link ActivateUserCommandHandler#handle(ActivateUserCommand)}
     * @throws UserAlreadyActivatedException see {@link ActivateUserCommandHandler#handle(ActivateUserCommand)}
     * @throws UserNotFoundException see {@link ActivateUserCommandHandler#handle(ActivateUserCommand)}
     * @throws RestValidationException see {@link RegisterUserDtoMapper#toCommand(UUID, RegisterUserDto)}
     */
    @PostMapping("/users/{userId}/activation-token")
    public ResponseEntity activateUser(
            @PathVariable UUID userId,
            @RequestBody ActivateUserDto activateUserDto
    ) {
        var activateUserCommand = ActivateUserDtoMapper.toCommand(
                userId,
                activateUserDto
        );

        commandDispatcher.dispatch(activateUserCommand);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
