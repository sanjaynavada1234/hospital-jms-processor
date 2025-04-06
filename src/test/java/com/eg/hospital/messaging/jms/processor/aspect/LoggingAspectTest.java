package com.eg.hospital.messaging.jms.processor.aspect;

import com.eg.hospital.messaging.jms.processor.dto.RequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoggingAspectTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private LogCaptor logCaptor;

    @BeforeEach
    void setUp() {
        logCaptor = LogCaptor.forClass(com.eg.hospital.messaging.jms.processor.aspect.LoggingAspect.class);
        logCaptor.clearLogs();
    }

    @Test
    void testServiceLoggingAspectLogsInput() throws Exception {
        RequestDTO request = new RequestDTO();
        request.setGroupId("G123");
        request.setParentGroupId("PG123");

        mockMvc.perform(post("/groups/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        assertThat(logCaptor.getInfoLogs())
                .anyMatch(log -> log.contains("G123"));
    }

    @Test
    void testControllerLoggingAspectLogsInput() throws Exception {
        RequestDTO request = new RequestDTO();
        request.setGroupId("G-300");
        request.setParentGroupId("P-300");

        mockMvc.perform(post("/groups/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        assertThat(logCaptor.getInfoLogs())
                .anyMatch(log -> log.contains("called with request body") || log.contains("G-300"));
    }

    @Test
    void testDeleteGroupLoggingAspectLogsInput() throws Exception {
        RequestDTO request = new RequestDTO();
        request.setGroupId("G999");
        request.setParentGroupId("PG999");

        mockMvc.perform(delete("/groups/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        assertThat(logCaptor.getInfoLogs())
                .anyMatch(log -> log.contains("G999") || log.contains("called with request body"));
    }
}