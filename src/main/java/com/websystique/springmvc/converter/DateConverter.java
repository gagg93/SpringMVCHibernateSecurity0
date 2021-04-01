package com.websystique.springmvc.converter;


import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
        if (!source.isEmpty()) {
            try {
                return sf.parse(source);
            } catch (ParseException e) {
                //LOGGER.error(e.getLocalizedMessage());
            }
        }
        return null;
    }
}