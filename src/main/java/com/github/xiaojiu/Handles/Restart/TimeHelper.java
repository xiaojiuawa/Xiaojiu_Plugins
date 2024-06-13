package com.github.xiaojiu.Handles.Restart;

import java.time.*;
import java.util.Calendar;
import java.util.Date;

public class TimeHelper {
    public static String GetTime(Date date) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate_old = date.toInstant().atZone(zoneId).toLocalDate();
        LocalDate localDate_now = LocalDate.now();

        LocalTime localTime_old = date.toInstant().atZone(zoneId).toLocalTime();
        LocalTime localTime_now = LocalTime.now();
        Period period = Period.between(localDate_old, localDate_now);
        Duration duration = Duration.between(localTime_old, localTime_now);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.add(Calendar.SECOND, (int) duration.getSeconds());
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return String.format("%s天,%s小时%s分%s秒", day - 1, hour, minute, second);
    }
}
