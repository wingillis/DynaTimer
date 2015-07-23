package com.wgillis.dynatimer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<TimerCard> timers;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    public RecyclerAdapter(ArrayList<TimerCard> cars) {

        timers = cars;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);

        return new ViewHolder((TextView)v);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        vh.textView.setText(timers.get(position).readable);
    }

    @Override
    public int getItemCount() {
        return timers.size();
    }

}
