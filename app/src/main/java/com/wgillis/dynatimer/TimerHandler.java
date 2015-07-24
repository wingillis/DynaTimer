package com.wgillis.dynatimer;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.CardView;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by wgillis on 7/23/2015.
 */
public class TimerHandler {
    private ArrayList<TimerCard> timers;
    private CountDownTimer timer;
    private Context context;
    public TimerHandler(ArrayList<TimerCard> c, Context context) {
        timers = c;
        this.context = context.getApplicationContext();
    }

    public void startTimers(final int position) {
        Log.e("Dyna Timer","We are in start timers");
        Log.e("Dyna Timer", Integer.toString(position));
        Log.e("Dyna Timer", Integer.toString(timers.size()));
        if (position < timers.size()) {
            final TimerCard timerCard = timers.get(position);
            timer = new CountDownTimer(timerCard.timerTime, timerCard.timerTime) {
                @Override
                public void onTick(long l) {
                    // Do nothing
                }

                @Override
                public void onFinish() {
                    Log.d("Dyna Timer", "We just finished one of the timers");
                    long[] pattern = {0,300,100,200,100,100,50,100,10};
                    Notification.Builder builder = new Notification.Builder(context)
                            .setContentTitle(timerCard.readable)
                            .setSmallIcon(R.drawable.abc_ratingbar_full_material)
                            .setPriority(Notification.PRIORITY_HIGH)
                            .setVibrate(pattern)
                            .setContentText(Long.toString(timerCard.timerTime));
                    Notification n = builder.build();
                    NotificationManager nm = (NotificationManager) context
                            .getSystemService(context.NOTIFICATION_SERVICE);
                    nm.notify(0, n);
                    startTimers(position+1);
                }
            };
            timer.start();
        } else {
            // Do nothing
        }
    }
}
