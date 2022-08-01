package com.mateuszziomek.issuestracker.users.command.ui.http.rest.v1;

import com.mateuszziomek.cqrs.command.dispatcher.CommandDispatcher;
import com.mateuszziomek.issuestracker.shared.readmodel.ObjectId;
import com.mateuszziomek.issuestracker.users.command.application.service.user.exception.UserEmailUnavailableException;
import com.mateuszziomek.issuestracker.users.command.ui.http.rest.v1.mapper.RegisterUserDtoMapper;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.issuestracker.users.command.application.command.RegisterUserCommand;
import com.mateuszziomek.issuestracker.users.command.application.command.handler.RegisterUserCommandHandler;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.user.RegisterUserDto;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.validation.RestValidationException;
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
     * @throws UserEmailUnavailableException see {@link RegisterUserCommandHandler#handle(RegisterUserCommand)}
     * @throws RestValidationException see {@link RegisterUserDtoMapper#toCommand(UUID, RegisterUserDto)}
     */
    @PostMapping("/users")
    public ResponseEntity<ObjectId> registerUser(@RequestBody RegisterUserDto registerUserDto) {
        var accountId = UUID.randomUUID();
        var registerUserCommand = RegisterUserDtoMapper.toCommand(accountId, registerUserDto);

        commandDispatcher.dispatch(registerUserCommand);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ObjectId(accountId));
    }
}
