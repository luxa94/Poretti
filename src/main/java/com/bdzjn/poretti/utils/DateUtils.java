package com.bdzjn.poretti.utils;

import java.util.Calendar;
import java.util.Date;

public final class DateUtils {

    private DateUtils() {

    }

    public static Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public static Date justDate(Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        setToDayBeginning(calendar);
        return calendar.getTime();
    }

    private static void setToDayBeginning(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public static boolean endsOnIsBeforeToday(Date endsOn) {
        final Date today = justDate(new Date());
        return endsOn.before(today);
    }
}
