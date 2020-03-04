package com.epam.summer19.micro_items;

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
public class MicroItemsApplication {

    /*RabbitMQ Props:*/
    @Value("${spring.rabbitmq.host}")
    String rabbitmqHost;

    @Value("${spring.rabbitmq.port}")
    int rabbitmqPort;

    @Value("${spring.rabbitmq.username}")
    String rabbitmqUsername;

    @Value("${spring.rabbitmq.password}")
    String rabbitmqPassword;

    /*RabbitMQ Items props*/

    @Value("${spring.rabbitmq.template.items.queue.getall}")
    String rabbitmqItemsGetAllQueue;

    @Value("${spring.rabbitmq.template.items.queue.add}")
    String rabbitmqItemsAddQueue;

    @Value("${spring.rabbitmq.template.items.queue.update}")
    String rabbitmqItemsUpdateQueue;

    @Value("${spring.rabbitmq.template.items.queue.delete}")
    String rabbitmqItemsDeleteQueue;

    @Value("${spring.rabbitmq.template.items.queue.findbyid}")
    String rabbitmqItemsFindByIdQueue;

    @Value("${spring.rabbitmq.template.items.queue.findbyname}")
    String rabbitmqItemsFindByNameQueue;
    /*RabbitMQ Props END*/

    public static void main(String[] args) {
        SpringApplication.run(MicroItemsApplication.class, args);
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
    public Queue itemsQueueGetAll() {
        return new Queue(rabbitmqItemsGetAllQueue, true);
    }

    @Bean
    public Queue itemsQueueAdd() {
        return new Queue(rabbitmqItemsAddQueue, true);
    }

    @Bean
    public Queue itemsQueueUpdate() {
        return new Queue(rabbitmqItemsUpdateQueue, true);
    }

    @Bean
    public Queue itemsQueueDelete() {
        return new Queue(rabbitmqItemsDeleteQueue, true);
    }

    @Bean
    public Queue itemsQueueFindById() {
        return new Queue(rabbitmqItemsFindByIdQueue, true);
    }

    @Bean
    public Queue itemsQueueFindByName() {
        return new Queue(rabbitmqItemsFindByNameQueue, true);
    }

    @Bean
    public MessageConverter jsonMessageConverter()
    {
        return new Jackson2JsonMessageConverter();
    }
    /*RabbitMQ Config END*/


    @Bean
    public ItemsRabbitConsumer itemsRabbitConsumer() {
        return new ItemsRabbitConsumer();
    }

}
