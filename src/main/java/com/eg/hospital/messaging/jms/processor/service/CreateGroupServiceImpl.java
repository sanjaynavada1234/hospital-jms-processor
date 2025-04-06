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
 * Service implementation for handling group creation logic.
 * <p>
 * This service transforms a {@link RequestDTO} into a {@link GroupMessageDTO},
 * adds metadata like operation type and timestamp, and
 * delegates the message dispatching responsibility to the {@link GroupMessageProducer}.
 * </p>
 * <p>
 * This class follows the Single Responsibility Principle by not containing any messaging infrastructure logic.
 * The actual JMS interaction is within the producer class.
 * </p>
 *
 * @author Sanjay Navada
 */

@Service
@AllArgsConstructor
public class CreateGroupServiceImpl implements CreateGroupService {

    private static final Logger log = LoggerFactory.getLogger(CreateGroupServiceImpl.class);

    private final GroupMessageProducer groupMessageProducer;

    /**
     * Constructs and sends a group creation message to the queue.
     * <p>
     * The message includes:
     * <ul>
     *     <li>{@code groupId} and {@code parentGroupId} from the incoming {@link RequestDTO}</li>
     *     <li>{@code operation} set to "CREATE"</li>
     *     <li>{@code timestamp} set to current UTC time in ISO-8601 format</li>
     * </ul>
     * This message is then passed to {@link GroupMessageProducer} for dispatching to the JMS queue.
     * </p>
     *
     * @param requestDTO the incoming group creation request
     */
    public void sendCreateGroupMessage(RequestDTO requestDTO) {

        if (requestDTO.getGroupId().equalsIgnoreCase(requestDTO.getParentGroupId())) {
            throw new IllegalArgumentException("Group ID and Parent Group ID cannot be the same.");
        }

        GroupMessageDTO message = GroupMessageDTO.builder().
                groupId(requestDTO.getGroupId()).
                parentGroupId(requestDTO.getParentGroupId()).
                operation(JmsConstants.CREATE_OPERATION).
                timestamp(DateTimeFormatter.ISO_INSTANT.format(Instant.now())).
                build();

        log.info("Sending Create Group message to Producer: {}", message);

        groupMessageProducer.sendMessage(message);


    }
}