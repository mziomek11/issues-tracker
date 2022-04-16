package org.example.issuestracker.accounts.command.ui.http.rest.v1;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.dispatcher.CommandDispatcher;
import org.example.issuestracker.accounts.command.application.command.ActivateAccountCommand;
import org.example.issuestracker.accounts.command.application.command.OpenAccountCommand;
import org.example.issuestracker.accounts.command.application.command.handler.ActivateAccountCommandHandler;
import org.example.issuestracker.accounts.command.application.command.handler.OpenAccountCommandHandler;
import org.example.issuestracker.accounts.command.application.gateway.exception.AccountEmailAlreadyTakenException;
import org.example.issuestracker.accounts.command.domain.account.exception.AccountActivationTokenMismatchException;
import org.example.issuestracker.accounts.command.domain.account.exception.AccountAlreadyActivatedException;
import org.example.issuestracker.accounts.command.domain.account.exception.AccountNotFoundException;
import org.example.issuestracker.accounts.command.ui.http.rest.v1.dto.ActivateAccountDto;
import org.example.issuestracker.accounts.command.ui.http.rest.v1.dto.OpenAccountDto;
import org.example.issuestracker.accounts.command.ui.http.rest.v1.mapper.ActivateAccountDtoMapper;
import org.example.issuestracker.accounts.command.ui.http.rest.v1.mapper.OpenAccountDtoMapper;
import org.example.rest.v1.RestValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/account-management")
@RequiredArgsConstructor
public class AccountRestController {
    private final CommandDispatcher commandDispatcher;

    /**
     * @throws AccountEmailAlreadyTakenException see {@link OpenAccountCommandHandler#handle(OpenAccountCommand)}
     * @throws RestValidationException see {@link OpenAccountDtoMapper#toCommand(UUID, OpenAccountDto)}
     */
    @PostMapping("/accounts")
    public ResponseEntity<UUID> openAccount(@RequestBody OpenAccountDto openAccountDto) {
        var accountId = UUID.randomUUID();
        var openAccountCommand = OpenAccountDtoMapper.toCommand(accountId, openAccountDto);

        commandDispatcher.dispatch(openAccountCommand);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountId);
    }

    /**
     * @throws AccountActivationTokenMismatchException see {@link ActivateAccountCommandHandler#handle(ActivateAccountCommand)}
     * @throws AccountAlreadyActivatedException see {@link ActivateAccountCommandHandler#handle(ActivateAccountCommand)}
     * @throws AccountNotFoundException see {@link ActivateAccountCommandHandler#handle(ActivateAccountCommand)}
     * @throws RestValidationException see {@link OpenAccountDtoMapper#toCommand(UUID, OpenAccountDto)}
     */
    @PostMapping("/accounts/{accountId}/activation-token")
    public ResponseEntity<UUID> activateAccount(
            @PathVariable UUID accountId,
            @RequestBody ActivateAccountDto activateAccountDto
    ) {
        var activateAccountCommand = ActivateAccountDtoMapper.toCommand(
                accountId,
                activateAccountDto
        );

        commandDispatcher.dispatch(activateAccountCommand);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountId);
    }
}
