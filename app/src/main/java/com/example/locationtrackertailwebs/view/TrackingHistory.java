package com.example.locationtrackertailwebs.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.locationtrackertailwebs.R;
import com.example.locationtrackertailwebs.controler.adapter.TrackDetailsAdapter;
import com.example.locationtrackertailwebs.model.TrackDetails;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackingHistory extends AppCompatActivity {

    @BindView(R.id.recyclerId)
    RecyclerView recyclerView;

    @BindView(R.id.clearId)
    ImageView imageView;

    TrackDetailsAdapter mAdapter;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    DocumentReference documentReference;
    TrackDetails trackDetails;
    FirestoreRecyclerAdapter adapter;
    List<TrackDetails> mArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_history);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        mArrayList = new ArrayList<>();
        fetchData();
    }

    public void fetchData(){
        String userEmail = firebaseAuth.getCurrentUser().getEmail();
        CollectionReference collectionReference = firestore.collection("trackdetails");
        collectionReference.whereEqualTo("userEmail",userEmail).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                                                  Log.d("chk", "onSuccess: LIST EMPTY");
                                                  return;
                                              } else {
                                                  // Convert the whole Query Snapshot to a list
                                                  // of objects directly! No need to fetch each
                                                  // document.
                                                  List<TrackDetails> types = queryDocumentSnapshots.toObjects(TrackDetails.class);

                                                  // Add all to your list
                                                  mArrayList.addAll(types);
                                                  Log.d("chk", "onSuccess: " + mArrayList);
                                                  //set layout manager
                                                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                                    recyclerView.setLayoutManager(mLayoutManager);
                                                    mAdapter = new TrackDetailsAdapter(TrackingHistory.this, mArrayList);
                                                    recyclerView.setAdapter(mAdapter);
                                              }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
            }
        });



//        try {
//            Query query = firestore.collection("trackdetails").document().collection("trackdetails");
//            FirestoreRecyclerOptions<TrackDetails> options = new FirestoreRecyclerOptions.Builder<TrackDetails>()
//                    .setQuery(query, TrackDetails.class)
//                    .build();
//            if (options == null){
//                adapter = new FirestoreRecyclerAdapter<TrackDetails, MyViewHolder>(options) {
//                    @Override
//                    protected void onBindViewHolder(@NonNull MyViewHolder holder, int i, @NonNull TrackDetails trackDetails) {
//                        holder.textViewDate.setText(options.getSnapshots().get(i).getDate() + "");
//                        holder.textViewTotalTrackedTime.setText(options.getSnapshots().get(i).getTotalTime());
//                        holder.linearLayoutClick.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                ArrayList<Double> latlist = new ArrayList<>();
//                                ArrayList<Double> longlist = new ArrayList<>();
//                                latlist = options.getSnapshots().get(i).getLatList();
//                                longlist = options.getSnapshots().get(i).getLongList();
//                                Intent intent = new Intent(TrackingHistory.this, MapActivity.class);
//                                intent.putExtra("Lat_List", latlist);
//                                intent.putExtra("Long_List", longlist);
//                                startActivity(intent);
//                            }
//                        });
//
//                        recyclerView.setHasFixedSize(true);
//                        recyclerView.setLayoutManager(new LinearLayoutManager(TrackingHistory.this));
//                        recyclerView.setAdapter(adapter);
//                    }
//
//
//
//                    @NonNull
//                    @Override
//                    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                        //inflating the layout
//                        View itemView = LayoutInflater.from(TrackingHistory.this)
//                                .inflate(R.layout.row_layout_for_track_details, parent, false);
//                        return new MyViewHolder(itemView);
//                    }
//                };
//            }
//
//        }catch (Exception ex){
//            Log.d("chk", ex.getMessage());
//            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
//        }


    }

//    private class MyViewHolder extends RecyclerView.ViewHolder{
//        TextView textViewTotalTrackedTime, textViewDate;
//        LinearLayout linearLayoutClick;
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            textViewTotalTrackedTime = itemView.findViewById(R.id.trackedTimeTvId);
//            textViewDate = itemView.findViewById(R.id.datetvId);
//            linearLayoutClick = itemView.findViewById(R.id.linearLayoutClick);
//        }
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }
}