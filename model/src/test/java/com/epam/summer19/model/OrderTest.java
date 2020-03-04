package com.epam.summer19.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDateTime;

/**
 * model test for Cafe Menu.
 */
public class OrderTest {

    @Autowired
    private Order order = new Order();

    @Test
    public void getOrderId() {
        order.setOrderId(4);
        assertTrue(order.getOrderId().equals(4));
    }

    @Test
    public void getOrderEmployeeId() {
        order.setOrderEmployeeId(2);
        assertTrue(order.getOrderEmployeeId().equals(2));
    }

    @Test
    public void getOrderDateTime() {
        LocalDateTime datetime = LocalDateTime.now();
        order.setOrderDateTime(datetime);
        assertTrue(order.getOrderDateTime().equals(datetime));
    }

}
