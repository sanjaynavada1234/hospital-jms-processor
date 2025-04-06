package com.eg.hospital.messaging.jms.processor.exception;

import com.eg.hospital.messaging.jms.processor.dto.ApiResponseDTO;
import com.eg.hospital.messaging.jms.processor.util.JmsConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Global exception handler for managing all application-wide exceptions.
 *
 * This class handles:
 *
 *     Validation errors (e.g., missing or invalid input fields)
 *     Custom JMS-related exceptions
 *     Generic fallback exceptions
 *
 * The responses include relevant status codes, error messages, and timestamps.
 *
 * @author Sanjay
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles validation errors thrown when @Valid fails in request body.
     *
     * @param ex the validation exception containing field errors
     * @return a response entity with a map of field names and corresponding error messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO> handleInputDataValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .sorted(Comparator.comparing(FieldError::getDefaultMessage))
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(" or "));
        ApiResponseDTO errorResponse = new ApiResponseDTO(DateTimeFormatter.ISO_INSTANT.format(Instant.now()), HttpStatus.BAD_REQUEST.value(), JmsConstants.VALIDATION_ERROR, errorMessage);
        logger.error("Validation failed: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles application-specific JMS messaging exceptions.
     *
     * @param ex the JmsMessageException containing error details
     * @return a response entity with timestamp, status, and custom error message
     */
    @ExceptionHandler(JmsMessageException.class)
    public ResponseEntity<ApiResponseDTO> handleJmsMessageException(JmsMessageException ex) {
        ApiResponseDTO errorResponse = new ApiResponseDTO(DateTimeFormatter.ISO_INSTANT.format(Instant.now()), HttpStatus.INTERNAL_SERVER_ERROR.value(), JmsConstants.JMS_MESSAGE_FAILURE, ex.getMessage());
        logger.error("JMS processing error: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Illegal argument Exception when the Group Id and Parent Id are equal.
     *
     * @param ex the illegal argument exception
     * @return an error response indicating group id and parent id cannot be the same
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDTO> handleIllegalArgument(IllegalArgumentException ex) {
        ApiResponseDTO errorResponse = new ApiResponseDTO(DateTimeFormatter.ISO_INSTANT.format(Instant.now()), HttpStatus.BAD_REQUEST.value(),JmsConstants.ILLEGAL_ARGUMENT, ex.getMessage());
        logger.error("Illegal argument: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other uncaught exceptions that are not explicitly handled.
     *
     * @param ex the exception
     * @return a generic error response with internal server error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO> handleGenericException(Exception ex) {
        ApiResponseDTO errorResponse = new ApiResponseDTO(DateTimeFormatter.ISO_INSTANT.format(Instant.now()), HttpStatus.INTERNAL_SERVER_ERROR.value(), JmsConstants.UNEXPECTED_ERROR, ex.getMessage());
        logger.error("Unhandled exception: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}