package com.eg.hospital.messaging.jms.processor.dto;

import lombok.*;

/**
 * DTO representing a group message in the hospital management system
 * This object is sent to the JMS Queue
 *
 * @author Sanjay Navada
 */
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupMessageDTO {

    /**
     * Unique identifier for the group.
     */
    private String groupId;

    /**
     * Identifier of the parent group to which this group belongs.
     */
    private String parentGroupId;

    /**
     * Type of operation to perform on the group (e.g., CREATE, DELETE).
     */
    private String operation;

    /**
     * ISO-8601 formatted timestamp representing when the operation was triggered.
     */
    private String timestamp;

}
