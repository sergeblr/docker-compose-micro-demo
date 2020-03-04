package com.epam.summer19.micro_orders;

import com.epam.summer19.dto.DateTimeFilterDTO;
import com.epam.summer19.dto.OrderDTO;
import com.epam.summer19.model.Item;
import com.epam.summer19.model.Order;
import com.epam.summer19.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

public class OrdersRabbitConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrdersRabbitConsumer.class);

    @Autowired
    private OrderService orderService;

    @Value("${spring.rabbitmq.template.orders.errormsg}")
    String rabbitmqOrdersErrorMsg;

    @Value("${spring.rabbitmq.template.orders.successfulmsg}")
    String rabbitmqOrdersSuccessfulMsg;

    @RabbitListener(queues = "#{ordersQueueGetAllDtoByDateTime.getName()}")
    public List<OrderDTO> ordersFindAllDTO(DateTimeFilterDTO dateTimeFilterDTO) {
        LOGGER.debug("OrdersRabbitConsumer: ordersFindAllDTO({})", dateTimeFilterDTO.toString());
        try {
            List<OrderDTO> ordersDTO = orderService.findOrdersDTOByDateTime(dateTimeFilterDTO.getStartDateTime(),
                    dateTimeFilterDTO.getEndDateTime());
            LOGGER.debug("OrdersRabbitConsumer: Successfully");
            return ordersDTO;
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.toString());
            return new ArrayList<>();
        }
    }

    @RabbitListener(queues = "#{ordersQueueAdd.getName()}")
    public Order ordersAdd(Order order) {
        LOGGER.debug("OrdersRabbitConsumer: Add order: {}", order);
        try {
            Order addedOrder = orderService.add(order);
            LOGGER.debug("OrdersRabbitConsumer: Successfully");
            return addedOrder;
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.toString());
            return new Order();
        }
    }

    @RabbitListener(queues = "#{ordersQueueUpdate.getName()}")
    public String ordersUpdate(Order order) {
        LOGGER.debug("OrdersRabbitConsumer: Update order: {}", order);
        try {
            orderService.update(order);
            LOGGER.debug("OrdersRabbitConsumer: Successfully");
            return rabbitmqOrdersSuccessfulMsg;
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.toString());
            return rabbitmqOrdersErrorMsg;
        }
    }

    @RabbitListener(queues = "#{ordersQueueDelete.getName()}")
    public String ordersDelete(Integer id) {
        LOGGER.debug("OrdersRabbitConsumer: Delete order by id: {}", id);
        try {
            orderService.delete(id);
            LOGGER.debug("OrdersRabbitConsumer: Successfully");
            return rabbitmqOrdersSuccessfulMsg;
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.toString());
            return rabbitmqOrdersErrorMsg;
        }
    }

    @RabbitListener(queues = "#{ordersQueueFindById.getName()}")
    public Order ordersFindById(Integer orderId) {
        LOGGER.debug("OrdersRabbitConsumer: Find by Id (EDIT): {}", orderId);
        try {
            Order updatableOrder = orderService.findOrderById(orderId);
            LOGGER.debug("OrdersRabbitConsumer: Successfully");
            return updatableOrder;
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.toString());
            return new Order();
        }
    }

}