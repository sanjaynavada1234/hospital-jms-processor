package com.eg.hospital.messaging.jms.processor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
public class ApiResponseDTO {
    private String timestamp;
    private int status;
    private String code;
    private String message;


    public ApiResponseDTO()
    {
        this.timestamp = DateTimeFormatter.ISO_INSTANT.format(Instant.now());
    }

}
