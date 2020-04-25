package com.altimetrik.usecase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.LocalDate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class DateHandler extends StdDeserializer<LocalDate> {

    public DateHandler() {
        this(null);
    }

    public DateHandler(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public LocalDate deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException {
        String date = jsonparser.getText()+"/2020";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            return new LocalDate(sdf.parse(date));
        } catch (Exception e) {
            return null;
        }
    }

}
