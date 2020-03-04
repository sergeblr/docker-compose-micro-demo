package com.epam.summer19.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

/**
 * model test for Cafe Menu.
 */
public class ItemTest {

    @Autowired
    private Item item = new Item();

    @Test
    public void getItemId() {
        item.setItemId(24);
        assertTrue(item.getItemId().equals(24));
    }

    @Test
    public void getItemName() {
        item.setItemName("Item");
        assertTrue(item.getItemName().equals("Item"));
    }

    @Test
    public void getItemPrice() {
        item.setItemPrice(new BigDecimal("5.0"));
        assertTrue(item.getItemPrice().compareTo(new BigDecimal("5.0")) == 0);
    }

}
