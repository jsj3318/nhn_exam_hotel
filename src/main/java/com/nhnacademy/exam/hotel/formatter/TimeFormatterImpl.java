package com.nhnacademy.exam.hotel.formatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TimeFormatterImpl implements TimeFormatter {

    @Value("${time.format}")
    private String pattern;

    @Override
    public String convert(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern(pattern).format(localDateTime);
    }
}
