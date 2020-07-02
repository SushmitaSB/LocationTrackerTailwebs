package com.example.locationtrackertailwebs.controler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationtrackertailwebs.R;
import com.example.locationtrackertailwebs.model.studentDetails;

import io.realm.RealmResults;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {
    private Context context;
    private RealmResults<studentDetails> realmResults;
    public StudentAdapter(Context context, RealmResults<studentDetails> realmResults) {
        this.context = context;
        this.realmResults = realmResults;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating the layout
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.row_layout_for_recycler, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            //set text on views
            holder.textViewName.setText(realmResults.get(position).getName());
            holder.textViewSub.setText(realmResults.get(position).getSubject());
            holder.textViewMarks.setText(realmResults.get(position).getMarks() + "");
    }

    @Override
    public int getItemCount() {
        return realmResults.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewSub, textViewMarks;
        MyViewHolder(View view) {
            super(view);
                //finding the id of view
                textViewName = view.findViewById(R.id.nameId);
                textViewSub = view.findViewById(R.id.subjectId);
                textViewMarks = view.findViewById(R.id.marksId);
        }

    }
}
