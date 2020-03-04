package com.epam.summer19.web_app_rabbit;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitItemsConfiguration {

    /*RabbitMQ Items props*/
    @Value("${spring.rabbitmq.template.items.exchange}")
    String rabbitmqItemsExchange;

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

    @Value("${spring.rabbitmq.template.items.routingkey.getallkey}")
    String rabbitmqItemsGetAllKey;

    @Value("${spring.rabbitmq.template.items.routingkey.addkey}")
    String rabbitmqItemsAddKey;

    @Value("${spring.rabbitmq.template.items.routingkey.updatekey}")
    String rabbitmqItemsUpdateKey;

    @Value("${spring.rabbitmq.template.items.routingkey.deletekey}")
    String rabbitmqItemsDeleteKey;

    @Value("${spring.rabbitmq.template.items.routingkey.findbyidkey}")
    String rabbitmqItemsFindByIdKey;

    @Value("${spring.rabbitmq.template.items.routingkey.findbynamekey}")
    String rabbitmqItemsFindByNameKey;
    /*RabbitMQ Props END*/

    /*RabbitMQ Items settings (exchange, queue & bindings create)*/
    @Bean
    public DirectExchange itemsExchange() {
        return new DirectExchange(rabbitmqItemsExchange);
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
    Binding bindingQueueItemsGetAll(DirectExchange itemsExchange) {
        return BindingBuilder.bind(itemsQueueGetAll()).to(itemsExchange).with(rabbitmqItemsGetAllKey);
    }

    @Bean
    Binding bindingQueueItemsAdd(DirectExchange itemsExchange) {
        return BindingBuilder.bind(itemsQueueAdd()).to(itemsExchange).with(rabbitmqItemsAddKey);
    }

    @Bean
    Binding bindingQueueItemsUpdate(DirectExchange itemsExchange) {
        return BindingBuilder.bind(itemsQueueUpdate()).to(itemsExchange).with(rabbitmqItemsUpdateKey);
    }

    @Bean
    Binding bindingQueueItemsDelete(DirectExchange itemsExchange) {
        return BindingBuilder.bind(itemsQueueDelete()).to(itemsExchange).with(rabbitmqItemsDeleteKey);
    }

    @Bean
    Binding bindingQueueItemsFindById(DirectExchange itemsExchange) {
        return BindingBuilder.bind(itemsQueueFindById()).to(itemsExchange).with(rabbitmqItemsFindByIdKey);
    }

    @Bean
    Binding bindingQueueItemsFindByName(DirectExchange itemsExchange) {
        return BindingBuilder.bind(itemsQueueFindByName()).to(itemsExchange).with(rabbitmqItemsFindByNameKey);
    }



}
