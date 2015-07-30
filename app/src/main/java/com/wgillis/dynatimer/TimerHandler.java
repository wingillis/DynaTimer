package com.wgillis.dynatimer;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.CardView;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wgillis on 7/23/2015.
 */
public class TimerHandler {
    private ArrayList<TimerCard> timers;
    public static Timer timer;
    public static TimerTask timerTask;
    public boolean cancelled = false;
    private Context context;
    private Notification.Builder builder;
    private NotificationManager nm;
    private final long interval = 1000; //ms
    private long accumulatedTime = 0;
    private TextToSpeech tts;
    private Bundle params;
    private Activity ac;
    private RecyclerAdapter adapter;

    public TimerHandler(ArrayList<TimerCard> c, Context context, RecyclerAdapter adap) {
        timers = c;
        this.context = context.getApplicationContext();
        ac = (Activity) context;
        nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        cancelled = false;
        tts = new TextToSpeech(this.context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                // nothing?
            }
        });
        params = new Bundle();
        params.putString(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_ALARM));
        tts.setLanguage(Locale.US);
        adapter = adap;
    }

    public void startTimers(final int position) {
        if (position < timers.size()) {
            final TimerCard timerCard = timers.get(position);
            if (timerCard.isActive()) {
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        accumulatedTime += interval;
                        if (accumulatedTime >= timerCard.timerTime) {
                            notifyFinish();
                            nm.cancel(0);
                            this.cancel();
                            return;
                        }
                        if (builder != null) {
                            final String time = Formatter.formatLong(timerCard.timerTime - accumulatedTime);
                            ac.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.setText(position, time);
                                }
                            });
                            builder.setContentText("Time left: " + time)
                                    .setContentTitle(timerCard.readable)
                                    .setPriority(Notification.PRIORITY_DEFAULT);
                            nm.notify(0, builder.build());
                        } else {
                            Intent inte = new Intent(context, NotificationIntent.class);
                            PendingIntent pi = PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), inte, PendingIntent.FLAG_UPDATE_CURRENT);
                            builder = new Notification.Builder(context)
                                    .setContentTitle(timerCard.readable)
                                    .setSmallIcon(R.drawable.abc_ratingbar_full_material)
                                    .setPriority(Notification.PRIORITY_DEFAULT)
                                    .setAutoCancel(true)
                                    .addAction(R.drawable.abc_btn_borderless_material, "Cancel", pi)
                                    .setContentText("Time left: " + Formatter.format(timerCard));
                            Notification n = builder.build();

                            nm.notify(0, n);
                        }

                    }

                    private void notifyFinish() {
                        long[] pattern = {0, 300, 100, 200, 100, 100, 50, 100, 10};
                        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(), 0);
                        Notification.Builder b = new Notification.Builder(context)
                                .setVibrate(pattern)
                                .setContentText("Timer Done")
                                .setContentTitle(timerCard.readable)
                                .setSmallIcon(R.drawable.abc_ratingbar_full_material)
                                .setAutoCancel(true)
                                .setContentIntent(pendingIntent)
                                .setSound(sound)
                                .setPriority(Notification.PRIORITY_HIGH);
                        Notification n = b.build();
                        nm.notify(1, n);

                        accumulatedTime = 0;
                        startTimers(position + 1);

                    }

                    @Override
                    public boolean cancel() {
                        boolean b = super.cancel();
                        ac.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.setText(position, Formatter.formatLong(timerCard.timerTime));
                            }
                        });
                        nm.cancel(0);
                        RecyclerAdapter.th = null;
                        return b;
                    }
                };
                tts.speak(timerCard.readable + " in" + formatSpeech(timerCard), TextToSpeech.QUEUE_ADD, params, "win");
                timer = new Timer();
                timer.scheduleAtFixedRate(timerTask, 0, interval);

            } else {
                startTimers(position + 1);
            }
        } else {
            if (MainActivity.repeat) {
                startTimers(0);
            }
        }

    }

    public String formatSpeech(TimerCard c) {

        String speech = " ";
        if (c.getHour() != 0) {
            speech += Integer.toString(c.getHour()) + " hours";
        }
        if (c.getMinute() != 0) {
            speech += " " + Integer.toString(c.getMinute()) + " minutes";
        }
        if ((c.getMinute() != 0 || c.getHour() != 0) && c.getSecond() != 0) {
            speech += " and";
        }

        if (c.getSecond() != 0) {
            speech += " " + Integer.toString(c.getSecond()) + " seconds";
        }

        return speech;
    }

}
