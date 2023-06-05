package com.suslov.basejava.util;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;

public class DateUtil implements Serializable {

    public static final LocalDate NOW = LocalDate.of(3999, 1, 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }
}
