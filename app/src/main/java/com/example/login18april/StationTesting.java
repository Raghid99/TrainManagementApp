package com.example.login18april;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StationTesting extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ReferenceNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_testing);
        Button button = findViewById(R.id.AddStationToDatabase);
        firebaseDatabase = FirebaseDatabase.getInstance();
        ReferenceNode = firebaseDatabase.getReference("TrainStations");
        ReferenceNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try{
                    Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();



                }catch (Exception ex){

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





            }
        });


    }
}
