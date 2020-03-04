package com.epam.summer19.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

public class ItemInOrderTest {

    @Autowired
    private ItemInOrder iteminorder = new ItemInOrder();

    @Test
    public void getIioOrderId() {
        iteminorder.setIioOrderId(3);
        assertTrue(iteminorder.getIioOrderId().equals(3));
    }

    @Test
    public void getIioItemId() {
        iteminorder.setIioItemId(17);
        assertTrue(iteminorder.getIioItemId().equals(17));
    }

    @Test
    public void getIioItemName() {
        iteminorder.setIioItemName("Burger");
        assertTrue(iteminorder.getIioItemName().equals("Burger"));
    }

    @Test
    public void getIioItemPrice() {
        iteminorder.setIioItemPrice(new BigDecimal("3.0"));
        assertTrue(iteminorder.getIioItemPrice().compareTo(new BigDecimal("3.0")) == 0);
    }

    @Test
    public void getIioItemCount() {
        iteminorder.setIioItemCount(4);
        assertTrue(iteminorder.getIioItemCount().equals(4));
    }
}
