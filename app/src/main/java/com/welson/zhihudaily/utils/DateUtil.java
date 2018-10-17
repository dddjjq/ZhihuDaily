package com.welson.zhihudaily.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static final String TAG = DateUtil.class.getSimpleName();
    private static final String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五","星期六"};

    public static String getBeforeDayString(String currentDate){
        try {
            Log.d(TAG,"currentDate is : " + currentDate);
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date date = dateFormat.parse(currentDate);
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(date);
            rightNow.add(Calendar.DAY_OF_YEAR,-1);
            Date dateBefore = rightNow.getTime();
            String result = dateFormat.format(dateBefore);
            Log.d(TAG,"result is : " + result);
            return result;
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String getCurrentStringStr(String currentDate){
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date date = dateFormat.parse(currentDate);
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(date);
            //rightNow.add(Calendar.DAY_OF_YEAR,1); //注意 这里是因为获取到的date和当日的时间差一天
            int dayOfWeek = rightNow.get(Calendar.DAY_OF_WEEK)-1;
            Date dateBefore = rightNow.getTime();
            String result = dateFormat.format(dateBefore);
            String month = result.substring(4,6);
            String day = result.substring(6,8);
            if (dayOfWeek < 0)
                dayOfWeek = 0;
            Log.d(TAG,"current week is : " + weeks[dayOfWeek]);
            return month + "月" + day + "日" + " " +weeks[dayOfWeek];
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String getCommentTime(long time){
        String dateTime; SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        dateTime = simpleDateFormat.format(new Date(time * 1000L));
        return dateTime;

    }
}
