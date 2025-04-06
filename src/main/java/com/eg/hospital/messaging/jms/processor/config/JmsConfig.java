package com.eg.hospital.messaging.jms.processor.config;

import jakarta.jms.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.stereotype.Component;

/**
 * Configuration class for setting up JMS (Java Message Service) related beans such as
 * {@link JmsTemplate}, {@link DefaultJmsListenerContainerFactory}, and {@link MessageConverter}.
 *
 * <p><b>Key Features:</b></p>
 * <ul>
 *     <li>Jackson-based message converter for serializing/deserializing message payloads</li>
 *     <li>Thread-safe and concurrent JMS listener factory</li>
 *     <li>Custom error handling for listener exceptions</li>
 *     <li>Transactional message processing</li>
 * </ul>
 *
 * @author Sanjay
 */
@Component
@Configuration
public class JmsConfig {

    private static final Logger logger = LoggerFactory.getLogger(JmsConfig.class);

    /**
     * Defines the Jackson-based message converter for JMS.
     * Converts Java objects to JSON and vice versa for message payloads.
     *
     * @return the configured {@link MessageConverter}
     */
    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    /**
     * Configures the JMS listener container factory with concurrency,
     * message conversion, transaction support, and custom error handling.
     *
     * @param connectionFactory          the connection factory for the JMS provider
     * @param jacksonJmsMessageConverter the message converter to use
     * @return the configured {@link DefaultJmsListenerContainerFactory}
     */
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory, MessageConverter jacksonJmsMessageConverter) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jacksonJmsMessageConverter);
        factory.setConcurrency("1-5");
        factory.setSessionTransacted(true);
        factory.setErrorHandler(t ->
                logger.error("Listener error{}: ", t.getMessage()));
        return factory;
    }

    /**
     * Configures the {@link JmsTemplate} used for sending messages to the broker.
     * Applies the Jackson message converter for JSON serialization.
     *
     * @param connectionFactory          the connection factory for the JMS provider
     * @param jacksonJmsMessageConverter the message converter to use
     * @return the configured {@link JmsTemplate}
     */
    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, MessageConverter jacksonJmsMessageConverter) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter);
        return jmsTemplate;
    }
}
