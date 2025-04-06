package com.eg.hospital.messaging.jms.processor.controller;

import com.eg.hospital.messaging.jms.processor.dto.ApiResponseDTO;
import com.eg.hospital.messaging.jms.processor.dto.RequestDTO;
import com.eg.hospital.messaging.jms.processor.service.CreateGroupService;
import com.eg.hospital.messaging.jms.processor.service.DeleteGroupService;
import com.eg.hospital.messaging.jms.processor.util.JmsConstants;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * This is a REST controller currently containing 2 methods POST and DELETE
 * <p>
 * Provides endpoints for creating and deleting group messages
 * </p>
 *
 * <ul>
 *      <li>{@code POST /groups/create} - Sends a group creation message to the JMS queue.</li>
 *      <li>{@code DELETE /groups/delete} - Sends a group deletion message to the JMS queue.</li>
 * </ul>
 *
 * @author Sanjay Navada
 */
@RestController
@RequestMapping("/groups")
@AllArgsConstructor
public class GroupController {

    private final CreateGroupService createGroupService;
    private final DeleteGroupService deleteGroupService;

    /**
     * Endpoint to send a group creation message to the queue.
     * <p>
     * We receive the group id and parent group id from the request body.
     * The Operation "CREATE" and Timestamp are added in {@link CreateGroupService}.
     * </p>
     *
     * @param requestDTO The request payload containing group details.
     * @return a {@link ResponseEntity} with creation confirmation message.
     */
    @PostMapping("/create")
    public ResponseEntity<ApiResponseDTO> createGroup(@Valid @RequestBody RequestDTO requestDTO) {
        createGroupService.sendCreateGroupMessage(requestDTO);
        ApiResponseDTO apiResponseDTO = ApiResponseDTO.builder()
                .status(HttpStatus.CREATED.value())
                .timestamp(DateTimeFormatter.ISO_INSTANT.format(Instant.now()))
                .code(JmsConstants.CREATION_SUCCESS)
                .message("Group creation message sent to queue successfully").build();
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.CREATED);
    }

    /**
     * Endpoint to send a group deletion message to the queue.
     * <p>
     * We receive the group id and parent group id from the request body.
     * The Operation "DELETE" and Timestamp are added in the service layer within {@link DeleteGroupService}.
     * </p>
     *
     * @param requestDTO The request payload containing group details.
     * @return a {@link ResponseEntity} with success message.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponseDTO> deleteGroup(@Valid @RequestBody RequestDTO requestDTO) {
        deleteGroupService.sendDeleteGroupMessage(requestDTO);
        ApiResponseDTO apiResponseDTO = ApiResponseDTO.builder()
                .status(HttpStatus.OK.value())
                .timestamp(DateTimeFormatter.ISO_INSTANT.format(Instant.now()))
                .code(JmsConstants.DELETION_SUCCESS)
                .message("Group deletion message sent to queue successfully").build();
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }
}