package org.example.issuestracker.issues.command;

import org.example.issuestracker.issues.command.command.OpenIssueCommand;
import org.example.issuestracker.issues.command.infrastructure.IssuesCommandGateway;
import org.example.issuestracker.issues.common.domain.IssueType;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.UUID;

class IssuesCommandApplication {
    public static void main(String[] args) {
        var context = new ClassPathXmlApplicationContext("beans.xml");
        var commandGateway = context.getBean("commandGateway", IssuesCommandGateway.class);
        var command = new OpenIssueCommand(UUID.randomUUID(), IssueType.BUG, "Example text");

        commandGateway.dispatch(command);
    }
}
