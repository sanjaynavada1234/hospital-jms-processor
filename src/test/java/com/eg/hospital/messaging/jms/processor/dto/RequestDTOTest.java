package com.eg.hospital.messaging.jms.processor.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RequestDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validRequestDTO_shouldPassValidation() {
        RequestDTO dto = new RequestDTO();
        dto.setGroupId("GROUP-123");
        dto.setParentGroupId("PARENT-456");

        Set<ConstraintViolation<RequestDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty(), "There should be no violations for a valid DTO");
    }

    @Test
    void blankGroupId_shouldFailValidation() {
        RequestDTO dto = new RequestDTO();
        dto.setGroupId("");
        dto.setParentGroupId("PARENT-456");

        Set<ConstraintViolation<RequestDTO>> violations = validator.validate(dto);

        List<String> messages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        assertFalse(violations.isEmpty());
        assertTrue(messages.contains("Group Id cannot be blank") || messages.contains("Group ID contains invalid characters") || messages.contains("Group ID must be between 3 and 50 characters"));
    }

    @Test
    void invalidCharactersInGroupId_shouldFailValidation() {
        RequestDTO dto = new RequestDTO();
        dto.setGroupId("GROUP@123");
        dto.setParentGroupId("PARENT-456");

        Set<ConstraintViolation<RequestDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("contains invalid characters")));
    }

    @Test
    void tooShortGroupId_shouldFailValidation() {
        RequestDTO dto = new RequestDTO();
        dto.setGroupId("G1");
        dto.setParentGroupId("PARENT-456");

        Set<ConstraintViolation<RequestDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Group ID must be between 3 and 50 characters")));
    }

    @Test
    void blankParentGroupId_shouldFailValidation() {
        RequestDTO dto = new RequestDTO();
        dto.setGroupId("GROUP-123");
        dto.setParentGroupId("");

        Set<ConstraintViolation<RequestDTO>> violations = validator.validate(dto);

        List<String> messages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        assertFalse(violations.isEmpty());
        assertTrue(messages.contains("Parent Group Id cannot be blank") || messages.contains("Parent Group ID contains invalid characters") || messages.contains("Parent Group ID must be between 3 and 50 characters."));

    }
}
