package org.example.issuestracker.issues.command;

import org.example.issuestracker.issues.command.application.command.*;
import org.example.issuestracker.issues.command.infrastructure.command.IssueCommandGateway;
import org.example.issuestracker.issues.common.domain.issue.IssueType;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.UUID;

class IssueCommandApplication {
    public static void main(String[] args) {
        var context = new ClassPathXmlApplicationContext("beans.xml");
        var commandGateway = context.getBean("commandGateway", IssueCommandGateway.class);
        var uuid = UUID.randomUUID();
        var openCommand = OpenIssueCommand
                .builder()
                .issueName("asd")
                .issueContent("example content")
                .issueType(IssueType.BUG)
                .issueId(uuid)
                .build();

        var renameCommand = RenameIssueCommand
                .builder()
                .issueId(uuid)
                .issueName("new name")
                .build();

        var closeCommand = CloseIssueCommand
                .builder()
                .issueId(uuid)
                .build();

        var changeTypeCommand = ChangeIssueTypeCommand
                .builder()
                .issueId(uuid)
                .issueType(IssueType.ENHANCEMENT)
                .build();

        var commentCommand = CommentIssueCommand
                .builder()
                .issueId(uuid)
                .commentId(UUID.randomUUID())
                .commentContent("")
                .build();

        commandGateway.dispatch(openCommand);
        commandGateway.dispatch(renameCommand);
        commandGateway.dispatch(changeTypeCommand);
        commandGateway.dispatch(commentCommand);
        commandGateway.dispatch(closeCommand);
    }
}
