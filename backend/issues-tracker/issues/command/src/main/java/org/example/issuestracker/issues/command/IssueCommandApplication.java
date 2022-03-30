package org.example.issuestracker.issues.command;

import org.example.issuestracker.issues.command.application.command.ChangeIssueTypeCommand;
import org.example.issuestracker.issues.command.application.command.CloseIssueCommand;
import org.example.issuestracker.issues.command.application.command.OpenIssueCommand;
import org.example.issuestracker.issues.command.application.command.RenameIssueCommand;
import org.example.issuestracker.issues.command.infrastructure.command.IssueCommandGateway;
import org.example.issuestracker.issues.common.domain.IssueType;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.UUID;

class IssueCommandApplication {
    public static void main(String[] args) {
        var context = new ClassPathXmlApplicationContext("beans.xml");
        var commandGateway = context.getBean("commandGateway", IssueCommandGateway.class);
        var uuid = UUID.randomUUID();
        var openCommand = new OpenIssueCommand(uuid, IssueType.BUG, "Example text", "Example title");
        var renameCommand = new RenameIssueCommand(uuid, "new name");
        var closeCommand = new CloseIssueCommand(uuid);
        var changeTypeCommand = new ChangeIssueTypeCommand(uuid, IssueType.ENHANCEMENT);

        commandGateway.dispatch(openCommand);
        commandGateway.dispatch(renameCommand);
        commandGateway.dispatch(changeTypeCommand);;
        commandGateway.dispatch(closeCommand);
    }
}
