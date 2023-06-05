package com.suslov.basejava.util.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class LocalDateXmlAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String strDate) {
        return LocalDate.parse(strDate);
    }

    @Override
    public String marshal(LocalDate date) {
        return date.toString();
    }
}
