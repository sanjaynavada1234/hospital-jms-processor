package com.eg.hospital.messaging.jms.processor.util;

/**
 * Utility class holding constant values used across the JMS messaging components
 * of the hospital management system.
 * <p>
 * This class is non-instantiable and provides static fields for standard operation
 * types, message statuses, and error identifiers.
 * </p>
 *
 * <p>Follows the utility class pattern by having a private constructor
 * and only static members.</p>
 *
 * @author Sanjay
 */
public class JmsConstants {

    private JmsConstants() {
    }

    public static final String CREATE_OPERATION = "CREATE";
    public static final String DELETE_OPERATION = "DELETE";
    public static final String OPERATION = "operation";
    public static final String CREATION_SUCCESS = "GROUP_CREATION_SUCCESS";
    public static final String DELETION_SUCCESS = "GROUP_DELETION_SUCCESS";
    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
    public static final String JMS_MESSAGE_FAILURE = "JMS_MESSAGE_FAILURE";
    public static final String ILLEGAL_ARGUMENT = "ILLEGAL_ARGUMENT";
    public static final String UNEXPECTED_ERROR = "UNEXPECTED_ERROR";
    public static final String LISTENER_ERROR = "LISTENER_ERROR";
}
