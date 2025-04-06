package com.eg.hospital.messaging.jms.processor.messaging;

import com.eg.hospital.messaging.jms.processor.dto.GroupMessageDTO;

/**
 * Interface for sending JMS messages to the queue.
 * <p>
 * Implementations of this interface are responsible for sending
 * messages containing group-related information such as group ID,
 * parent group ID, operation type (CREATE or DELETE), and timestamp
 * to a JMS destination (queue).
 * </p>
 *
 * <p>This interface supports decoupling the message sending logic
 * from the service layer, adhering to SOLID principles.</p>
 *
 * @author Sanjay Navada
 */
public interface GroupMessageProducer {
    /**
     * Sends a group-related message to the JMS destination.
     *
     * @param message the {@link GroupMessageDTO} containing the
     *                details of the group operation to be sent
     */
    void sendMessage(GroupMessageDTO message);
}
