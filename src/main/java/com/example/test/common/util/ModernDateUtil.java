package com.example.test.common.util;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

public class ModernDateUtil {
    /**
     * 기본 ZoneId 확인
     *
     * @return
     */
    public static ZoneId getDefaultZoneId() {
        return ZoneId.systemDefault();
    }

    /**
     * 기본 ZoneId 기반 Date -> LocalDate 변환
     *
     * @param date
     * @return
     */
    public static LocalDate asLocalDate(final Date date) {
        return asLocalDate(date, getDefaultZoneId());
    }

    /**
     * Date -> LocalDate 변환
     *
     * @param date
     * @param zoneId
     * @return
     */
    public static LocalDate asLocalDate(final Date date, final ZoneId zoneId) {
        return Instant.ofEpochMilli(date.getTime()).atZone(zoneId).toLocalDate();
    }

    /**
     *
     * @param date
     * @return
     */
    public static LocalDateTime asLocalDateTime(final Date date) {
        return asLocalDateTime(date, getDefaultZoneId());
    }

    /**
     * Date -> LocalDateTime 변환
     *
     * @param date
     * @param zoneId
     * @return
     */
    public static LocalDateTime asLocalDateTime(final Date date, final ZoneId zoneId) {
        return Instant.ofEpochMilli(date.getTime()).atZone(zoneId).toLocalDateTime();
    }

    /**
     * 기본 ZoneId 기반 LocalDateTime -> Date 변환
     *
     * @param localDate
     * @return
     */
    public static Date asDate(final LocalDate localDate) {
        return asDate(localDate, getDefaultZoneId());
    }

    /**
     * LocalDateTime -> Date 변환
     *
     * @param localDate
     * @param zoneId
     * @return
     */
    public static Date asDate(final LocalDate localDate, final ZoneId zoneId) {
        return asDate(localDate.atStartOfDay(), zoneId);
    }

    /**
     * 기본 ZoneId 기반 LocalDateTime -> Date 변환
     *
     * @param localDateTime
     * @return
     */
    public static Date asDate(final LocalDateTime localDateTime) {
        return asDate(localDateTime, getDefaultZoneId());
    }

    /**
     * LocalDateTime -> Date 변환
     *
     * @param localDateTime
     * @param zoneId
     * @return
     */
    public static Date asDate(final LocalDateTime localDateTime, final ZoneId zoneId) {
        return Date.from(localDateTime.atZone(zoneId).toInstant());
    }

    /**
     * 요일 확인
     *
     * @param date
     * @return
     */
    public static DayOfWeek getDayOfWeek(final LocalDate date) {
        return date.getDayOfWeek();
    }

    /**
     * 요일 확인
     *
     * @param dateTime
     * @return
     */
    public static DayOfWeek getDayOfWeek(final LocalDateTime dateTime) {
        return dateTime.getDayOfWeek();
    }

    /**
     * 주중 여부 확인
     *
     * @param date
     * @return
     */
    public static boolean isWeekday(final LocalDate date) {
        return isWeekday(getDayOfWeek(date));
    }

    /**
     * 주말 여부 확인
     *
     * @param date
     * @return
     */
    public static boolean isWeekend(final LocalDate date) {
        return !isWeekday(date);
    }

    /**
     * 주중 여부 확인
     *
     * @param dateTime
     * @return
     */
    public static boolean isWeekday(final LocalDateTime dateTime) {
        return isWeekday(getDayOfWeek(dateTime));
    }

    /**
     * 주말 여부 확인
     *
     * @param dateTime
     * @return
     */
    public static boolean isWeekend(final LocalDateTime dateTime) {
        return !isWeekday(dateTime);
    }

    /**
     * 이전 일자 여부 확인
     *
     * @param thisDate
     * @param thatDate
     * @param excludeSameDay true: thisDate < thatDate, false: thisDate <= thatDate
     * @return
     */
    public static boolean beforeThan(final LocalDate thisDate, final LocalDate thatDate, final boolean excludeSameDay) {
        final int comp = thisDate.compareTo(thatDate);
        return excludeSameDay
                ? comp < 0
                : comp <= 0;
    }

    /**
     * 이후 일자 여부 확인
     *
     * @param thisDate
     * @param thatDate
     * @param excludeSameDay true: thisDate > thatDate, false: thisDate >= thatDate
     * @return
     */
    public static boolean afterThan(final LocalDate thisDate, final LocalDate thatDate, final boolean excludeSameDay) {
        return !beforeThan(thisDate, thatDate, !excludeSameDay);
    }

    /**
     * startDate, endDate 사이의 날짜인지 확인
     *
     * @param date
     * @param startDate
     * @param endDate
     * @param option {@link BetweenOption}
     * @return
     */
    public static boolean betweenDate(final LocalDate date, final LocalDate startDate, final LocalDate endDate, final BetweenOption option) {
        final boolean[] excludeDates = checkExcludeStartEndDate(option);
        return afterThan(date, startDate, excludeDates[0]) && beforeThan(date, endDate, excludeDates[1]);
    }

    /**
     * 이전 일자 여부 확인
     *
     * @param thisDateTime
     * @param thatDateTime
     * @param excludeSameDay true: thisDate < thatDate, false: thisDate <= thatDate
     * @return
     */
    public static boolean beforeThan(final LocalDateTime thisDateTime, final LocalDateTime thatDateTime, final boolean excludeSameDay) {
        final int comp = thisDateTime.compareTo(thatDateTime);
        return excludeSameDay
                ? comp < 0
                : comp <= 0;
    }

    /**
     * 이후 일자 여부 확인
     *
     * @param thisDateTime
     * @param thatDateTime
     * @param excludeSameDay true: thisDate > thatDate, false: thisDate >= thatDate
     * @return
     */
    public static boolean afterThan(final LocalDateTime thisDateTime, final LocalDateTime thatDateTime, final boolean excludeSameDay) {
        return !beforeThan(thisDateTime, thatDateTime, !excludeSameDay);
    }

    /**
     * startDateTime, endDateTime 사이의 날짜인지 확인
     *
     * @param dateTime
     * @param startDateTime
     * @param endDateTime
     * @param option {@link BetweenOption}
     * @return
     */
    public static boolean betweenDate(final LocalDateTime dateTime, final LocalDateTime startDateTime, final LocalDateTime endDateTime, final BetweenOption option) {
        final boolean[] excludeDates = checkExcludeStartEndDate(option);
        return afterThan(dateTime, startDateTime, excludeDates[0]) && beforeThan(dateTime, endDateTime, excludeDates[1]);
    }

    /**
     * 주중 여부 확인
     *
     * @param dayOfWeek
     * @return
     */
    public static boolean isWeekday(final DayOfWeek dayOfWeek) {
        final boolean result;
        switch (dayOfWeek) {
            case MONDAY:
            case TUESDAY:
            case WEDNESDAY:
            case THURSDAY:
            case FRIDAY:
                result = true;
                break;
            case SATURDAY:
            case SUNDAY:
                result = false;
                break;
            default:
                throw new DateTimeException("Invalid DayOfWeek");
        }
        return result;
    }

    /**
     * String formatting
     *
     * @param localDateOrDateTime { LocalDate | LocalDateTime }
     * @param format
     * @return
     */
    public static String fmt(final TemporalAccessor localDateOrDateTime, final String format) {
        return fmt(localDateOrDateTime, DateTimeFormatter.ofPattern(format));
    }

    /**
     * String formatting
     *
     * @param localDateOrDateTime { LocalDate | LocalDateTime }
     * @param formatter
     * @return
     */
    public static String fmt(final TemporalAccessor localDateOrDateTime, final DateTimeFormatter formatter) {
        return formatter.format(localDateOrDateTime);
    }

    /**
     * Format String -> LocalDate 변환
     *
     * @param fmtDate
     * @param formatter
     * @return
     */
    public static LocalDate parse2LocalDate(final String fmtDate, final DateTimeFormatter formatter) {
        return LocalDate.parse(fmtDate, formatter);
    }

    /**
     * Format String -> LocalDateTime 변환
     *
     * @param fmtDate
     * @param formatter
     * @return
     */
    public static LocalDateTime parse2LocalDateTime(final String fmtDate, final DateTimeFormatter formatter) {
        return LocalDateTime.parse(fmtDate, formatter);
    }

    private static boolean[] checkExcludeStartEndDate(final BetweenOption option) {
        final boolean excludeStartDate, excludeEndDate;
        switch (option) {
            case INCLUDE_START_DATE_AND_INCLUDE_END_DATE:
                excludeStartDate = excludeEndDate = false;
                break;
            case INCLUDE_START_DATE_AND_EXCLUDE_END_DATE:
                excludeStartDate = false;
                excludeEndDate = true;
                break;
            case EXCLUDE_START_DATE_AND_INCLUDE_END_DATE:
                excludeStartDate = true;
                excludeEndDate = false;
                break;
            case EXCLUDE_START_DATE_AND_EXCLUDE_END_DATE:
            default:
                excludeStartDate = excludeEndDate = true;
                break;
        }
        return new boolean[] {excludeStartDate, excludeEndDate};
    }

    public enum BetweenOption {
        INCLUDE_START_DATE_AND_INCLUDE_END_DATE,
        INCLUDE_START_DATE_AND_EXCLUDE_END_DATE,
        EXCLUDE_START_DATE_AND_INCLUDE_END_DATE,
        EXCLUDE_START_DATE_AND_EXCLUDE_END_DATE;
    }
}