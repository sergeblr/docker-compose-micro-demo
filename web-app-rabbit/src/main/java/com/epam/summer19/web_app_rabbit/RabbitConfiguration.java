package com.epam.summer19.web_app_rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    /*RabbitMQ Props:*/
    @Value("${spring.rabbitmq.host}")
    String rabbitmqHost;

    @Value("${spring.rabbitmq.port}")
    int rabbitmqPort;

    @Value("${spring.rabbitmq.username}")
    String rabbitmqUsername;

    @Value("${spring.rabbitmq.password}")
    String rabbitmqPassword;

    @Value("${spring.rabbitmq.timeout}")
    String rabbitmqTimeout;


    /* RabbitMQ Config SENDER Start */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitmqHost);
        connectionFactory.setPort(rabbitmqPort);
        connectionFactory.setUsername(rabbitmqUsername);
        connectionFactory.setPassword(rabbitmqPassword);
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setReplyTimeout(Integer.parseInt(rabbitmqTimeout));
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        /*        rabbitTemplate.setExchange(rabbitmqExchange);*/
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jsonMessageConverter()
    {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        return new Jackson2JsonMessageConverter(mapper);
    }

}
