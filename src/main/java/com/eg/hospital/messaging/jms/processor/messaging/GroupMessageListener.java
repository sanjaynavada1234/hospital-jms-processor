package com.eg.hospital.messaging.jms.processor.messaging;

import com.eg.hospital.messaging.jms.processor.dto.GroupMessageDTO;

/**
 * Interface for processing incoming JMS messages related to group operations
 * such as CREATE or DELETE.
 *
 * <p>
 * Implementations of this interface should define the logic to be executed
 * when a {@link GroupMessageDTO} message is received from the queue.
 * </p>
 *
 * @author Sanjay
 */
public interface GroupMessageListener {
    /**
     * Processes the incoming {@link GroupMessageDTO} message.
     *
     * @param message the message containing group operation details like group ID,
     *                parent group ID, operation type (CREATE/DELETE), and timestamp
     */
    void processMessage(GroupMessageDTO message);
}