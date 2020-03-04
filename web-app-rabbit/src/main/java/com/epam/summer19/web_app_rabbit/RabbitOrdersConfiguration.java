package com.epam.summer19.web_app_rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitOrdersConfiguration {

    /*RabbitMQ Orders props*/
    @Value("${spring.rabbitmq.template.orders.exchange}")
    String rabbitmqOrdersExchange;

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

    @Value("${spring.rabbitmq.template.orders.routingkey.getalldtobydatetimekey}")
    String rabbitmqOrdersGetAllDtoByDateTimeKey;

    @Value("${spring.rabbitmq.template.orders.routingkey.addkey}")
    String rabbitmqOrdersAddKey;

    @Value("${spring.rabbitmq.template.orders.routingkey.updatekey}")
    String rabbitmqOrdersUpdateKey;

    @Value("${spring.rabbitmq.template.orders.routingkey.deletekey}")
    String rabbitmqOrdersDeleteKey;

    @Value("${spring.rabbitmq.template.orders.routingkey.findbyidkey}")
    String rabbitmqOrdersFindByIdKey;

    /*RabbitMQ Props END*/

    /*RabbitMQ Items settings (exchange, queue & bindings create)*/
    @Bean
    public DirectExchange ordersExchange() {
        return new DirectExchange(rabbitmqOrdersExchange);
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
    Binding bindingQueueOrdersGetAllDtoByDateTime(DirectExchange ordersExchange) {
        return BindingBuilder.bind(ordersQueueGetAllDtoByDateTime()).to(ordersExchange)
                .with(rabbitmqOrdersGetAllDtoByDateTimeKey);
    }

    @Bean
    Binding bindingOrdersQueueAdd(DirectExchange ordersExchange) {
        return BindingBuilder.bind(ordersQueueAdd()).to(ordersExchange).with(rabbitmqOrdersAddKey);
    }

    @Bean
    Binding bindingOrdersQueueUpdate(DirectExchange ordersExchange) {
        return BindingBuilder.bind(ordersQueueUpdate()).to(ordersExchange).with(rabbitmqOrdersUpdateKey);
    }

    @Bean
    Binding bindingOrdersQueueDelete(DirectExchange ordersExchange) {
        return BindingBuilder.bind(ordersQueueDelete()).to(ordersExchange).with(rabbitmqOrdersDeleteKey);
    }

    @Bean
    Binding bindingOrdersQueueFindById(DirectExchange ordersExchange) {
        return BindingBuilder.bind(ordersQueueFindById()).to(ordersExchange).with(rabbitmqOrdersFindByIdKey);
    }

}
