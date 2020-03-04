package com.epam.summer19.micro_iteminorders;

import com.epam.summer19.dto.DateTimeFilterDTO;
import com.epam.summer19.dto.OrderDTO;
import com.epam.summer19.model.ItemInOrder;
import com.epam.summer19.model.Order;
import com.epam.summer19.service.ItemInOrderService;
import com.epam.summer19.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

public class ItemInOrdersRabbitConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemInOrdersRabbitConsumer.class);

    @Autowired
    private ItemInOrderService itemInOrderService;

    @Value("${spring.rabbitmq.template.iteminorders.errormsg}")
    String rabbitmqItemInOrdersErrorMsg;

    @Value("${spring.rabbitmq.template.iteminorders.successfulmsg}")
    String rabbitmqItemInOrdersSuccessfulMsg;



    @RabbitListener(queues = "#{itemInOrdersQueueAdd.getName()}")
    public String itemInOrdersAdd(ItemInOrder itemInOrder) {
        LOGGER.debug("ItemInOrdersRabbitConsumer: Add {}", itemInOrder);
        try {
            itemInOrderService.add(itemInOrder);
            LOGGER.debug("ItemInOrdersRabbitConsumer: Successfully");
            return rabbitmqItemInOrdersSuccessfulMsg;
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.toString());
            return rabbitmqItemInOrdersErrorMsg;
        }
    }

    @RabbitListener(queues = "#{itemInOrdersQueueUpdate.getName()}")
    public String itemInOrdersUpdate(ItemInOrder itemInOrder) {
        LOGGER.debug("ItemInOrdersRabbitConsumer: Update ItemInOrder {}", itemInOrder);
        try {
            itemInOrderService.update(itemInOrder);
            LOGGER.debug("ItemInOrdersRabbitConsumer: Successfully");
            return rabbitmqItemInOrdersSuccessfulMsg;
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.toString());
            return rabbitmqItemInOrdersErrorMsg;
        }
    }

    @RabbitListener(queues = "#{itemInOrdersQueueDelete.getName()}")
    public String itemInOrdersDelete(ItemInOrder itemInOrder) {
        LOGGER.debug("ItemInOrdersRabbitConsumer: Delete ItemInOrder: {}", itemInOrder);
        try {
            itemInOrderService.delete(itemInOrder.getIioOrderId(), itemInOrder.getIioItemId());
            LOGGER.debug("ItemInOrdersRabbitConsumer: Successfully");
            return rabbitmqItemInOrdersSuccessfulMsg;
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.toString());
            return rabbitmqItemInOrdersErrorMsg;
        }
    }

    @RabbitListener(queues = "#{itemInOrdersQueueFindByOrderItemId.getName()}")
    public ItemInOrder itemInOrdersFindByOrderItemId(ItemInOrder findItemInOrder) {
        LOGGER.debug("ItemInOrdersRabbitConsumer: itemInOrderFindByOrderItemId({}, {})",
                findItemInOrder.getIioOrderId(), findItemInOrder.getIioItemId());
        try {
            ItemInOrder foundItemInOrder = itemInOrderService.findIioByOrderItemId(findItemInOrder.getIioOrderId(),
                    findItemInOrder.getIioItemId());
            LOGGER.debug("ItemInOrdersRabbitConsumer: Successfully");
            if(foundItemInOrder != null) {
                return foundItemInOrder;
            } else {
                ItemInOrder nullItemInOrder = new ItemInOrder();
                nullItemInOrder.setIioOrderId(-1);
                nullItemInOrder.setIioItemId(-1);
                return nullItemInOrder;
            }
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.toString());
            return new ItemInOrder();
        }
    }

    @RabbitListener(queues = "#{itemInOrdersQueueFindByOrderId.getName()}")
    public List<ItemInOrder> itemInOrdersFindByOrderId(Integer orderId) {
        LOGGER.debug("ItemInOrdersRabbitConsumer: itemInOrdersFindByOrderId({})", orderId);
        try {
            List<ItemInOrder> itemInOrders = itemInOrderService.findIioByOrderId(orderId);
            LOGGER.debug("ItemInOrdersRabbitConsumer: Successfully");
            return itemInOrders;
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.toString());
            return new ArrayList<>();
        }
    }

}