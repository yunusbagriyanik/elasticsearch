package com.yunusbagriyanik.elasticsearch.util;

import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

@UtilityClass
public class DateUtil {

    private final String DATE_FORMAT = "yyyyMMdd'T'HHmmss.SSSZ";

    public static String currentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date currentDate = new Date();
        return sdf.format(currentDate);
    }

    public static String convertDateToFormattedString(String date) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        Instant instant = OffsetDateTime.parse(date, inputFormatter).toInstant();

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.UTC);

        return outputFormatter.format(offsetDateTime);
    }
}
