package com.wgillis.dynatimer;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;



public class TimePickerFragment extends DialogFragment {
    private EditText time1;
    private EditText time2;
    private EditText time3;

    static TimePickerFragment newInstance() {
        return new TimePickerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater lf, ViewGroup parent, Bundle savedState) {
        Dialog d = getDialog();
        d.setTitle("Choose Time");
        View v = lf.inflate(R.layout.timer_time_picker, parent, false);
        return v;
    }

}
