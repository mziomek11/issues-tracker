package org.example.issuestracker.users.query.ui.http.rest.v1;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.query.dispatcher.QueryDispatcher;
import org.example.issuestracker.users.query.application.query.IsUserEmailAvailableQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-management")
@RequiredArgsConstructor
public class UserRestController {
    private final QueryDispatcher queryDispatcher;

    @GetMapping("/users-by-email/{userEmail}")
    public ResponseEntity<Boolean> isUserEmailAvailable(@PathVariable String userEmail) {
        var isUserEmailAvailableQuery = new IsUserEmailAvailableQuery(userEmail);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(queryDispatcher.dispatch(isUserEmailAvailableQuery));
    }
}
