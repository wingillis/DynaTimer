package com.wgillis.dynatimer;


public class TimerCard {
    public long timerTime; //milliseconds
    public int index; //position in the timers
    public String readable;

    public TimerCard (int hour, int minute, int seconds) {
        int hourseconds = hour * 60 * 60;
        int minuteseconds = minute * 60;
        timerTime = (hourseconds+seconds + minuteseconds) * 1000;
        readable = Long.toString(timerTime);
    }
}
