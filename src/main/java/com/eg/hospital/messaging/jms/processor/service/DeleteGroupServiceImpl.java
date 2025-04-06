package com.eg.hospital.messaging.jms.processor.service;

import com.eg.hospital.messaging.jms.processor.dto.GroupMessageDTO;
import com.eg.hospital.messaging.jms.processor.dto.RequestDTO;
import com.eg.hospital.messaging.jms.processor.messaging.GroupMessageProducer;
import com.eg.hospital.messaging.jms.processor.util.JmsConstants;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Service implementation for handling group deletion.
 * <p>
 * This service is responsible for transforming a {@link RequestDTO}
 * into a {@link GroupMessageDTO}, adding operation type and timestamp,
 * and forwarding it to the {@link GroupMessageProducer} for dispatching to the JMS queue.
 * </p>
 * <p>
 * Following the Single Responsibility Principle, this class focuses solely on
 * business logic for preparing the delete message, while the producer handles
 * the actual JMS interaction.
 * </p>
 *
 * @author Sanjay Navada
 */
@Service
@AllArgsConstructor
public class DeleteGroupServiceImpl implements DeleteGroupService {

    private static final Logger log = LoggerFactory.getLogger(DeleteGroupServiceImpl.class);

    private final GroupMessageProducer groupMessageProducer;
    /**
     * Constructs and sends a group deletion message to the JMS queue.
     * <p>
     * The message includes:
     * <ul>
     *     <li>{@code groupId} and {@code parentGroupId} from the incoming {@link RequestDTO}</li>
     *     <li>{@code operation} set to "DELETE"</li>
     *     <li>{@code timestamp} set to the current UTC time in ISO-8601 format</li>
     * </ul>
     * This message is passed to {@link GroupMessageProducer} for dispatching to the queue.
     * </p>
     *
     * @param requestDTO the request containing group identification details for deletion
     */
    public void sendDeleteGroupMessage(RequestDTO requestDTO) {

        if (requestDTO.getGroupId().equalsIgnoreCase(requestDTO.getParentGroupId())) {
            throw new IllegalArgumentException("Group ID and Parent Group ID cannot be the same.");
        }

        GroupMessageDTO message = GroupMessageDTO.builder().
                groupId(requestDTO.getGroupId()).
                parentGroupId(requestDTO.getParentGroupId()).
                operation(JmsConstants.DELETE_OPERATION).
                timestamp(DateTimeFormatter.ISO_INSTANT.format(Instant.now())).
                build();

        log.info("Sending Delete Group message to Producer: {}", message );

        groupMessageProducer.sendMessage(message);

    }

}