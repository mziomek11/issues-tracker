package org.example.issuestracker.organizations.command.ui.http.rest.v1;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.dispatcher.CommandDispatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/organization-management")
@RequiredArgsConstructor
public class OrganizationRestController {
    private final CommandDispatcher commandDispatcher;
}
