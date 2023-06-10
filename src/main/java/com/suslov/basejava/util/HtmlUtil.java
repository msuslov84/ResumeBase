package com.suslov.basejava.util;

import com.suslov.basejava.model.Experience;

public class HtmlUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String formatDates(Experience.Period period) {
        return DateUtil.format(period.getPeriodFrom()) + " - " + DateUtil.format(period.getPeriodTo());
    }
}
