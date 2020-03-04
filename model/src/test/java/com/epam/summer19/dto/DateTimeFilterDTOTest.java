package com.epam.summer19.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

/**
 * model test for Cafe Menu.
 */
public class DateTimeFilterDTOTest {

    @Autowired
    private DateTimeFilterDTO dateTimeFilterDTO = new DateTimeFilterDTO();

    @Test
    public void getStartDateTime() {
        LocalDateTime datetime = LocalDateTime.now();
        dateTimeFilterDTO.setStartDateTime(datetime);
        assertTrue(dateTimeFilterDTO.getStartDateTime().equals(datetime));
    }

    @Test
    public void getEndDateTime() {
        LocalDateTime datetime = LocalDateTime.now();
        dateTimeFilterDTO.setEndDateTime(datetime);
        assertTrue(dateTimeFilterDTO.getEndDateTime().equals(datetime));
    }

}
