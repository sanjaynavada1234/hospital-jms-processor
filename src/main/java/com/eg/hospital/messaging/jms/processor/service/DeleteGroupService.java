package com.eg.hospital.messaging.jms.processor.service;

import com.eg.hospital.messaging.jms.processor.dto.RequestDTO;

/**
 * Service interface for handling group deletion logic.
 * <p>
 * Implementations of this interface are responsible for preparing
 * and sending group deletion messages to the JMS queue.
 * </p>
 * <p>
 * The {@link RequestDTO} contains the required information such as
 * {@code groupId} and {@code parentGroupId}, which are used to construct
 * a JMS message indicating a group deletion request.
 * </p>
 *
 * @author Sanjay Navada
 */
public interface DeleteGroupService {
    /**
     * Sends a group deletion message based on the provided request.
     * <p>
     * This method prepares the message payload with metadata like
     * operation type (DELETE) and a timestamp, and sends it to the message producer.
     * </p>
     *
     * @param requestDTO the incoming request containing group details
     */
    void sendDeleteGroupMessage(RequestDTO requestDTO);
}
