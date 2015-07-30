package com.wgillis.dynatimer;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created by wgillis on 7/26/2015.
 */
public class EditCard extends DialogFragment {
    private TimerCard card;
    private int hour;
    private int minute;
    private int second;
    private int position;
    private MainActivity act;

    static EditCard newInstance(TimerCard c, int position, MainActivity a) {
        EditCard editCard = new EditCard();
        editCard.card = c;
        editCard.act = a;
        editCard.position = position;
        return editCard;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater lf, ViewGroup parent, Bundle savedState) {
        final Dialog d = getDialog();
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final View v = lf.inflate(R.layout.edit_time_picker, parent, false);
        EditText time1 = (EditText) v.findViewById(R.id.timerText);

        initializeTime(card);
        String text = Formatter.format(card);
        time1.setText(text);
        time1.setSelection(text.length());
        final EditText timeTitle = (EditText) v.findViewById(R.id.title_for_timer);
        timeTitle.setText(card.readable);
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

        Button done = (Button) v.findViewById(R.id.button);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimerCard t = new TimerCard(hour, minute, second);
                t.setTitle(timeTitle.getText().toString());
                act.addTimerData(t, position);
                d.dismiss();
            }
        });

        Button delete = (Button) v.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act.removeCard(position);
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

    private void initializeTime(TimerCard c) {
        hour = c.getHour();
        minute = c.getMinute();
        second = c.getSecond();
    }
}
