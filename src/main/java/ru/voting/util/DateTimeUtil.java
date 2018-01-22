package ru.voting.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");


    private DateTimeUtil() {
    }

    public static String dateToString(Date date) {
        return date == null ? "" : simpleDateFormat.format(date);
    }

    public static Date toDate(String date){
        try{
            return simpleDateFormat.parse(date);
        }catch (ParseException e){
            return null;
        }
    }
}
