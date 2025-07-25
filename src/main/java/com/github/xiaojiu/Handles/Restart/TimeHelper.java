package com.github.xiaojiu.Handles.Restart;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

public class TimeHelper {
    public static String GetTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd HH mm ss");
        String time = format.format(date);

        int year = Calendar.getInstance().get(Calendar.YEAR);
        return null;
    }
}
