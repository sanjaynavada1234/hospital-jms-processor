package com.eg.hospital.messaging.jms.processor.messaging;

import com.eg.hospital.messaging.jms.processor.config.QueueConfig;
import com.eg.hospital.messaging.jms.processor.dto.GroupMessageDTO;
import com.eg.hospital.messaging.jms.processor.exception.JmsMessageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jms.core.JmsTemplate;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class JmsMessageProducerTest {

    @Mock
    private JmsTemplate jmsTemplate;

    @Mock
    private QueueConfig queueConfig;

    @InjectMocks
    private JmsMessageProducer jmsMessageProducer;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendMessage_success()
    {
        String queueName = "hospital.management.queue";

        GroupMessageDTO queueMessage = GroupMessageDTO.builder()
                .groupId("G123")
                .parentGroupId("PG123")
                .operation("CREATE")
                .timestamp(DateTimeFormatter.ISO_INSTANT.format(Instant.now()))
                .build();

        when(queueConfig.getHospitalManagement()).thenReturn(queueName);

        jmsMessageProducer.sendMessage(queueMessage);

        verify(jmsTemplate, times(1)).convertAndSend(eq(queueName), eq(queueMessage), any());

    }

    @Test
    void testSendMessage_failure()
    {
        String queueName = "hospital.management.queue";

        GroupMessageDTO queueMessage = GroupMessageDTO.builder()
                .groupId("G123")
                .parentGroupId("PG123")
                .operation("CREATE")
                .timestamp(DateTimeFormatter.ISO_INSTANT.format(Instant.now()))
                .build();

        when(queueConfig.getHospitalManagement()).thenReturn(queueName);

        doThrow(new RuntimeException("JMS error")).when(jmsTemplate).convertAndSend(anyString(), any(), any());

        JmsMessageException thrown = assertThrows(JmsMessageException.class, () ->
                jmsMessageProducer.sendMessage(queueMessage)
        );

        assertTrue(thrown.getMessage().contains("Failed while performing CREATE operation"));
    }

}
