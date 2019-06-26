package com.boc.hopeheatapp.parser;

import android.content.Context;

import com.boc.hopeheatapp.R;

import java.util.Date;

public class DateParser {
    public static final String TODAY = "今天";
    public static final String TOMORROW = "明天";
    public static final String NEXT_NEXT_DAY = "后天";

    public static final String DAY_22_DAY = "二十二";
    public static final String DAY_23_DAY = "二十三";
    public static final String DAY_24_DAY = "二十四";
    public static final String DAY_25_DAY = "二十五";
    public static final String DAY_26_DAY = "二十六";
    public static final String DAY_27_DAY = "二十七";
    public static final String DAY_28_DAY = "二十八";
    public static final String DAY_29_DAY = "二十九";


    public static String parser(Context context, String date) {
        if (!TextUtils.isEmpty(date)) {
            if (date.length() == 2) {
                int day = new Date().getDay();
                if (TODAY.equals(date)) {
                    return context.getString(R.string.date_time_format, day);
                } else if (TOMORROW.equals(date)) {
                    return context.getString(R.string.date_time_format, day + 1);
                } else if (NEXT_NEXT_DAY.equals(date)) {
                    return context.getString(R.string.date_time_format, day+2);
                }
            } else if (date.length() == 4) {
                String compare = date.substring(0, 3);
                if (DAY_22_DAY.equals(compare)) {
                    return context.getString(R.string.date_time_format, 22);
                } else if (DAY_23_DAY.equals(compare)) {
                    return context.getString(R.string.date_time_format, 23);
                } else if (DAY_24_DAY.equals(compare)) {
                    return context.getString(R.string.date_time_format, 24);
                } else if (DAY_25_DAY.equals(compare)) {
                    return context.getString(R.string.date_time_format, 25);
                } else if (DAY_26_DAY.equals(compare)) {
                    return context.getString(R.string.date_time_format, 26);
                } else if (DAY_27_DAY.equals(compare)) {
                    return context.getString(R.string.date_time_format, 27);
                } else if (DAY_28_DAY.equals(compare)) {
                    return context.getString(R.string.date_time_format, 28);
                } else if (DAY_29_DAY.equals(compare)) {
                    return context.getString(R.string.date_time_format, 29);
                }
            }
        }
        return null;
    }
}
