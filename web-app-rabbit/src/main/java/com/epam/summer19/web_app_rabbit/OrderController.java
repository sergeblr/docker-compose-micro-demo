package com.epam.summer19.web_app_rabbit;

import com.epam.summer19.model.Item;
import com.epam.summer19.dto.DateTimeFilterDTO;
import com.epam.summer19.model.ItemInOrder;
import com.epam.summer19.model.Order;
import com.epam.summer19.service.ItemInOrderService;
import com.epam.summer19.service.OrderService;
import com.epam.summer19.web_app_rabbit.validators.DateTimeFilterDTOValidator;
import com.epam.summer19.web_app_rabbit.validators.OrderValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


/** !!!!!!!!!!!!!!!!
 * Order controller ALMOST NOT touched for RabbitMQ changes, all RabbitMQ logic implemented in OrderConsumerToMicro as OrderService bean
 * ONLY Item RabbitMQ messaging used there (because logic implemented in ItemController instead Consumer
 !!!!!!!!!!!!!!!!! */
@Controller
@SessionAttributes("dateTimeFilterDTO")
public class OrderController {
    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemInOrderService itemInOrderService;

    @Autowired
    OrderValidator orderValidator;

    @Autowired
    DateTimeFilterDTOValidator dateTimeFilterDTOValidator;

    /* RabbitMQ wiring exchange ONLY FOR ITEMS one request: */
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange itemsExchange;

    @Value("${spring.rabbitmq.template.items.routingkey.getallkey}")
    String rabbitmqItemsGetAllKey;

    @ModelAttribute("dateTimeFilterDTO")
    DateTimeFilterDTO defDateTime() {
        DateTimeFilterDTO dateTimeFilterDTO = new DateTimeFilterDTO();
        dateTimeFilterDTO.setStartDateTime(LocalDateTime.now()
                .minusYears(1).truncatedTo(ChronoUnit.MINUTES));
        dateTimeFilterDTO.setEndDateTime(LocalDateTime.now()
                .plusHours(24).truncatedTo(ChronoUnit.MINUTES));
        return dateTimeFilterDTO;
    }

    @GetMapping(value = "/ordersdto")
    public final String listAllOrdersDTOByDateTime(
            @ModelAttribute("dateTimeFilterDTO") DateTimeFilterDTO dateTimeFilterDTO,
            Model model)
    {
        LOGGER.debug("OrderController: listAllOrdersDTOByDateTime({})", dateTimeFilterDTO);
        model.addAttribute("isFilterExpanded", false);
        model.addAttribute("dateTimeFilterDTO", dateTimeFilterDTO);
        model.addAttribute("ordersdto", orderService.findOrdersDTOByDateTime(
                dateTimeFilterDTO.getStartDateTime(), dateTimeFilterDTO.getEndDateTime()));
        return "ordersdto";
    }

    @PostMapping(value = "/ordersdtofilterbydatetime")
    public final String postAllOrdersDTOByDateTime(
            @ModelAttribute DateTimeFilterDTO dateTimeFilterDTO,
            BindingResult result, Model model)
    {
        LOGGER.debug("OrderController: postAllOrdersDTOByDateTime({}, {})", dateTimeFilterDTO, result);
        dateTimeFilterDTOValidator.validate(dateTimeFilterDTO, result);
        if (result.hasErrors()) {
            model.addAttribute("isFilterExpanded", true);
            return "ordersdto";
        } else {
            model.addAttribute("ordersdto",orderService.findOrdersDTOByDateTime(
                    dateTimeFilterDTO.getStartDateTime(), dateTimeFilterDTO.getEndDateTime()));
            return "ordersdto";
        }
    }

    @GetMapping(value = "/ordersdtofilterreset")
    public final String ordersDtoFilterReset(Model model)
    {
        DateTimeFilterDTO dateTimeFilterDTO = defDateTime();
        LOGGER.debug("OrderController: ordersDtoFilterReset(), (DTO: {})", dateTimeFilterDTO);
        model.addAttribute("isFilterExpanded", true);
        model.addAttribute("dateTimeFilterDTO", dateTimeFilterDTO);
        model.addAttribute("ordersdto", orderService.findOrdersDTOByDateTime(
                dateTimeFilterDTO.getStartDateTime(), dateTimeFilterDTO.getEndDateTime()));
        return "ordersdto";
    }

    /**
     * GOTO Order add page
     * @param model
     * @return
     */
    @GetMapping(value = "/order")
    public final String gotoAddOrderPage(Model model) {
        LOGGER.debug("OrderController: gotoAddOrderPage({})", model);
        Order order = new Order();
        model.addAttribute("isNew", true);
        model.addAttribute("order", order);
        return "order";
    }

    /**
     * Add order
     * @param order
     * @param result
     * @return
     */
    @PostMapping(value = "/order")
    public final String addOrder(@Valid Order order, BindingResult result) {
        LOGGER.debug("OrderController: addOrder({}, {})", order, result);
        orderValidator.validate(order, result);
        if (result.hasErrors()) {
            return "order";
        } else {
            order = orderService.add(order);
            return "redirect:/order/"+order.getOrderId();
        }

    }

    /**
     * GOTO Edit order page
     * @param id
     * @param model
     * @return
     */
    @GetMapping(value = "/order/{id}")
    public final String gotoEditOrderPage(@PathVariable Integer id, Model model) {
        LOGGER.debug("OrderController: gotoEditOrderPage({})", id);
        Order order = orderService.findOrderById(id);
        List<ItemInOrder> iteminorders = itemInOrderService.findIioByOrderId(id);
        /* !!! RabbitMQ ITEMS template request there (because itemService is not implemented as RabbitMQ CONSUMER) !!! */
        List<Item> items =  (List<Item>) template.convertSendAndReceive(
                itemsExchange.getName(), rabbitmqItemsGetAllKey, "msg");
        ItemInOrder iteminorderin = new ItemInOrder();
        model.addAttribute("isNew", false);
        model.addAttribute("iteminorders", iteminorders);
        model.addAttribute("iteminorderin", iteminorderin);
        model.addAttribute("items", items);
        model.addAttribute("order", order);
        return "order";
    }

    /**
     * Update edited order
     * @param order
     * @param result
     * @return
     */
    @PostMapping(value = "/order/{id}")
    public final String updateOrder(@Valid Order order, BindingResult result) {
        LOGGER.debug("OrderController: updateOrder({})", order);
        orderValidator.validate(order, result);
        if (result.hasErrors()) {
            return "order";
        } else {
            this.orderService.update(order);
        }
        return "redirect:/ordersdto";
    }

    /**
     * Delete order
     * @param id
     * @param model
     * @return
     */
    @GetMapping(value = "/orders/{id}/delete")
    public final String deleteOrder(@PathVariable Integer id, Model model) {
        LOGGER.debug("OrderController: deleteOrder({},{})", id);
        orderService.delete(id);
        return "redirect:/ordersdto";
    }

}
