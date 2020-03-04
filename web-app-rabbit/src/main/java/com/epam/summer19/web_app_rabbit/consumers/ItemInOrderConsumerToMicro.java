package com.epam.summer19.web_app_rabbit.consumers;

import com.epam.summer19.model.ItemInOrder;
import com.epam.summer19.service.ItemInOrderService;
import com.epam.summer19.web_app_rabbit.ItemInOrderController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * ItemInOrder Consumer RabbitMQ code in THIS file for ItemInOrders
 */

public class ItemInOrderConsumerToMicro implements ItemInOrderService {

    /*RabbitMQ Orders props*/
    @Value("${spring.rabbitmq.template.iteminorders.exchange}")
    String rabbitmqItemInOrdersExchange;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemInOrderController.class);

    /* RabbitMQ wiring: */
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange itemInOrdersExchange;

    public ItemInOrderConsumerToMicro() {

    }


    // DEPRECATED
    @Override
    public List<ItemInOrder> findAll() {
/*        LOGGER.debug("ItemInOrderMicroConsumer: findAll()");
        return restTemplate.getForEntity(url + "/all", List.class).getBody();*/
        return new ArrayList<>();
    }

    @Override
    public void add(ItemInOrder iio) {
        LOGGER.debug("ItemInOrderMicroConsumer: add({})", iio);
        /*RabbitMQ add ItemInOrder MSG.  String feedbackResult is necessary to receive returning "object", else convertSend*** will be delayed ~30sec */
        String feedbackResult = (String) template.convertSendAndReceive(
                itemInOrdersExchange.getName(), rabbitmqItemInOrdersAddKey, iio);
/*        restTemplate.postForEntity(url, iio, ItemInOrder.class);*/
    }

    @Override
    public void update(ItemInOrder iio) {
        LOGGER.debug("ItemInOrderMicroConsumer: update({})", iio);
        /*RabbitMQ update iteminorder MSG. String feedbackResult is necessary to receive returning "object", else convertSend*** will be delayed ~30sec */
        String feedbackResult = (String) template.convertSendAndReceive(
                itemInOrdersExchange.getName(), rabbitmqItemInOrdersUpdateKey, iio);
/*        restTemplate.put(url, iio);*/
    }

    @Override
    public void delete(Integer iioOrderId, Integer iioItemId) {
        LOGGER.debug("ItemInOrderMicroConsumer: delete({}{})", iioOrderId, iioItemId);
        /*RabbitMQ delete iteminorder MSG. String feedbackResult is necessary to receive returning "object", else convertSend*** will be delayed ~30sec */
        ItemInOrder deletableItemInOrder = new ItemInOrder();
        deletableItemInOrder.setIioOrderId(iioOrderId);
        deletableItemInOrder.setIioItemId(iioItemId);
        String feedbackResult = (String) template.convertSendAndReceive(
                itemInOrdersExchange.getName(), rabbitmqItemInOrdersDeleteKey, deletableItemInOrder);
/*        restTemplate.delete(url + "/" + iioOrderId + "/" + iioItemId);*/
    }

    @Override
    public List<ItemInOrder> findIioByOrderId(Integer iioOrderId) {
        LOGGER.debug("ItemInOrderMicroConsumer: findIioByOrderId({})", iioOrderId);
        /*RabbitMQ find ItemInOrders by OrderId MSG. */
        List<ItemInOrder> itemInOrders = (List<ItemInOrder>) template.convertSendAndReceive(
                itemInOrdersExchange.getName(), rabbitmqItemInOrdersFindByOrderIdKey, iioOrderId);
        return itemInOrders;
/*        return restTemplate.getForEntity(url + "/" + iioOrderId, List.class).getBody();*/
    }

    @Override
    public ItemInOrder findIioByOrderItemId(Integer iioOrderId, Integer iioItemId) {
        LOGGER.debug("ItemInOrderMicroConsumer: findIioByOrderItemId({}{})", iioOrderId, iioItemId);
        /*  RabbitMQ find ItemInOrder by OrderID & ItemID MSG.
        * Creating findItemInOrder to send object with OrderID & ItemID via ItemInOrder object in message */
        ItemInOrder findItemInOrder = new ItemInOrder();
        findItemInOrder.setIioOrderId(iioOrderId);
        findItemInOrder.setIioItemId(iioItemId);
        ItemInOrder receivedItemInOrder = (ItemInOrder) template.convertSendAndReceive(
                itemInOrdersExchange.getName(), rabbitmqItemInOrdersFindByOrderItemIdKey, findItemInOrder);
        if(receivedItemInOrder.getIioOrderId() != -1)
            return receivedItemInOrder;
        else
            return null;
/*        return restTemplate.getForEntity(
                url + "/" + iioOrderId + "/" + iioItemId, ItemInOrder.class).getBody();*/
    }


}