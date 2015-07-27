package com.wgillis.dynatimer;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;



public class TimePickerFragment extends DialogFragment {
    private EditText time1;
    private int minute = 0;
    private int hour = 0;
    private int second = 0;
    private MainActivity act;
    private Integer pos = null;

    static TimePickerFragment newInstance() {
        TimePickerFragment t = new TimePickerFragment();
        return t;
    }

    static TimePickerFragment newInstance(int position) {
        TimePickerFragment t = new TimePickerFragment();
        t.pos = position;
        return t;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void addContext(MainActivity a) {
        act = a;
    }

    @Override
    public View onCreateView(LayoutInflater lf, ViewGroup parent, Bundle savedState) {
        final Dialog d = getDialog();
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final View v = lf.inflate(R.layout.timer_time_picker, parent, false);

        time1 = (EditText) v.findViewById(R.id.timerText);
        time1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                parseTime(editable.toString());
            }
        });
        EditText ee = (EditText) v.findViewById(R.id.title_for_timer);
        ee.requestFocus();

        Button b = (Button) v.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimerCard timer = new TimerCard(hour, minute, second);
                EditText e = (EditText) v.findViewById(R.id.title_for_timer);
                timer.setTitle(e.getText().toString());
                if (pos != null) {
                    act.insertTimerData(timer, pos+1);
                } else {
                    act.addTimerData(timer);
                }
                d.dismiss();
            }
        });

        return v;
    }

    private void parseTime(String timer) {
        int colonCount = timer.length() - timer.replace(":", "").length();
        if (timer.indexOf(":") == timer.length()) {
            return;
        }
        if (timer.length() > 0) {
            switch (colonCount) {
                case 0:
                    // timer is minute
                    minute = Integer.parseInt(timer);
                    hour = 0;
                    second = 0;
                    break;
                case 1:
                    int colonIndex = timer.indexOf(":");
                    minute = Integer.parseInt(timer.substring(0, colonIndex));
                    if (timer.length() > colonIndex + 1) {
                        second = Integer.parseInt(timer.substring(colonIndex + 1));
                    } else {
                        second = 0;
                    }
                    hour = 0;
                    // timer is minute and seconds
                    break;
                case 2:
                    // timer is hour, minute, seconds
                    int idx1 = timer.indexOf(":");
                    int idx2 = timer.indexOf(":", idx1);
                    hour = Integer.parseInt(timer.substring(0, idx1));
                    minute = Integer.parseInt(timer.substring(idx1 + 1, idx2));
                    if (timer.length() > idx2 + 1) {
                        second = Integer.parseInt(timer.substring(idx2 + 1));
                    } else {
                        second = 0;
                    }
                    break;
                default:
                    return;
            }
        }
    }




}
