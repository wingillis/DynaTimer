package com.wgillis.dynatimer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private TimerCard[] timers;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    public RecyclerAdapter(TimerCard[] cars) {
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
        vh.textView.setText(timers[position].readable);
    }

    @Override
    public int getItemCount() {
        return timers.length;
    }

}
