package com.epam.summer19.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * model test for Cafe Menu.
 */
public class OrderDTOTest {

    @Autowired
    private OrderDTO orderDTO = new OrderDTO();

    @Test
    public void getOrderId() {
        orderDTO.setOrderId(4);
        assertTrue(orderDTO.getOrderId().equals(4));
    }

    @Test
    public void getOrderEmployeeId() {
        orderDTO.setEmployeeId(2);
        assertTrue(orderDTO.getEmployeeId().equals(2));
    }

    @Test
    public void getOrderDateTime() {
        LocalDateTime datetime = LocalDateTime.now();
        orderDTO.setOrderDateTime(datetime);
        assertTrue(orderDTO.getOrderDateTime().equals(datetime));
    }

    @Test
    public void getSummaryPrice() {
        orderDTO.setSummaryPrice(new BigDecimal("22.0"));
        assertTrue(orderDTO.getSummaryPrice().compareTo(new BigDecimal("22.0")) == 0);
    }

    @Test
    public void getItemsQuantity() {
        orderDTO.setItemsQuantity(7);
        assertTrue(orderDTO.getItemsQuantity().equals(7));
    }
}
