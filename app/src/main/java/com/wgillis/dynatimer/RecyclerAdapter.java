package com.wgillis.dynatimer;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<TimerCard> timers;
    private MainActivity activity;
    public static TimerHandler th;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardview;
        public ViewHolder(CardView v) {
            super(v);
            cardview = v;
        }
    }

    public RecyclerAdapter(ArrayList<TimerCard> cars, MainActivity a) {
        timers = cars;
        activity = a;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);

        return new ViewHolder((CardView)v);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        if (vh.cardview != null) {
            CardView c = vh.cardview;
            final int p = position;
            final TimerCard card = timers.get(position);
            TextView t = (TextView) c.findViewById(R.id.title_view);
            TextView time = (TextView) c.findViewById(R.id.time_view);
            Button b = (Button) c.findViewById(R.id.timer_start);
            Button editCard = (Button) c.findViewById(R.id.editCard);
            t.setText(card.readable);
            time.setText(Formatter.format(card));
            card.index = position;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    th = new TimerHandler(timers, view.getContext());
                    th.startTimers(p);

                }
            });

            editCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (activity != null) {
                        EditCard ec = EditCard.newInstance(card, position, activity);
                        ec.show(activity.getFragmentManager(), "");
                    }
                }
            });

            Button insert = (Button) c.findViewById(R.id.insertButton);
            insert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TimePickerFragment tpf = TimePickerFragment.newInstance(position);
                    tpf.addContext(activity);
                    tpf.show(activity.getFragmentManager(), "");
                }
            });

            ToggleButton bt = (ToggleButton) c.findViewById(R.id.timerState);
            bt.setChecked(card.isActive());
            if (!card.isActive()) {
                bt.setBackgroundColor(Color.argb(21,183,0,41));
            }
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToggleButton butt = (ToggleButton) view.findViewById(R.id.timerState);
                    boolean bu = butt.isChecked();
                    if (bu) {
                        butt.setBackgroundColor(Color.parseColor("#212aff1d"));
                    } else {
                        butt.setBackgroundColor(Color.argb(21, 183, 0, 41));
                    }
                    TimerCard tc = timers.get(position);
                    tc.setOn(bu);
                    timers.set(position, tc);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return timers.size();
    }

}
