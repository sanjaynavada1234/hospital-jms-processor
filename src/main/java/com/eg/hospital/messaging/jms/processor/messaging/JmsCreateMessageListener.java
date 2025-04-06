package com.eg.hospital.messaging.jms.processor.messaging;

import com.eg.hospital.messaging.jms.processor.dto.GroupMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Listener component that handles incoming JMS messages for CREATE operations.
 * <p>
 * This class is annotated with {@link JmsListener} to listen specifically to messages
 * sent to the configured queue with a message selector filtering by operation = 'CREATE'.
 * </p>
 * <p>
 * Upon receiving a message, it logs the operation and processes the {@link GroupMessageDTO} payload.
 * </p>
 *
 * <p><b>Expected message metadata:</b>
 * <ul>
 *     <li>Group ID</li>
 *     <li>Parent Group ID</li>
 *     <li>Operation: CREATE</li>
 *     <li>Timestamp</li>
 * </ul>
 * </p>
 *
 * @author Sanjay Navada
 */
@Component
public class JmsCreateMessageListener implements GroupMessageListener {

    private static final Logger log = LoggerFactory.getLogger(JmsCreateMessageListener.class);

    /**
     * Processes a {@link GroupMessageDTO} received from the configured JMS queue where the
     * operation is CREATE.
     * <p>
     * Logs the received message and its operation type.
     * Additional business logic for CREATE operations can be added here.
     * </p>
     *
     * @param message the message payload containing group details and operation metadata
     */
    @Override
    @JmsListener(destination = "${spring.jms.queues.hospitalManagement}", selector = "operation = 'CREATE'")
    public void processMessage(GroupMessageDTO message) {
            log.info("Queue Listener for {} operation", message.getOperation());
            log.info("Thread: {} :Processing Message from the queue: {}",Thread.currentThread().getName(), message);
    }
}
