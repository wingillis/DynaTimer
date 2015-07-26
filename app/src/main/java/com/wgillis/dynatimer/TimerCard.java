package com.wgillis.dynatimer;


import java.io.Serializable;

public class TimerCard implements Serializable {
    public long timerTime; //milliseconds
    public int index; //position in the timers
    public String readable;
    private int hour;
    private int second;
    private int minute;
    private boolean isOn = true;

    public TimerCard (int hour, int minute, int seconds) {
        int hourseconds = hour * 60 * 60;
        int minuteseconds = minute * 60;
        timerTime = (hourseconds+seconds + minuteseconds) * 1000;
        readable = Long.toString(timerTime);
        this.hour = hour;
        this.minute = minute;
        second = seconds;
    }

    public void setTitle(String title) {
        readable = title;
    }

    public int getMinute() {
        return minute;
    }

    public int getHour() {
        return hour;
    }

    public int getSecond() {
        return second;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public boolean isActive() {
        return isOn;
    }
}
