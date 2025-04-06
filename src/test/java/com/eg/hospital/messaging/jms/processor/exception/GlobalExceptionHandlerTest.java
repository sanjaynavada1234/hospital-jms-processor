package com.eg.hospital.messaging.jms.processor.exception;

import com.eg.hospital.messaging.jms.processor.controller.GroupController;
import com.eg.hospital.messaging.jms.processor.dto.RequestDTO;
import com.eg.hospital.messaging.jms.processor.service.CreateGroupService;
import com.eg.hospital.messaging.jms.processor.service.DeleteGroupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @Mock
    private CreateGroupService createGroupService;

    @Mock
    private DeleteGroupService deleteGroupService;

    @InjectMocks
    private GroupController groupController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(groupController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testValidationException() throws Exception {
        RequestDTO invalidRequest = new RequestDTO();
        invalidRequest.setGroupId("");
        invalidRequest.setParentGroupId("PARENT");

        mockMvc.perform(post("/groups/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Group Id is empty")));
    }

    @Test
    void testIllegalArgumentException() throws Exception {
        RequestDTO request = new RequestDTO();
        request.setGroupId("ID-001");
        request.setParentGroupId("ID-001");

        doThrow(new IllegalArgumentException("Group ID and Parent ID cannot be the same"))
                .when(createGroupService).sendCreateGroupMessage(any());

        mockMvc.perform(post("/groups/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Group ID and Parent ID cannot be the same")));
    }

    @Test
    void testJmsMessageException() throws Exception {
        RequestDTO request = new RequestDTO();
        request.setGroupId("GROUP-1");
        request.setParentGroupId("PARENT-1");

        doThrow(new JmsMessageException("JMS failure"))
                .when(deleteGroupService).sendDeleteGroupMessage(any());

        mockMvc.perform(delete("/groups/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", containsString("JMS failure")));
    }

    @Test
    void testGenericException() throws Exception {
        RequestDTO request = new RequestDTO();
        request.setGroupId("GROUP");
        request.setParentGroupId("PARENT");

        doThrow(new RuntimeException("Unexpected error"))
                .when(createGroupService).sendCreateGroupMessage(any());

        mockMvc.perform(post("/groups/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", containsString("Unexpected error")));
    }
}