package com.wgillis.dynatimer;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.nio.BufferUnderflowException;
import android.text.format.DateFormat;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;


public class MainActivity extends Activity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<TimerCard> timers;
    private Button button;
    public static boolean repeat;
    public static final String prefFile = "dynaTimerPrefs";
    public static final String timerString = "timers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timers = new ArrayList<TimerCard>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences pref = getSharedPreferences(prefFile, MODE_PRIVATE);
        ArrayList<TimerCard> temp_t = null;
        try {
            temp_t = (ArrayList<TimerCard>) ObjectSerializer.deserialize(pref.getString(timerString, ""));
        } catch (Exception e) {
            Log.e("win", e.getMessage());
            Log.d("win", "ObjectSerializer has a problem");
        }

        if (temp_t != null) {
            timers = temp_t;
        }
        repeat = pref.getBoolean("repeat", false);
        Switch s = (Switch) findViewById(R.id.repeat_switch);
        s.setChecked(repeat);

        adapter = new RecyclerAdapter(timers, this);
        recyclerView.setAdapter(adapter);


    }

    public void showTimePicker(View v){
        TimePickerFragment newFragment = TimePickerFragment.newInstance();
        newFragment.addContext(this);
        newFragment.show(getFragmentManager(), "");
    }


    public void onRepeatSwitchClicked(View v) {
        // do nothing yet
        Switch s = (Switch) v.findViewById(R.id.repeat_switch);
        repeat = s.isChecked();
    }

    public void addTimerData(TimerCard c) {
        timers.add(c);
        adapter.notifyDataSetChanged();
    }

    public void addTimerData(TimerCard c, int position) {
        timers.set(position, c);
        adapter.notifyDataSetChanged();
    }

    public void removeCard(int position) {
        timers.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences(prefFile, MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean("repeat", repeat);
        try {
            edit.putString(timerString, ObjectSerializer.serialize(timers));
        } catch (Exception e) {
            Log.e("win", e.getMessage());
            Log.e("win", "putting string in editor is not working properly");
        }
        edit.commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
