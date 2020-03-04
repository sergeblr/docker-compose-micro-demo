package com.epam.summer19.web_app_rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitItemInOrdersConfiguration {

    /*RabbitMQ Orders props*/
    @Value("${spring.rabbitmq.template.iteminorders.exchange}")
    String rabbitmqItemInOrdersExchange;

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

    @Value("${spring.rabbitmq.template.iteminorders.routingkey.findbyorderitemidkey}")
    String rabbitmqItemInOrdersFindByOrderItemIdKey;

    @Value("${spring.rabbitmq.template.iteminorders.routingkey.findbyorderidkey}")
    String rabbitmqItemInOrdersFindByOrderIdKey;

    @Value("${spring.rabbitmq.template.iteminorders.routingkey.addkey}")
    String rabbitmqItemInOrdersAddKey;

    @Value("${spring.rabbitmq.template.iteminorders.routingkey.updatekey}")
    String rabbitmqItemInOrdersUpdateKey;

    @Value("${spring.rabbitmq.template.iteminorders.routingkey.deletekey}")
    String rabbitmqItemInOrdersDeleteKey;
    /*RabbitMQ Props END*/

    /*RabbitMQ ItemInOrders settings (exchange, queue & bindings create)*/
    @Bean
    public DirectExchange itemInOrdersExchange() {
        return new DirectExchange(rabbitmqItemInOrdersExchange);
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
    Binding bindingItemInOrdersQueueFindByOrderItemId(DirectExchange itemInOrdersExchange) {
        return BindingBuilder.bind(itemInOrdersQueueFindByOrderItemId()).to(itemInOrdersExchange)
                .with(rabbitmqItemInOrdersFindByOrderItemIdKey);
    }

    @Bean
    Binding bindingItemInOrdersQueueFindByOrderId(DirectExchange itemInOrdersExchange) {
        return BindingBuilder.bind(itemInOrdersQueueFindByOrderId()).to(itemInOrdersExchange)
                .with(rabbitmqItemInOrdersFindByOrderIdKey);
    }

    @Bean
    Binding bindingItemInOrdersQueueAdd(DirectExchange itemInOrdersExchange) {
        return BindingBuilder.bind(itemInOrdersQueueAdd()).to(itemInOrdersExchange).with(rabbitmqItemInOrdersAddKey);
    }

    @Bean
    Binding bindingItemInOrdersQueueUpdate(DirectExchange itemInOrdersExchange) {
        return BindingBuilder.bind(itemInOrdersQueueUpdate()).to(itemInOrdersExchange).with(rabbitmqItemInOrdersUpdateKey);
    }

    @Bean
    Binding bindingItemInOrdersQueueDelete(DirectExchange itemInOrdersExchange) {
        return BindingBuilder.bind(itemInOrdersQueueDelete()).to(itemInOrdersExchange).with(rabbitmqItemInOrdersDeleteKey);
    }

}
