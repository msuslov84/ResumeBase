package com.suslov.basejava.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String strDate) {
        return LocalDate.parse(strDate);
    }

    @Override
    public String marshal(LocalDate date) {
        return date.toString();
    }
}
