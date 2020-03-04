package com.epam.summer19.web_app_rabbit.validators;

import com.epam.summer19.model.Item;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class ItemValidator implements Validator {

    public static final int ITEM_NAME_MAX_SIZE = 255;

    /* RabbitMQ wiring exchange: */
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange itemsExchange;

    @Value("${spring.rabbitmq.template.items.routingkey.findbynamekey}")
    String rabbitmqItemsFindByNameKey;

    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "itemName", "itemName.empty");
        ValidationUtils.rejectIfEmpty(errors, "itemPrice", "itemPrice.empty");
        Item item = (Item) target;

        if (StringUtils.hasLength(item.getItemName())
                && item.getItemName().length() > ITEM_NAME_MAX_SIZE) {
            errors.rejectValue("itemName", "itemName.maxSize255");
        }

        /** UNCOMMENT to add ExtraSymbols validation
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
        if (regex.matcher(item.getItemName()).find()) {
            errors.rejectValue("itemName", "itemName.incorrect");
        }*/

        if (item.getItemPrice() != null
                && item.getItemPrice().floatValue() < 0) {
            errors.rejectValue("itemPrice", "itemPrice.negative");
        }

        Item foundItem = (Item) template.convertSendAndReceive(
                itemsExchange.getName(), rabbitmqItemsFindByNameKey, item.getItemName());
        if(foundItem.getItemId() != -1 && item.getItemId() != foundItem.getItemId()) {
            errors.rejectValue("itemName", "itemName.alreadyInDB");
        }
    }
}

