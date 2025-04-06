package com.eg.hospital.messaging.jms.processor.service;

import com.eg.hospital.messaging.jms.processor.dto.GroupMessageDTO;
import com.eg.hospital.messaging.jms.processor.dto.RequestDTO;
import com.eg.hospital.messaging.jms.processor.messaging.GroupMessageProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteGroupServiceImplTest {

    @Mock
    private GroupMessageProducer groupMessageProducer;

    @InjectMocks
    private DeleteGroupServiceImpl deleteGroupServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendCreateGroupMessage_success() {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setGroupId("H123");
        requestDTO.setParentGroupId("P123");

        deleteGroupServiceImpl.sendDeleteGroupMessage(requestDTO);

        ArgumentCaptor<GroupMessageDTO> captor =
                ArgumentCaptor.forClass(GroupMessageDTO.class);
        verify(groupMessageProducer, times(1)).sendMessage(captor.capture());

        var message = captor.getValue();
        assertEquals("H123", message.getGroupId());
        assertEquals("P123", message.getParentGroupId());
        assertEquals("DELETE", message.getOperation());
        assertNotNull(message.getTimestamp());
    }

    @Test
    void testSendCreateGroupMessage_sameGroupAndParentId_shouldThrowException() {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setGroupId("H123");
        requestDTO.setParentGroupId("H123");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                deleteGroupServiceImpl.sendDeleteGroupMessage(requestDTO)
        );

        assertEquals("Group ID and Parent Group ID cannot be the same.", exception.getMessage());
        verify(groupMessageProducer, never()).sendMessage(any());
    }
}
