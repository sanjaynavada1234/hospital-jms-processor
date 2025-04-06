package com.eg.hospital.messaging.jms.processor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 *  DTO representing a group message in the hospital management system
 *  This object is sent to the JMS Queue
 *  @author Sanjay Navada
 */
@Data
public class RequestDTO {

    /** Unique identifier for the group */
    @NotBlank(message = "Group Id is empty")
    @Pattern(regexp = "^[a-zA-Z0-9\\-]+$", message = "Group ID contains invalid characters")
    @Size(min = 3, max = 50, message = "Group ID must be between 3 and 50 characters")
    private String groupId;

    /** Parent group identifier */
    @Pattern(regexp = "^[a-zA-Z0-9\\-]+$", message = "Parent Group ID contains invalid characters")
    @NotBlank(message = "Parent Group Id is empty")
    @Size(min = 3, max = 50, message = "Parent Group ID must be between 3 and 50 characters")
    private String parentGroupId;

}
