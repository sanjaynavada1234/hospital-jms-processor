package com.eg.hospital.messaging.jms.processor.controller;

import com.eg.hospital.messaging.jms.processor.dto.ApiResponseDTO;
import com.eg.hospital.messaging.jms.processor.dto.RequestDTO;
import com.eg.hospital.messaging.jms.processor.service.CreateGroupService;
import com.eg.hospital.messaging.jms.processor.service.DeleteGroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GroupControllerTest {

    @InjectMocks
    private GroupController groupController;

    @Mock
    private CreateGroupService createGroupService;

    @Mock
    private DeleteGroupService deleteGroupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateGroup() {

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setGroupId("GROUP-123");
        requestDTO.setParentGroupId("PARENT-456");

        doNothing().when(createGroupService).sendCreateGroupMessage(requestDTO);

        ResponseEntity<ApiResponseDTO> response = groupController.createGroup(requestDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Group creation message sent to queue successfully");
        assertThat(response.getBody().getStatus()).isEqualTo(HttpStatus.CREATED.value());

        verify(createGroupService, times(1)).sendCreateGroupMessage(requestDTO);
    }

    @Test
    void testDeleteGroup() {

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setGroupId("GROUP-789");
        requestDTO.setParentGroupId("PARENT-999");

        doNothing().when(deleteGroupService).sendDeleteGroupMessage(requestDTO);

        ResponseEntity<ApiResponseDTO> response = groupController.deleteGroup(requestDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Group deletion message sent to queue successfully");
        assertThat(response.getBody().getStatus()).isEqualTo(HttpStatus.OK.value());

        verify(deleteGroupService, times(1)).sendDeleteGroupMessage(requestDTO);
    }

}