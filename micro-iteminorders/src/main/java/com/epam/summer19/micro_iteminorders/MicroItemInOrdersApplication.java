package com.epam.summer19.micro_iteminorders;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@ComponentScan(basePackages = {"com.epam.summer19"})
@SpringBootApplication
@ImportResource(locations = {"classpath:test-db.xml"})
public class MicroItemInOrdersApplication {

    /*RabbitMQ Props:*/
    @Value("${spring.rabbitmq.host}")
    String rabbitmqHost;

    @Value("${spring.rabbitmq.port}")
    int rabbitmqPort;

    @Value("${spring.rabbitmq.username}")
    String rabbitmqUsername;

    @Value("${spring.rabbitmq.password}")
    String rabbitmqPassword;

    /*RabbitMQ ItemInOrders props*/
    @Value("${spring.rabbitmq.template.iteminorders.queue.findbyorderitemid}")
    String rabbitmqItemInOrdersFindByOrderItemIdQueue;

    @Value("${spring.rabbitmq.template.iteminorders.queue.findbyorderid}")
    String rabbitmqItemInOrdersFindByOrderIdQueue;

    @Value("${spring.rabbitmq.template.iteminorders.queue.add}")
    String rabbitmqItemInOrdersAddQueue;

    @Value("${spring.rabbitmq.template.iteminorders.queue.update}")
    String rabbitmqItemInOrdersUpdateQueue;

    @Value("${spring.rabbitmq.template.iteminorders.queue.delete}")
    String rabbitmqItemInOrdersDeleteQueue;
    /*RabbitMQ Props END*/

    public static void main(String[] args) {
        SpringApplication.run(MicroItemInOrdersApplication.class, args);
    }

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
        rabbitTemplate.setReplyTimeout(60 * 1000);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Queue itemInOrdersQueueFindByOrderItemId() {
        return new Queue(rabbitmqItemInOrdersFindByOrderItemIdQueue, true);
    }

    @Bean
    public Queue itemInOrdersQueueFindByOrderId() {
        return new Queue(rabbitmqItemInOrdersFindByOrderIdQueue, true);
    }

    @Bean
    public Queue itemInOrdersQueueAdd() {
        return new Queue(rabbitmqItemInOrdersAddQueue, true);
    }

    @Bean
    public Queue itemInOrdersQueueUpdate() {
        return new Queue(rabbitmqItemInOrdersUpdateQueue, true);
    }

    @Bean
    public Queue itemInOrdersQueueDelete() {
        return new Queue(rabbitmqItemInOrdersDeleteQueue, true);
    }

    @Bean
    public MessageConverter jsonMessageConverter()
    {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        return new Jackson2JsonMessageConverter(mapper);
    }
    /*RabbitMQ Config END*/

    @Bean
    public ItemInOrdersRabbitConsumer itemInOrdersRabbitConsumer() {
        return new ItemInOrdersRabbitConsumer();
    }

}
