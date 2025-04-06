package com.eg.hospital.messaging.jms.processor.exception;

/**
 * Custom runtime exception used to indicate issues during JMS message processing.
 *
 * This exception is typically thrown when:
 *     Message consumption or production fails
 *     There is an issue in sending or receiving JMS messages
 *     Error handling in listeners or message converters
 *
 * It is caught by the {@link GlobalExceptionHandler} to send a meaningful error response to the client.
 *
 * @author Sanjay
 */
public class JmsMessageException extends RuntimeException {

    /**
     * Constructs a new JmsMessageException with the specified detail message.
     *
     * @param message a user readable message back to the end user
     */
    public JmsMessageException(String message) {
        super(message);
    }

}