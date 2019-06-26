package com.boc.hopeheatapp.util.common;

import android.content.Context;

import com.boc.hopeheatapp.util.string.StringUtil;

import java.sql.Timestamp;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author andy.xu
 * @ClassName: DateFormat
 * @Description: 取得标准格式日期和时间
 * @date 2014-3-11 下午1:06:12
 */

public class DateFormat {

    /**
     * 缺省日期格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 缺省日期格式
     */
    public static final String DEFAULT_DATE_HOUR_FORMAT = "yyyy-MM-dd HH";

    /**
     * 缺省时间格式
     */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    /**
     * 缺省时间格式
     */
    public static final String DEFAULT_TIME_FORMAT2 = "HH:mm";

    /**
     * 缺省月格式
     */
    public static final String DEFAULT_MONTH = "MONTH";

    /**
     * 缺省年格式
     */
    public static final String DEFAULT_YEAR = "YEAR";

    /**
     * 缺省日格式
     */
    public static final String DEFAULT_DATE = "DAY";

    /**
     * 缺省小时格式
     */
    public static final String DEFAULT_HOUR = "HOUR";

    /**
     * 缺省分钟格式
     */
    public static final String DEFAULT_MINUTE = "MINUTE";

    /**
     * 缺省秒格式
     */
    public static final String DEFAULT_SECOND = "SECOND";

    /**
     * 缺省长日期格式
     */
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH-mm";

    /**
     * 缺省长日期格式,精确到秒
     */
    public static final String DEFAULT_DATETIME_FORMAT_SEC = "yyyy-MM-dd HH:mm:ss";

    /**
     * 通话记录时间格式
     */
    public static final String RECORDS_DATETIME_FORMAT_SEC = "MM/dd HH:mm";

    /**
     * 缺省长日期格式,精确到秒
     */
    public static final String DEFAULT_DATETIME_FORMAT_SEC2 = "yyyyMMddHHmmss";

    /**
     * 缺省长日期格式,精确到秒
     */
    public static final String DEFAULT_DATETIME_FORMAT_SEC3 = "MMddHHmmss";

    /**
     * 缺省长日期格式,精确到分
     */
    public static final String DEFAULT_DATETIME_FORMAT_SEC4 = "MM-dd HH:mm:ss";

    /**
     * 缺省长日期格式,精确到秒
     */
    public static final String DEFAULT_DATETIME_FORMAT_SEC5 = "yyyy-MM-dd HHmmss";

    /**
     * 星期数组
     */
    public static final String[] WEEKS = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    public static final String[] ENG_WEEKS = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY",
            "SATURDAY"};

    /**
     * 取当前日期的字符串表示
     *
     * @return 当前日期的字符串 ,如2010-05-28
     **/
    public static String today() {
        return formatDate(new Date(), DEFAULT_DATE_FORMAT);
    }

    /**
     * 取昨天日期的字符串表示
     *
     * @return 当前日期的字符串 ,如2010-05-28
     **/
    public static String yesterday() {
        Date yesterday = new Date(System.currentTimeMillis() - 24 * 3600 * 1000);
        return formatDate(yesterday, DEFAULT_DATE_FORMAT);
    }

    /**
     * 取当前日期的字符串表示
     *
     * @return 当前日期的字符串 ,如2010-05-28 12:00
     **/
    public static String todayHour() {
        return formatDate(new Date(), DEFAULT_DATE_HOUR_FORMAT) + ":00";
    }

    /**
     * 根据输入的格式得到当前日期的字符串
     *
     * @param strFormat 日期格式
     * @return
     */
    public static String formatDate(Date date, String strFormat) {
        return toString(date, strFormat);
    }

    /**
     * 取当前时间的字符串表示,
     *
     * @return 当前时间, 如:21:10:12
     **/
    public static String currentTime() {
        return currentTime(DEFAULT_TIME_FORMAT);
    }

    /**
     * 取当前时间的字符串表示,
     *
     * @return 当前时间, 如:2012-01-01 21:10:12
     **/
    public static String currentDateTime() {
        return currentTime(DEFAULT_DATETIME_FORMAT_SEC);
    }

    /**
     * 取当前时间的字符串表示,
     *
     * @return 当前时间, 如:20120101211012
     **/
    public static String currentDateAndTime() {
        return currentTime(DEFAULT_DATETIME_FORMAT_SEC2);
    }

    /**
     * 根据输入的格式获取时间的字符串表示
     *
     * @param strFormat 输出格式 ,如'hh:mm:ss'
     * @return 当前时间, 如:21:10:12
     **/

    public static String currentTime(String strFormat) {
        return toString(new Date(), strFormat);
    }

    /**
     * 取得相对于当前时间增加天数/月数/年数后的日期 <br>
     * 欲取得当前日期5天前的日期,可做如下调用:<br>
     * getAddDay("DAY", -5).
     *
     * @param field  ,段,如"year","month","date",对大小写不敏感
     * @param amount ,增加的数量(减少用负数表示),如5,-1
     * @return 格式化后的字符串 如"2010-05-28"
     * @throws ParseException
     **/

    public static String getAddDay(String field, int amount) throws ParseException {
        return getAddDay(field, amount, null);
    }

    /**
     * 取得相对于当前时间增加天数/月数/年数后的日期,按指定格式输出 欲取得当前日期5天前的日期,可做如下调用:<br>
     * getAddDay("DATE", -5,'yyyy-mm-dd hh:mm').
     *
     * @param field     ,段,如"year","month","date",对大小写不敏感
     * @param amount    ,增加的数量(减少用负数表示),如5,-1
     * @param strFormat ,输出格式,如"yyyy-mm-dd","yyyy-mm-dd hh:mm"
     * @return 格式化后的字符串 如"2010-05-28"
     * @throws ParseException
     **/
    public static String getAddDay(String field, int amount, String strFormat) throws ParseException {
        return getAddDay(null, field, amount, strFormat);
    }

    /**
     * 功能：对于给定的时间增加天数/月数/年数后的日期,按指定格式输出
     *
     * @param date      String 要改变的日期
     * @param field     int 日期改变的字段，YEAR,MONTH,DAY
     * @param amount    int 改变量
     * @param strFormat 日期返回格式
     * @return
     * @throws ParseException
     */
    public static String getAddDay(String date, String field, int amount, String strFormat) throws ParseException {
        if (strFormat == null) {
            strFormat = DEFAULT_DATETIME_FORMAT_SEC;
        }
        Calendar rightNow = Calendar.getInstance();
        if (date != null && !"".equals(date.trim())) {
            rightNow.setTime(parseDate(date, strFormat));
        }
        if (field == null) {
            return toString(rightNow.getTime(), strFormat);
        }
        rightNow.add(getInterval(field), amount);
        return toString(rightNow.getTime(), strFormat);
    }

    /**
     * 获取时间间隔类型
     *
     * @param field 时间间隔类型
     * @return 日历的时间间隔
     */
    protected static int getInterval(String field) {
        if (StringUtil.isEmpty(field)) {
            return 0;
        }
        String tmpField = StringUtil.upperCase(field);
        if (StringUtil.equals(tmpField, DEFAULT_YEAR)) {
            return Calendar.YEAR;
        } else if (StringUtil.equals(tmpField, DEFAULT_MONTH)) {
            return Calendar.MONTH;
        } else if (StringUtil.equals(tmpField, DEFAULT_DATE)) {
            return Calendar.DATE;
        } else if (StringUtil.equals(tmpField, DEFAULT_HOUR)) {
            return Calendar.HOUR;
        } else if (StringUtil.equals(tmpField, DEFAULT_MINUTE)) {
            return Calendar.MINUTE;
        } else {
            return Calendar.SECOND;
        }
    }

    /**
     * 获取格式化对象
     *
     * @param strFormat 格式化的格式 如"yyyy-MM-dd"
     * @return 格式化对象
     */
    public static SimpleDateFormat getSimpleDateFormat(String strFormat) {
        if (strFormat != null && !"".equals(strFormat.trim())) {
            return new SimpleDateFormat(strFormat);
        } else {
            return new SimpleDateFormat();
        }
    }

    /**
     * 得到当前日期的星期数
     *
     * @return 当前日期的星期的字符串
     * @throws ParseException
     */
    public static String getWeekOfMonth() throws ParseException {
        return getWeekOfMonth(null, null);
    }

    /**
     * 根据日期的到给定日期的在当月中的星期数
     *
     * @param date 给定日期
     * @return
     * @throws ParseException
     */
    public static String getWeekOfMonth(String date, String fromat) throws ParseException {
        Calendar rightNow = Calendar.getInstance();
        if (date != null && !"".equals(date.trim())) {
            rightNow.setTime(parseDate(date, fromat));
        }
        return WEEKS[rightNow.get(Calendar.WEEK_OF_MONTH)];
    }

    /**
     * 根据日期后去星期数
     *
     * @param date
     * @return
     */
    public static String getWeekOfMonth(final Date date) {
        if (null == date) {
            return null;
        }

        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        int nWeek = rightNow.get(Calendar.WEEK_OF_MONTH);
        return WEEKS[nWeek];
    }

    /**
     * 将java.util.date型按照指定格式转为字符串
     *
     * @param date   源对象
     * @param format 想得到的格式字符串
     * @return 如：2010-05-28
     */
    public static String toString(Date date, String format) {
        return getSimpleDateFormat(format).format(date);
    }

    /**
     * 将java.util.date型按照缺省格式转为字符串
     *
     * @param date 源对象
     * @return 如：2010-05-28
     */
    public static String toString(Date date) {
        return toString(date, DEFAULT_DATE_FORMAT);
    }

    /**
     * 强制类型转换 从串到日期
     *
     * @param strDate   源字符串，采用yyyy-MM-dd格式
     * @param format ps
     * @return 得到的日期对象
     * @throws ParseException
     */
    public static Date parseDate(String strDate, String format) throws ParseException {
        return getSimpleDateFormat(format).parse(strDate);
    }

    /***
     * 根据传入的毫秒数和格式，对日期进行格式化输出
     *
     * @version 2011-7-12
     * @param millisecond
     * @param format
     * @return
     */
    public static String millisecondFormat(Long millisecond, String format) {
        if (millisecond == null || millisecond <= 0) {
            throw new IllegalArgumentException(String.format("传入的时间毫秒数[%s]不合法", "" + millisecond));
        }
        if (format == null || "".equals(format.trim())) {
            format = DEFAULT_DATE_FORMAT;
        }
        return toString(new Date(millisecond), format);
    }

    /**
     * 强制类型转换 从串到时间戳
     *
     * @param strDate   源串
     * @param format 遵循格式
     * @return 取得的时间戳对象
     * @throws ParseException
     */
    public static Timestamp parseTimestamp(String strDate, String format) throws ParseException {
        Date utildate = getSimpleDateFormat(format).parse(strDate);
        return new Timestamp(utildate.getTime());
    }

    /**
     * getCurDate 取当前日期
     *
     * @return java.util.Date型日期
     **/
    public static Date getCurDate() {
        return (new Date());
    }

    /**
     * getCurTimestamp 取当前时间戳
     *
     * @return java.sql.Timestamp
     **/
    public static Timestamp getCurTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * getCurTimestamp 取遵循格式的当前时间
     *
     * @param format 遵循格式
     * @return java.sql.Timestamp
     **/
    public static Date getCurDate(String format) throws Exception {
        return getSimpleDateFormat(format).parse(toString(new Date(), format));
    }

    /**
     * Timestamp按照指定格式转为字符串
     *
     * @param timestamp 源对象
     * @param format    ps（如yyyy.mm.dd）
     * @return 如：2010-05-28 或2010-05-281 13:21
     */
    public static String toString(Timestamp timestamp, String format) {
        if (timestamp == null) {
            return "";
        }
        return toString(new Date(timestamp.getTime()), format);
    }

    /**
     * Timestamp按照缺省格式转为字符串
     *
     * @param ts 源对象
     * @return 如：2010-05-28
     */
    public static String toString(Timestamp ts) {
        return toString(ts, DEFAULT_DATE_FORMAT);
    }

    /**
     * Timestamp按照缺省格式转为字符串，可指定是否使用长格式
     *
     * @param timestamp  欲转化之变量Timestamp
     * @param fullFormat 是否使用长格式
     * @return 如：2010-05-28 或2010-05-28 21:21
     */
    public static String toString(Timestamp timestamp, boolean fullFormat) {
        if (fullFormat) {
            return toString(timestamp, DEFAULT_DATETIME_FORMAT_SEC);
        } else {
            return toString(timestamp, DEFAULT_DATE_FORMAT);
        }
    }

    /**
     * 将sqldate型按照指定格式转为字符串
     *
     * @param sqldate 源对象
     * @param sFormat ps
     * @return 如：2010-05-28 或2010-05-28 00:00
     */
    public static String toString(java.sql.Date sqldate, String sFormat) {
        if (sqldate == null) {
            return "";
        }
        return toString(new Date(sqldate.getTime()), sFormat);
    }

    /**
     * 将sqldate型按照缺省格式转为字符串
     *
     * @param sqldate 源对象
     * @return 如：2010-05-28
     */
    public static String toString(java.sql.Date sqldate) {
        return toString(sqldate, DEFAULT_DATE_FORMAT);
    }

    /**
     * 计算日期时间之间的差值， date1得时间必须大于date2的时间
     *
     * @param date1
     * @param date2
     * @return {@link Map} Map的键分别为, day(天),
     * hour(小时),minute(分钟)和second(秒)。
     * @version 2011-7-12
     */
    public static Map<String, Long> timeDifference(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            throw new NullPointerException("date1 and date2 can't null");
        }
        long mim1 = date1.getTime();
        long mim2 = date2.getTime();
        if (mim1 < mim2) {
            throw new IllegalArgumentException(String.format("date1[%s] not be less than date2[%s].", mim1 + "", mim2
                    + ""));
        }
        long m = (mim1 - mim2 + 1) / 1000L;
        long mday = 24 * 3600;
        final Map<String, Long> map = new HashMap<String, Long>();
        map.put("day", m / mday);
        m = m % mday;
        map.put("hour", (m) / 3600);
        map.put("minute", (m % 3600) / 60);
        map.put("second", (m % 3600 % 60));
        return map;
    }

    public static Map<String, Integer> compareTo(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            return null;
        }
        long time1 = date1.getTime();
        long time2 = date2.getTime();
        long time = Math.max(time1, time2) - Math.min(time1, time2);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("year", (calendar.get(Calendar.YEAR) - 1970) > 0 ? (calendar.get(Calendar.YEAR) - 1970) : 0);
        map.put("month", (calendar.get(Calendar.MONTH) - 1) > 0 ? (calendar.get(Calendar.MONTH) - 1) : 0);
        map.put("day", (calendar.get(Calendar.DAY_OF_MONTH) - 1) > 0 ? (calendar.get(Calendar.DAY_OF_MONTH) - 1) : 0);
        map.put("hour", (calendar.get(Calendar.HOUR_OF_DAY) - 8) > 0 ? (calendar.get(Calendar.HOUR_OF_DAY) - 8) : 0);
        map.put("minute", calendar.get(Calendar.MINUTE) > 0 ? calendar.get(Calendar.MINUTE) : 0);
        map.put("second", calendar.get(Calendar.SECOND) > 0 ? calendar.get(Calendar.SECOND) : 0);
        return map;
    }

    // KK 代表24进制， HH代表12进制
    private static SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.CHINA);

    public static Date parseToLocalTime(final long timeMills) {
        String curTimeString = sdformat.format(new Date(timeMills));
        return parseTime(curTimeString);
    }

    public static Date parseTime(final String timeString) {
        if (StringUtil.isEmpty(timeString)) {
            return null;
        }

        try {
            return sdformat.parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String parseDateTime(long datetime) {
        Format format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(new Date(datetime));
    }

    /**
     * @方法: parse12Time
     * @描述: 格式化成12进制的时间格式
     * @参数 @param datetime
     * @参数 @param context
     * @参数 @return
     * @返回值类型 String
     * @捕获异常
     */
    public static String parse12Time(long datetime, final Context context) {
        Date curDate = new Date(datetime);
        String formatTime = "";
        // 24小时制格式化
        SimpleDateFormat hour24Format = new SimpleDateFormat("HH:mm");
        // 12小时制格式化
        SimpleDateFormat hour12Format = new SimpleDateFormat("hh:mm");
        formatTime = hour12Format.format(curDate);
        String hourStr = hour24Format.format(curDate).substring(0, 2);
        if (Integer.valueOf(hourStr) < 12) {
            formatTime += " AM";
        } else {
            formatTime += " PM";
        }
        return formatTime;
    }

    /**
     * @方法: onGetDate
     * @描述: 获取英文版本的周几
     * @参数 @param dateTime
     * @参数 @param context
     * @参数 @return
     * @返回值类型 String
     * @捕获异常
     */
//	public static String onGetDate(long dateTime, final Context context) {
//
//		Calendar rightNow = Calendar.getInstance();
//		rightNow.setTime(new Date(dateTime));
//		int nWeek = rightNow.get(Calendar.WEEK_OF_MONTH);
//		return ENG_WEEKS[nWeek];
//	}

    // ///////////////////////////////////////////////////////////////////////////////////
    // ///////////////////////////////////////////////////////////////////////////////////

    private final SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
    ;
    private final SimpleDateFormat longHourSdf = new SimpleDateFormat("yyyy-MM-dd HH");
    private final SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获得本周的第一天，周一
     *
     * @return
     */
    public Date getCurrentWeekDayStartTime() {
        Calendar c = Calendar.getInstance();
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK) - 2;
            c.add(Calendar.DATE, -weekday);
            c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 获得本周的最后一天，周日
     *
     * @return
     */
    public Date getCurrentWeekDayEndTime() {
        Calendar c = Calendar.getInstance();
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK);
            c.add(Calendar.DATE, 8 - weekday);
            c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 获得本天的开始时间，即2012-01-01 00:00:00
     *
     * @return
     */
    public Date getCurrentDayStartTime() {
        Date now = new Date();
        try {
            now = shortSdf.parse(shortSdf.format(now));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获得本天的结束时间，即2012-01-01 23:59:59
     *
     * @return
     */
    public Date getCurrentDayEndTime() {
        Date now = new Date();
        try {
            now = longSdf.parse(shortSdf.format(now) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获得本月的开始时间，即2012-01-01 00:00:00
     *
     * @return
     */
    public Date getCurrentMonthStartTime(final long nTimeMill) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(nTimeMill);
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            now = shortSdf.parse(shortSdf.format(c.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前月的结束时间，即2012-01-31 23:59:59
     *
     * @return
     */
    public Date getCurrentMonthEndTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DATE, -1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前年的开始时间，即2012-01-01 00:00:00
     *
     * @return
     */
    public Date getCurrentYearStartTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.MONTH, 0);
            c.set(Calendar.DATE, 1);
            now = shortSdf.parse(shortSdf.format(c.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前年的结束时间，即2012-12-31 23:59:59
     *
     * @return
     */
    public Date getCurrentYearEndTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.MONTH, 11);
            c.set(Calendar.DATE, 31);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

}
