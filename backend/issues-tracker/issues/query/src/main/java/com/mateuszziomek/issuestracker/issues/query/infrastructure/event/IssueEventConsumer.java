package com.mateuszziomek.issuestracker.issues.query.infrastructure.event;

import com.mateuszziomek.issuestracker.shared.domain.event.IssueOpenedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class IssueEventConsumer {}
