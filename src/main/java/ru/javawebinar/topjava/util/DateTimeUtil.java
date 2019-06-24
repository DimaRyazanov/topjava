package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static <T extends Comparable<? super T>> boolean isBetween(T lt, T startTime, T endTime) {
        return (startTime == null || lt.compareTo(startTime) >= 0) && (endTime == null || lt.compareTo(endTime) <= 0);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate parseLocalDate(String localDate){
        if (localDate == null) return null;
        try {
            return LocalDate.parse(localDate, DATE_FORMATTER);
        }catch (Exception e){
            return null;
        }

    }

    public static LocalTime parseLocalTime(String localTime){
        if (localTime == null) return null;
        try {
            return LocalTime.parse(localTime, TIME_FORMATTER);
        }catch (Exception e){
            return null;
        }

    }
}
