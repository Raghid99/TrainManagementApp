package com.example.login18april;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login18april.TrainClasses.Train;
import com.example.login18april.TrainClasses.TrainStation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddClientBooking extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Spinner SelectStartingPoint, SelectEndingPoint;
    TextView SelectedDateText;
    Button SelectDate, CheckTraintatus;
    DatePickerDialog datePickerDialog;
    FirebaseDatabase OnlineDatabase;

    int IndexOfFrom = 0;
    int IndextOfTo = 0;

    String UserSelectedFromCity, UserSelectedToCity;


    ArrayAdapter<String> stationsArrayAdapterFrom;


    List<String> trainStopListUserSelected = new ArrayList<String>();


    String[] StationsFoundForAdapterNew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client_booking);


        SelectDate = findViewById(R.id.BTNAddClientBookingSelectDate);
        SelectedDateText = findViewById(R.id.TVAddClientBookingSelectedDate);


        SelectStartingPoint = findViewById(R.id.SPRAddClientBookingSelectFirstStation);
        SelectEndingPoint = findViewById(R.id.SPRAddClientBookingSelectSecondStation);


        CheckTraintatus = findViewById(R.id.BTNAddClientBookingCheckTrainAvailable);

        try {
            FileInputStream fis = openFileInput("StationListNew");
            int size = fis.available();
            byte[] data = new byte[size];
            fis.read(data);
            String text = new String(data);
            fis.close();

            StationsFoundForAdapterNew = text.split("\n", 0);


        } catch (Exception ex) {

            Toast.makeText(this, "Error while setting", Toast.LENGTH_SHORT).show();

        }

        //ArrayAdapter<String> stationsArrayAdapterTo = new ArrayAdapter<String>(AddClientBooking.this, android.R.layout.simple_spinner_item, StationListForAdapter);

        stationsArrayAdapterFrom = new ArrayAdapter<String>(AddClientBooking.this, android.R.layout.simple_spinner_item, StationsFoundForAdapterNew);


        SelectStartingPoint.setAdapter(stationsArrayAdapterFrom);
        SelectEndingPoint.setAdapter(stationsArrayAdapterFrom);


        SelectStartingPoint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UserSelectedFromCity = StationsFoundForAdapterNew[position];
                IndexOfFrom = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                UserSelectedFromCity = "Nothing";

            }
        });
        SelectEndingPoint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UserSelectedToCity = StationsFoundForAdapterNew[position];
                IndextOfTo = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                UserSelectedToCity = "Nothing";

            }
        });


        SelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(AddClientBooking.this, AddClientBooking.this, year, month, day);

                datePickerDialog.setTitle("Select Booking Date");
                long now = System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 1;
                long after = (now + 1000 * 60 * 60 * 24 * 7);

                datePickerDialog.getDatePicker().setMinDate(now);
                datePickerDialog.getDatePicker().setMaxDate(after);

                datePickerDialog.show();

            }
        });

        CheckTraintatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (UserSelectedFromCity.equals("Nothing") || UserSelectedToCity.equals("Nothing")) {
                    ShowDialogue("Please select both the stations", AddClientBooking.this);
                } else {

                    if (IndexOfFrom == IndextOfTo) {
                        ShowDialogue("Pease choose proper destination", AddClientBooking.this);
                    } else {
                        String from = UserSelectedFromCity;
                        String to = UserSelectedToCity;

                        int IntentIfUpOrDown;

                        LoadTrainAccordingToStation(from, to);
                        Intent it = new Intent(AddClientBooking.this, SelectTrainCoach.class);
                        if (IndexOfFrom < IndextOfTo) {
                            it.putExtra("SEL", 2);

                        } else {
                            it.putExtra("SEL", 1);
                        }
                        it.putExtra("CITY1", UserSelectedFromCity);
                        it.putExtra("CITY2", UserSelectedToCity);
                        startActivity(it);
                    }


                }
            }

        });

    }


    private void addStationtoOnlineDatabase() {

        String stationName = "Gigiko Station";
        String stationCityName = "Gigiko";
        String[] TrainStopsAtStation = {"Train 1", "Blue Line"};
        String[] EmployeesName = {"Omar Assem", "Khaled", "Alkindi"};
        String stationMaster = "Mohamed Ali";
        Date dateOfConstruction = new Date(2006, 3, 21);

        TrainStation trainStation = new TrainStation(stationName, stationCityName, stationMaster, dateOfConstruction);

        OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("TrainStations");
        String key = ReferenceNode.push().getKey();

        ReferenceNode.child(key).setValue(trainStation);


        try {
            ReferenceNode = OnlineDatabase.getReference("TrainStations" + "/" + key + "/" + "trainStopAtStaion");
            for (String s : TrainStopsAtStation) {
                String Stopskey = ReferenceNode.push().getKey();
                ReferenceNode.child(Stopskey).setValue(s);
            }

            ReferenceNode = OnlineDatabase.getReference("TrainStations" + "/" + key + "/" + "employeesInStation");
            for (String s : EmployeesName) {
                String employeeKey = ReferenceNode.push().getKey();
                ReferenceNode.child(employeeKey).setValue(s);

            }


        } catch (Exception x) {
            Toast.makeText(this, "Exception", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Date datetemp = new Date();

        Date or = new Date(year, month, dayOfMonth);
        long za = or.getTime();

        Date dateeee = new Date(za);
        Toast.makeText(this, dateeee.toString(), Toast.LENGTH_SHORT).show();


        Date date = new Date(year, month, dayOfMonth, datetemp.getHours(), datetemp.getMinutes(), datetemp.getSeconds());

//        String pattern = "yyyy-MM-dd";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//
//        String dateShowing = simpleDateFormat.format(date);

        //SelectedDateText.setText(date.toString());


    }


    public void LoadTrainAccordingToStation(final String firsetStation, final String secondStation) {

        try {
            FileOutputStream fos = openFileOutput("SelectedTrains", Context.MODE_PRIVATE);
            fos.close();
        } catch (Exception ex) {

        }

        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Trains");

        ReferenceNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();

                for (DataSnapshot child : iterable) {

                    Train train = child.getValue(Train.class);

                    boolean found1 = false;
                    boolean found2 = false;

                    for (String t : train.getTrainStationStops()) {
                        if (t.equals(firsetStation)) {
                            found1 = true;
                        }
                        if (t.equals(secondStation)) {
                            found2 = true;
                        }

                    }
                    if (found1 && found2) {
                        try {

                            FileOutputStream fos = openFileOutput("SelectedTrains", Context.MODE_APPEND);
                            String trainName = train.getTrainName() + "\n";
                            fos.write(trainName.getBytes());
                            fos.close();

                        } catch (Exception ex) {

                        }
                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void ShowDialogue(String message, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }

    public void CheckFare() {

    }


}


//    private void addStationsToList() {
//
//        OnlineDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference ReferenceNode = OnlineDatabase.getReference("TrainStations");
//
//        ReferenceNode.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
//
//                for (DataSnapshot child : iterable) {
//
//                    TrainStation trainStation = child.getValue(TrainStation.class);
//                    //stationList.add(trainStation.getStationCityName());
//
//                    for (int i = 0; i < 5; i++) {
//                        if (stationList[i] == null) {
//                            stationList[i] = trainStation.getStationCityName();
//
//                        }
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }
