package com.mateuszziomek.issuestracker.issues.query.infrastructure.event;

import com.mateuszziomek.cqrs.event.dispatcher.ReactiveEventDispatcher;
import com.mateuszziomek.issuestracker.issues.query.application.event.handler.*;
import com.mateuszziomek.issuestracker.shared.domain.event.*;
import com.mateuszziomek.issuestracker.shared.domain.event.IssueCommentContentChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class IssueEventRegistry {
    private final ReactiveEventDispatcher eventDispatcher;
    private final IssueClosedEventHandler issueClosedEventHandler;
    private final IssueCommentedEventHandler issueCommentedEventHandler;
    private final IssueCommentContentChangedEventHandler issueCommentContentChangedEventHandler;
    private final IssueCommentHiddenEventHandler issueCommentHiddenEventHandler;
    private final IssueCommentVotedEventHandler issueCommentVotedEventHandler;
    private final IssueContentChangedEventHandler issueContentChangedEventHandler;
    private final IssueOpenedEventHandler issueOpenedEventHandler;
    private final IssueRenamedEventHandler issueRenamedEventHandler;
    private final IssueTypeChangedEventHandler issueTypeChangedEventHandler;
    private final IssueVotedEventHandler issueVotedEventHandler;
    private final OrganizationCreatedEventHandler organizationCreatedEventHandler;
    private final OrganizationMemberJoinedEventHandler organizationMemberJoinedEventHandler;
    private final OrganizationProjectCreatedEventHandler organizationProjectCreatedEventHandler;
    private final UserRegisteredEventHandler userRegisteredEventHandler;

    @PostConstruct
    public void registerHandlers() {
        eventDispatcher.registerHandler(IssueClosedEvent.class, issueClosedEventHandler);
        eventDispatcher.registerHandler(IssueCommentedEvent.class, issueCommentedEventHandler);
        eventDispatcher.registerHandler(IssueContentChangedEvent.class, issueContentChangedEventHandler);
        eventDispatcher.registerHandler(IssueCommentContentChangedEvent.class, issueCommentContentChangedEventHandler);
        eventDispatcher.registerHandler(IssueCommentVotedEvent.class, issueCommentVotedEventHandler);
        eventDispatcher.registerHandler(IssueCommentHiddenEvent.class, issueCommentHiddenEventHandler);
        eventDispatcher.registerHandler(IssueOpenedEvent.class, issueOpenedEventHandler);
        eventDispatcher.registerHandler(IssueRenamedEvent.class, issueRenamedEventHandler);
        eventDispatcher.registerHandler(IssueTypeChangedEvent.class, issueTypeChangedEventHandler);
        eventDispatcher.registerHandler(IssueVotedEvent.class, issueVotedEventHandler);
        eventDispatcher.registerHandler(OrganizationCreatedEvent.class, organizationCreatedEventHandler);
        eventDispatcher.registerHandler(OrganizationMemberJoinedEvent.class, organizationMemberJoinedEventHandler);
        eventDispatcher.registerHandler(OrganizationProjectCreatedEvent.class, organizationProjectCreatedEventHandler);
        eventDispatcher.registerHandler(UserRegisteredEvent.class, userRegisteredEventHandler);
    }
}
