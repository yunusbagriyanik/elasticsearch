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

    public static String currentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss.SSSZ");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date currentDate = new Date();
        return sdf.format(currentDate);
    }

    public static String convertDateToFormattedString(String date) {
        Instant instant = Instant.parse(date);
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss.SSSZ");
        return formatter.format(offsetDateTime);
    }
}
