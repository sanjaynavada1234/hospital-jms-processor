package com.eg.hospital.messaging.jms.processor.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ApiResponseDTOTest {

    @Test
    void testDefaultConstructorSetsTimestamp() {
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();

        assertThat(apiResponseDTO.getTimestamp()).isNotNull();
        assertThat(apiResponseDTO.getStatus()).isZero();
        assertThat(apiResponseDTO.getMessage()).isNull();
    }
}