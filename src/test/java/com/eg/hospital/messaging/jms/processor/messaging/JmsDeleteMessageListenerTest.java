package com.eg.hospital.messaging.jms.processor.messaging;

import com.eg.hospital.messaging.jms.processor.dto.GroupMessageDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class JmsDeleteMessageListenerTest {

    private JmsDeleteMessageListener listener;

    @BeforeEach
    void setUp() {
        listener = new JmsDeleteMessageListener();
    }

    @Test
    void testProcessMessage_logsSuccessfully() {
        GroupMessageDTO message = GroupMessageDTO.builder()
                .groupId("G123")
                .parentGroupId("PG123")
                .operation("DELETE")
                .timestamp(DateTimeFormatter.ISO_INSTANT.format(Instant.now()))
                .build();

        assertDoesNotThrow(() -> listener.processMessage(message));
    }
}
