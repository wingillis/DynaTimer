package com.wgillis.dynatimer;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<TimerCard> timers;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardview;
        public ViewHolder(CardView v) {
            super(v);
            cardview = v;
        }
    }

    public RecyclerAdapter(ArrayList<TimerCard> cars) {

        timers = cars;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);

        return new ViewHolder((CardView)v);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        CardView c = vh.cardview;
        final int p = position;
        TimerCard card = timers.get(position);
        TextView t = (TextView) c.findViewById(R.id.title_view);
        TextView time = (TextView) c.findViewById(R.id.time_view);
        Button b = (Button) c.findViewById(R.id.timer_start);
        t.setText(card.readable);
        time.setText(Long.toString(card.timerTime));
        card.index = position;
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimerHandler th = new TimerHandler(timers, view.getContext());
                th.startTimers(p);
            }
        });

    }

    @Override
    public int getItemCount() {
        return timers.size();
    }

}
