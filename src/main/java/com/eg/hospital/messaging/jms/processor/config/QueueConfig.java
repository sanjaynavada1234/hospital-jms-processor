package com.eg.hospital.messaging.jms.processor.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration class that binds JMS queue properties defined under the
 * {@code spring.jms.queues} prefix in the application's configuration file.
 *
 * <p>
 * This is primarily used to externalize and manage the queue names used
 * throughout the messaging layer of the hospital management system.
 * </p>
 *
 * @author Sanjay
 */

@Component
@ConfigurationProperties(prefix = "spring.jms.queues")
@Getter
@Setter
public class QueueConfig {

    /**
     * The name of the JMS queue used for hospital management-related messaging operations.
     */
    private String hospitalManagement;
}
