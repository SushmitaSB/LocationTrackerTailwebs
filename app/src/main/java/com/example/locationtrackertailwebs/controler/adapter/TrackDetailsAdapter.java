package com.example.locationtrackertailwebs.controler.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationtrackertailwebs.view.MapActivity;
import com.example.locationtrackertailwebs.R;
import com.example.locationtrackertailwebs.model.TrackDetails;

import java.util.ArrayList;
import java.util.List;

public class TrackDetailsAdapter extends RecyclerView.Adapter<TrackDetailsAdapter.MyViewHolder> {
    private Context context;
    private  List<TrackDetails> types;
    public TrackDetailsAdapter(Context context,  List<TrackDetails> types){
        this.context = context;
        this.types = types;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating the layout
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.row_layout_for_track_details, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewDate.setText(types.get(position).getDate() + "");
        holder.textViewTotalTrackedTime.setText(types.get(position).getTotalTime());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Double> latlist = new ArrayList<>();
                                ArrayList<Double> longlist = new ArrayList<>();
                                latlist = types.get(position).getLatList();
                                longlist =types.get(position).getLongList();
                                Double[] latArray = new Double[latlist.size()];
                                Double[] longArray = new Double[longlist.size()];
                                for (int i = 0; i < latlist.size(); i++) {
                                    latArray[i] = latlist.get(i).doubleValue();
                                    longArray[i] = longlist.get(i).doubleValue();
                                }
                                Bundle bundle = new Bundle();
                                Intent intent = new Intent(context, MapActivity.class);
                                    bundle.putSerializable("Lat_List", latlist);
                                    bundle.putSerializable("Long_List", longlist);
                                    intent.putExtras(bundle);
                                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return types.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTotalTrackedTime, textViewDate;
        LinearLayout linearLayout;
        MyViewHolder(View view) {
            super(view);
            //finding the id of view
            textViewTotalTrackedTime = view.findViewById(R.id.trackedTimeTvId);
            textViewDate = view.findViewById(R.id.datetvId);
            linearLayout = view.findViewById(R.id.linearLayoutClick);
        }

    }
}
