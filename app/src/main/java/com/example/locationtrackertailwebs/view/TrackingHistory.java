package com.example.locationtrackertailwebs.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    List<TrackDetails> mArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_history);
        initializedVariables();
        fetchData();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initializedVariables() {
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        mArrayList = new ArrayList<>();
    }

    public void fetchData() {
        String userEmail = firebaseAuth.getCurrentUser().getEmail();
        CollectionReference collectionReference = firestore.collection("trackdetails");
        collectionReference.whereEqualTo("userEmail", userEmail).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            Log.d("chk", "onSuccess: LIST EMPTY");
                            return;
                        } else {
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

    }
}