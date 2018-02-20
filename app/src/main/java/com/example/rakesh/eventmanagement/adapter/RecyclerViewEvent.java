package com.example.rakesh.eventmanagement.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rakesh.eventmanagement.eventitem.EventItems;
import com.example.rakesh.eventmanagement.R;


import java.util.ArrayList;

public class RecyclerViewEvent extends RecyclerView.Adapter<RecyclerViewEvent.ViewHolder> {

    private Context mContext;

    private ArrayList<EventItems> mEventItemses;

    public RecyclerViewEvent(Context context,ArrayList<EventItems> eventItemses) {

        this.mContext = context;
        this.mEventItemses = eventItemses;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mGetName.setText(mEventItemses.get(position).getEventName());
        holder.mGetLocation.setText(mEventItemses.get(position).getEventLocation());
        holder.mGetDescription.setText(mEventItemses.get(position).getEventDescription());
        holder.mGetStartDate.setText(mEventItemses.get(position).getEventStartDate());
        holder.mGetEndDate.setText(mEventItemses.get(position).getEventEndDate());
        holder.mGetTime.setText(mEventItemses.get(position).getEventTime());
    }

    @Override
    public int getItemCount() {
        return null == mEventItemses ? 0 : mEventItemses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView mGetName;
        private final TextView mGetLocation;
        private final TextView mGetDescription;
        private final TextView mGetStartDate;
        private final TextView mGetEndDate;
        private final TextView mGetTime;

        public ViewHolder(View itemView) {
            super(itemView);

            mGetName = (TextView)itemView.findViewById(R.id.get_name);
            mGetLocation = (TextView)itemView.findViewById(R.id.get_location);
            mGetDescription = (TextView)itemView.findViewById(R.id.get_description);
            mGetStartDate = (TextView)itemView.findViewById(R.id.get_start_date);
            mGetEndDate = (TextView)itemView.findViewById(R.id.get_end_date);
            mGetTime = (TextView)itemView.findViewById(R.id.get_time);
        }
    }
}
