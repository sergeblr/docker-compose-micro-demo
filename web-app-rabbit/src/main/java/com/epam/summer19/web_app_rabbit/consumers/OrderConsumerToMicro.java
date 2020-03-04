package com.epam.summer19.web_app_rabbit.consumers;

import com.epam.summer19.dto.DateTimeFilterDTO;
import com.epam.summer19.dto.OrderDTO;
import com.epam.summer19.model.Order;
import com.epam.summer19.service.OrderService;
import com.epam.summer19.web_app_rabbit.OrderController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Order Consumer RabbitMQ code in THIS file for Orders
 */

public class OrderConsumerToMicro implements OrderService {

    /*RabbitMQ Orders props*/
    @Value("${spring.rabbitmq.template.orders.exchange}")
    String rabbitmqOrdersExchange;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    /* RabbitMQ wiring: */
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange ordersExchange;

    public OrderConsumerToMicro() {

    }

    // DEPRECATED
    @Override
    public List<Order> findAll() {
/*        LOGGER.debug("OrderRestConsumer: findAll()");
        return restTemplate.getForEntity(url, List.class).getBody();*/
        return new ArrayList<>();
    }

    // DEPRECATED
    @Override
    public List<OrderDTO> findAllDTO() {
/*        LOGGER.debug("OrderRestConsumer: findAllDTO()");
        return restTemplate.getForEntity(url+"dto", List.class).getBody();*/
        return new ArrayList<>();
    }

    @Override
    public List<OrderDTO> findOrdersDTOByDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LOGGER.debug("Rabbited OrderRestConsumer: findOrdersDTOByDateTime({},{})", startDateTime, endDateTime);
        /*RabbitMQ send MSG and wait result (list DTOs)*/
        DateTimeFilterDTO dateTimeFilterDTO = new DateTimeFilterDTO();
        dateTimeFilterDTO.setStartDateTime(startDateTime);
        dateTimeFilterDTO.setEndDateTime(endDateTime);
        List<OrderDTO> orders = (List<OrderDTO>) template.convertSendAndReceive(
                ordersExchange.getName(), rabbitmqOrdersGetAllDtoByDateTimeKey, dateTimeFilterDTO);
        return orders;
        /* OLD code: return restTemplate.getForEntity(
           url + "dto/" + startDateTime + ":00/" + endDateTime + ":00", List.class).getBody();*/
    }

    @Override
    public Order add(Order order) {
        LOGGER.debug("OrderRestConsumer: add({})", order);
        /*RabbitMQ add order MSG. */
        Order returnedOrder = (Order) template.convertSendAndReceive(
                ordersExchange.getName(), rabbitmqOrdersAddKey, order);
        return returnedOrder;
        /* OLD code: return restTemplate.postForEntity(url, order, Order.class).getBody();*/
    }

    @Override
    public void update(Order order) {
        LOGGER.debug("OrderRestConsumer: update({})", order);
        /*RabbitMQ delete order MSG. String feedbackResult is necessary to receive returning "object", else convertSend*** will be delayed ~30sec */
        String feedbackResult = (String) template.convertSendAndReceive(
                ordersExchange.getName(), rabbitmqOrdersUpdateKey, order);
        /* OLD code: restTemplate.put(url, order);*/
    }

    @Override
    public void delete(Integer orderId) {
        LOGGER.debug("OrderRestConsumer: delete({})", orderId);
        /*RabbitMQ delete order MSG. String feedbackResult is necessary to receive returning "object", else convertSend*** will be delayed ~30sec */
        String feedbackResult = (String) template.convertSendAndReceive(
                ordersExchange.getName(), rabbitmqOrdersDeleteKey, orderId);
        /* OLD code: restTemplate.delete(url + "/" + orderId);*/
    }

    @Override
    public Order findOrderById(Integer orderId) {
        LOGGER.debug("OrderRestConsumer: findOrderById({})", orderId);
        /*RabbitMQ findOrderById MSG returning found order */
        Order order = (Order) template.convertSendAndReceive(
                ordersExchange.getName(), rabbitmqOrdersFindByIdKey, orderId);
        return order;
        /* OLD code: return restTemplate.getForEntity(url + "/" + orderId, Order.class).getBody();*/
    }

}