package com.eg.hospital.messaging.jms.processor.service;

import com.eg.hospital.messaging.jms.processor.dto.RequestDTO;

/**
 * Service interface for handling group creation
 * <p>
 * The implementing classes are responsible for transforming incoming requests
 * into message payloads and sending it to the corresponding producer
 * </p>
 * @author Sanjay Navada
 */
public interface CreateGroupService {
    /**
     *  <p> Adds the operation type (CREATE) and timestamp to the message before sending. </p>
     *
     * @param requestDTO The input data containing group ID and parent group ID.
     */
    void sendCreateGroupMessage(RequestDTO requestDTO);
}