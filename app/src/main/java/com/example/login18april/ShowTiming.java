package com.example.login18april;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.login18april.TrainClasses.Train;
import com.example.login18april.TrainClasses.TrainStop;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ShowTiming extends AppCompatActivity {
    Spinner AllTrainSPR;

    RecyclerView ShowingDown, ShowingUp;

    ArrayAdapter AdapterForShowingAllTrains;
    String[] AllTrainsArray;

    AdapterForShowingTime adapter1, adapter2;

    static List<TrainStop> TrainStopListDown = new ArrayList<TrainStop>();
    static List<TrainStop> TrainStopListUp = new ArrayList<TrainStop>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_timing);

        AllTrainSPR = findViewById(R.id.SPRShowNewTimmingShowAllTrains);
        ShowingDown = findViewById(R.id.RVShowNewTimmingDownRouteShowing);
        ShowingUp = findViewById(R.id.RVShowNewTimmingUpRouteShowing);

        try {

            FileInputStream fis = openFileInput("TrainForTimming");
            int size = fis.available();
            byte[] data = new byte[size];
            fis.read(data);
            String text = new String(data);
            fis.close();

            AllTrainsArray = text.split("\n", 0);

            AdapterForShowingAllTrains = new ArrayAdapter<String>(ShowTiming.this, android.R.layout.simple_spinner_item, AllTrainsArray);


        } catch (Exception ex) {

        }

        AllTrainSPR.setAdapter(AdapterForShowingAllTrains);

        AllTrainSPR.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TrainStopListUp.clear();
                TrainStopListDown.clear();

                AddTimmingDataDown(AllTrainsArray[position]);
                AddTimmingDataUp(AllTrainsArray[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);

        ShowingDown.setLayoutManager(linearLayoutManager1);
        ShowingUp.setLayoutManager(linearLayoutManager2);

        adapter1 = new AdapterForShowingTime(TrainStopListDown);
        adapter2 = new AdapterForShowingTime(TrainStopListUp);

        ShowingDown.setAdapter(adapter1);
        ShowingUp.setAdapter(adapter2);


    }

    public void LoadTrain() {

        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Trains");

        ReferenceNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> stationsFound = new ArrayList<String>();

                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();


                for (DataSnapshot childs : iterable) {

                    Train train = childs.getValue(Train.class);

                    try {

                        FileOutputStream fos = openFileOutput("TrainsForSpinner", MODE_APPEND);
                        fos.write((train.getTrainName() + "\n").getBytes());
                        fos.close();

                    } catch (Exception ex) {

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void AddTimmingDataDown(final String TrainName) {

        TrainStopListDown.clear();

        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("TrainStops");

        ReferenceNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();

                for (DataSnapshot child : iterable) {

                    TrainStop trainStop = child.getValue(TrainStop.class);

                    if (trainStop.getTrainName().equals(TrainName)) {
                        ShowTiming.TrainStopListDown.add(trainStop);
                        adapter1.notifyDataSetChanged();

                    } else {
                        continue;
                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void AddTimmingDataUp(final String TrainName) {

        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("TrainStopsUP");

        ReferenceNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();

                for (DataSnapshot child : iterable) {

                    TrainStop trainStop = child.getValue(TrainStop.class);

                    if (trainStop.getTrainName().equals(TrainName)) {
                        TrainStopListUp.add(trainStop);
                        adapter2.notifyDataSetChanged();

                    } else {
                        continue;
                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
