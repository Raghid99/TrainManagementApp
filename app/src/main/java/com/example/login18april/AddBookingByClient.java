package com.example.login18april;

import android.app.DatePickerDialog;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.login18april.TrainClasses.TrainStation;
import com.example.login18april.TrainClasses.TrainStop;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddBookingByClient extends AppCompatActivity {
    Spinner SelectStartingPoint,SelectEndingPoint;
    Button SelectDate,CheckTraintatus;
    TextView SelectedDateText;
    DatePickerDialog datePickerDialog;
    FirebaseDatabase OnlineDatabase;

    String UserSelectedFromCity,  UserSelectedToCity;
    Date UserSelectedDate;
    List<TrainStop> trainStopListUserSelected = new ArrayList<TrainStop>();


    private List<String> stationList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking_by_client);


        SelectDate = findViewById(R.id.BTNAddClientBookingSelectDate);
        SelectedDateText = findViewById(R.id.TVAddClientBookingSelectedDate);



        SelectStartingPoint = findViewById(R.id.SPRAddClientBookingSelectFirstStation);
        SelectEndingPoint = findViewById(R.id.SPRAddClientBookingSelectSecondStation);
        addStationsToList();


        CheckTraintatus = findViewById(R.id.BTNAddClientBookingCheckTrainAvailable);
        ArrayAdapter<String> stationsArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,stationList);


        SelectStartingPoint.setAdapter(stationsArrayAdapter);
        SelectEndingPoint.setAdapter(stationsArrayAdapter);

//        SelectStartingPoint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                UserSelectedFromCity = stationList.get(position);
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                UserSelectedFromCity = "Nothing";
//
//            }
//        });
//
//        SelectEndingPoint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                UserSelectedToCity = stationList.get(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                UserSelectedToCity = "Nothing";
//
//            }
//        });
        
//        CheckTraintatus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(AddBookingByClient.this, UserSelectedFromCity+" "+ UserSelectedToCity, Toast.LENGTH_SHORT).show();
//            }
//        });






    }

    private void addStationsToList(){

        OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("TrainStations");

        ReferenceNode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();

                for (DataSnapshot child: iterable){

                    TrainStation trainStation = child.getValue(TrainStation.class);
                    stationList.add(trainStation.getStationCityName());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
