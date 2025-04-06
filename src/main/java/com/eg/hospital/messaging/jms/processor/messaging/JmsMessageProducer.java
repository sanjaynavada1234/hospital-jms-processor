package com.eg.hospital.messaging.jms.processor.messaging;

import com.eg.hospital.messaging.jms.processor.config.QueueConfig;
import com.eg.hospital.messaging.jms.processor.dto.GroupMessageDTO;
import com.eg.hospital.messaging.jms.processor.exception.JmsMessageException;
import com.eg.hospital.messaging.jms.processor.util.JmsConstants;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link GroupMessageProducer} interface responsible for sending messages
 * to the JMS queue using Spring's {@link JmsTemplate}.
 * <p>
 * This producer has the logic for sending a {@link GroupMessageDTO} object
 * to the configured queue, with additional metadata (operation type).
 * </p>
 * <p>
 * In case of any failure during message production, it throws a {@link JmsMessageException}
 * with a custom error message to aid in error tracking.
 * </p>
 *
 * <p><b>Example message metadata:</b>
 * <ul>
 *     <li>Group ID</li>
 *     <li>Parent Group ID</li>
 *     <li>Operation (CREATE/DELETE) (added when the DTO is created)</li>
 *     <li>Timestamp (added when the DTO is created)</li>
 * </ul>
 * </p>
 *
 * @author Sanjay
 */
@Component
@AllArgsConstructor
public class JmsMessageProducer implements GroupMessageProducer {

    private static final Logger log = LoggerFactory.getLogger(JmsMessageProducer.class);

    private final JmsTemplate jmsTemplate;
    private final QueueConfig queueConfig;


    /**
     * Sends the configured message from the service layer to the configured JMS queue.
     * <p>
     * Adds the operation type as a string property to the message for filtering by listeners.
     * If sending fails, logs the error and throws a custom {@link JmsMessageException}.
     * </p>
     *
     * @param queueMessage the message payload containing group ID, parent group ID, operation, and timestamp
     * @throws JmsMessageException if message sending fails
     */
    @Override
    public void sendMessage(GroupMessageDTO queueMessage) {
        try {
            String queueName = queueConfig.getHospitalManagement();

            log.info("Sending Message to the queue: {}", queueName);
            jmsTemplate.convertAndSend(queueName, queueMessage, message -> {
                message.setStringProperty(JmsConstants.OPERATION, queueMessage.getOperation());
                return message;
            });
            log.info("Message: {} sent successfully to the Queue", queueMessage);
        } catch (Exception e) {
            log.error(" Failed to send message to queue: {}", e.getMessage());
            throw new JmsMessageException("Failed while performing " + queueMessage.getOperation() + " operation for Group Id: " + queueMessage.getGroupId());
        }

    }

}