package org.example.issuestracker.accounts.command.ui.http.rest.v1;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandGateway;
import org.example.issuestracker.accounts.command.application.command.OpenAccountCommand;
import org.example.issuestracker.accounts.command.application.command.handler.OpenAccountCommandHandler;
import org.example.issuestracker.accounts.command.application.gateway.exception.AccountEmailAlreadyTakenException;
import org.example.issuestracker.accounts.command.ui.http.rest.v1.dto.OpenAccountDto;
import org.example.issuestracker.accounts.command.ui.http.rest.v1.mapper.OpenAccountDtoMapper;
import org.example.rest.v1.RestValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/account-management")
@RequiredArgsConstructor
public class AccountRestController {
    private final CommandGateway commandGateway;

    /**
     * @throws AccountEmailAlreadyTakenException see {@link OpenAccountCommandHandler#handle(OpenAccountCommand)}
     * @throws RestValidationException see {@link OpenAccountDtoMapper#toCommand(UUID, OpenAccountDto)}
     */
    @PostMapping("/accounts")
    public ResponseEntity<UUID> openAccount(@RequestBody OpenAccountDto openAccountDto) {
        var accountId = UUID.randomUUID();
        var openAccountCommand = OpenAccountDtoMapper.toCommand(accountId, openAccountDto);

        commandGateway.dispatch(openAccountCommand);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountId);
    }
}
