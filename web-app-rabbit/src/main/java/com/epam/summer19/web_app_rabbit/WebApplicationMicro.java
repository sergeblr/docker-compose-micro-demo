package com.epam.summer19.web_app_rabbit;

import com.epam.summer19.web_app_rabbit.consumers.ItemInOrderConsumerToMicro;
import com.epam.summer19.web_app_rabbit.consumers.OrderConsumerToMicro;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@SpringBootApplication
@Configuration
public class WebApplicationMicro {

    public static void main(String[] args) {
        SpringApplication.run(WebApplicationMicro.class, args);
    }

/*  !!!!!!!!!!
    As example ItemConsumer BEAN removed, RabbitMQ code implemented directly in ItemController.java (without consumer SUBlayer)
    @Bean
    public ItemConsumerToMicro itemService() {
        ...
        return itemService;
    }*/

    @Bean
    public OrderConsumerToMicro orderService() {
/*        OrderRestConsumer orderService = new OrderRestConsumer(restUrl+restOrders, restTemplate());*/
        OrderConsumerToMicro orderService = new OrderConsumerToMicro();
        return orderService;
    }

    @Bean
    public ItemInOrderConsumerToMicro itemInOrderService() {
        ItemInOrderConsumerToMicro itemInOrderService = new ItemInOrderConsumerToMicro();
        return itemInOrderService;
    }

}
