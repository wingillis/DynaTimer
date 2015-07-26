package com.wgillis.dynatimer;

/**
 * Created by wgillis on 7/26/2015.
 */
public class Formatter {

    public static String format(TimerCard card) {
        int second = card.getSecond();
        int minute = card.getMinute();
        int hour = card.getHour();
        String time = "";
        if (hour != 0) {
            time += String.format("%01d", hour);
            time += ":";
            if (minute == 0) {
                time += "00";
            }
            if (second == 0) {
                time += ":00";
            }
        }
        if (minute != 0) {
            time += String.format("%02d", minute);
            if (second != 0) {
                time += ":";
            } else {
                time += ":00";
            }
        }
        if (second != 0) {
            time += String.format("%02d", second);
            if (minute == 0 && hour == 0) {
                time = "0:" + time;
            }
        }
        return time;
    }

    public static String formatLong(long millis) {
        long hour = (millis / (1000 * 60 * 60)) % 24;
        long minute = (millis / (1000 * 60)) % 60;
        long second = (millis/ (1000)) % 60;
        String time = "";
        if (hour != 0) {
            time += String.format("%01d", hour);
            time += ":";
            if (minute == 0) {
                time += "00";

            }
            if (second == 0) {
                time += ":00";
            }
        }
        if (minute != 0) {
            time += String.format("%02d", minute);
            if (second != 0) {
                time += ":";
            } else {
                time += ":00";
            }
        }
        if (second != 0) {
            time += String.format("%02d", second);
            if (minute == 0 && hour == 0) {
                time = "0:" + time;
            }
        }
        return time;

    }
}
