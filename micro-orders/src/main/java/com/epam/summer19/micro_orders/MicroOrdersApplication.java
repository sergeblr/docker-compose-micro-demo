package com.epam.summer19.micro_orders;

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
public class MicroOrdersApplication {

    /*RabbitMQ Props:*/
    @Value("${spring.rabbitmq.host}")
    String rabbitmqHost;

    @Value("${spring.rabbitmq.port}")
    int rabbitmqPort;

    @Value("${spring.rabbitmq.username}")
    String rabbitmqUsername;

    @Value("${spring.rabbitmq.password}")
    String rabbitmqPassword;

    /*RabbitMQ Orders props*/
    @Value("${spring.rabbitmq.template.orders.queue.getalldtobydatetime}")
    String rabbitmqOrdersGetAllDtoByDateTimeQueue;

    @Value("${spring.rabbitmq.template.orders.queue.add}")
    String rabbitmqOrdersAddQueue;

    @Value("${spring.rabbitmq.template.orders.queue.update}")
    String rabbitmqOrdersUpdateQueue;

    @Value("${spring.rabbitmq.template.orders.queue.delete}")
    String rabbitmqOrdersDeleteQueue;

    @Value("${spring.rabbitmq.template.orders.queue.findbyid}")
    String rabbitmqOrdersFindByIdQueue;
    /*RabbitMQ Props END*/

    public static void main(String[] args) {
        SpringApplication.run(MicroOrdersApplication.class, args);
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
    public Queue ordersQueueGetAllDtoByDateTime() {
        return new Queue(rabbitmqOrdersGetAllDtoByDateTimeQueue, true);
    }

    @Bean
    public Queue ordersQueueAdd() {
        return new Queue(rabbitmqOrdersAddQueue, true);
    }

    @Bean
    public Queue ordersQueueUpdate() {
        return new Queue(rabbitmqOrdersUpdateQueue, true);
    }

    @Bean
    public Queue ordersQueueDelete() {
        return new Queue(rabbitmqOrdersDeleteQueue, true);
    }

    @Bean
    public Queue ordersQueueFindById() {
        return new Queue(rabbitmqOrdersFindByIdQueue, true);
    }

    @Bean
    public MessageConverter jsonMessageConverter()
    {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        return new Jackson2JsonMessageConverter(mapper);
    }
    /*RabbitMQ Config END*/

    @Bean
    public OrdersRabbitConsumer ordersRabbitConsumer() {
        return new OrdersRabbitConsumer();
    }

}
