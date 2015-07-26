package com.wgillis.dynatimer;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by wgillis on 7/26/2015.
 */
public class NotificationIntent extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        TimerHandler.timerTask.cancel();
    }

}
