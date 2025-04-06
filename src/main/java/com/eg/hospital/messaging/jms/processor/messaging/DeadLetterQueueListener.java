package com.eg.hospital.messaging.jms.processor.messaging;

import com.eg.hospital.messaging.jms.processor.dto.GroupMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Listener component that listens to the Dead Letter Queue (DLQ) in ActiveMQ.
 * <p>
 * This is used to capture messages that failed to be delivered or processed
 * successfully in the primary listeners, even after the configured redelivery attempts.
 * </p>
 * <p>
 * The default DLQ destination in ActiveMQ is "ActiveMQ.DLQ". Messages sent here typically
 * indicate a processing failure.
 * </p>
 * <p>
 *
 * @author Sanjay
 */
@Component
public class DeadLetterQueueListener {

    private static final Logger log = LoggerFactory.getLogger(DeadLetterQueueListener.class);

    /**
     * Processes a {@link GroupMessageDTO} received when the messages are not processed by the primary listeners
     * <p>
     * Logs the received message and its operation type.
     * Additional business logic or RETRY operations can be added here.
     * </p>
     *
     * @param message the message payload containing group details and operation metadata
     */
    @JmsListener(destination = "ActiveMQ.DLQ")
    public void handleFailedMessage(GroupMessageDTO message) {
        log.info("Received message in Dead Letter Queue: {}", message);
    }
}
