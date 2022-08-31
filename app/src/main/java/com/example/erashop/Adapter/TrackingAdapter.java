package com.example.erashop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erashop.Model.TrackingModel;
import com.example.erashop.Model.TrackingModel;
import com.example.erashop.R;
import com.example.erashop.databinding.ItemTimelineBinding;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;

public class TrackingAdapter extends RecyclerView.Adapter<TrackingAdapter.ViewHolder> {
    ArrayList<TrackingModel> arrayList_routs;


    public TrackingAdapter(ArrayList<TrackingModel> arrayList_routs) {
        this.arrayList_routs = arrayList_routs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_timeline,parent,false);
        return new ViewHolder(view, viewType);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrackingModel model = arrayList_routs.get(position);
        holder.text_timeline_title.setText(model.getTitle());
        holder.text_timeline_date.setText(model.getDate());
    }

    @Override
    public int getItemCount() {
        return arrayList_routs.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView text_timeline_title,text_timeline_date;
        TimelineView mTimelineView;

        public ViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            text_timeline_title = itemView.findViewById(R.id.title);
            text_timeline_date = itemView.findViewById(R.id.date);
            mTimelineView = itemView.findViewById(R.id.timeline);
            mTimelineView.initLine(viewType);
        }
    }
}
