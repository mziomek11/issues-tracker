package org.example.issuestracker.issues.command;

import org.example.issuestracker.issues.command.application.command.CloseIssueCommand;
import org.example.issuestracker.issues.command.application.command.OpenIssueCommand;
import org.example.issuestracker.issues.command.infrastructure.command.IssueCommandGateway;
import org.example.issuestracker.issues.common.domain.IssueType;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.UUID;

class IssueCommandApplication {
    public static void main(String[] args) {
        var context = new ClassPathXmlApplicationContext("beans.xml");
        var commandGateway = context.getBean("commandGateway", IssueCommandGateway.class);
        var uuid = UUID.randomUUID();
        var openCommand = new OpenIssueCommand(uuid, IssueType.BUG, "Example text");
        var goodCloseCommand = new CloseIssueCommand(uuid);

        commandGateway.dispatch(openCommand);
        commandGateway.dispatch(goodCloseCommand);
    }
}
