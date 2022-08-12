package com.example.login18april;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login18april.TrainClasses.Train;
import com.example.login18april.TrainClasses.TrainCoach;
import com.example.login18april.TrainClasses.TrainSeat;
import com.example.login18april.TrainClasses.TrainStaionMaster;
import com.example.login18april.TrainClasses.TrainStation;
import com.example.login18april.TrainClasses.TrainStop;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button button;
    TextView textView;

    String name = "";

    List<Train> trains = new ArrayList<Train>();

    List<String> trainFound = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.buttonAdd);

        textView = findViewById(R.id.textView611);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadTrainAccordingToStation("Bahawalpur", "Lodhran");

                TrainStation trainStation = new TrainStation("AbuDhabi Junction", "AbuDhabi", "Anon", new Date(2001, 3, 10));
                AddTrainStaionToOnlineDatabase(trainStation);

//                getSelectedTrainsFromLocalData();
//
//
//                Date reaching = new Date(2019, 2, 2, 9, 10, 0);
//                Date departuring = new Date(2019, 2, 2, 9, 30, 0);
//
//
//                TrainStop trainStop = new TrainStop("Zakaria Express", "Dubai", reaching, departuring);
//                AddTrainStopsOtherDirectionToOnlineDatabase(trainStop);


            }
        });
    }


    public void AddStationMasterToDatabase(TrainStaionMaster trainStaionMaster) {

        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("StationMasters");

        String key = ReferenceNode.push().getKey();
        ReferenceNode.child(key).child("contactNumber").setValue(trainStaionMaster.getContactNumber());
        ReferenceNode.child(key).child("dateOfJoin").setValue(trainStaionMaster.getDateOfJoin());
        ReferenceNode.child(key).child("Password").setValue(trainStaionMaster.getPassword());
        ReferenceNode.child(key).child("Gender").setValue(trainStaionMaster.getGender());
        ReferenceNode.child(key).child("Salary").setValue(trainStaionMaster.getSalary());
        ReferenceNode.child(key).child("stationMasterName").setValue(trainStaionMaster.getStationMasterName());
        ReferenceNode.child(key).child("workingStationName").setValue(trainStaionMaster.getWorkingStationName());

    }

    public void AddTrainCoachToOnlineDatabase(TrainCoach trainCoach) {
        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Coaches");

        String key = ReferenceNode.push().getKey();

        ReferenceNode.child(key).setValue(trainCoach);

    }

    public void AddingPattern(String TrainName) {
        List<TrainSeat> trainSeats1 = new ArrayList<>(10);
        List<String> trainSeats1NamesOnly1 = new ArrayList<>(10);

        List<TrainSeat> trainSeats2 = new ArrayList<>(10);
        List<String> trainSeats1NamesOnly2 = new ArrayList<>(10);

        List<TrainSeat> trainSeats3 = new ArrayList<>(10);
        List<String> trainSeats1NamesOnly3 = new ArrayList<>(10);


        for (int i = 0; i < 10; i++) {
            trainSeats1.add(new TrainSeat(TrainName + "%%" + "Coach01", TrainName + "%%" + "Coach01" + "%%" + "Seat" + (i + 1), false));    // = new TrainSeat(TrainName + "%%" + "Coach01", TrainName + "%%" + "Coach01" + "%%" + "Seat" + (i + 1), false);
            trainSeats1NamesOnly1.add(trainSeats1.get(i).getSeatFullName());    //= trainSeats1[i].getSeatFullName();


            trainSeats2.add(new TrainSeat(TrainName + "%%" + "Coach02", TrainName + "%%" + "Coach02" + "%%" + "Seat" + (i + 1), false));    // = new TrainSeat(TrainName + "%%" + "Coach01", TrainName + "%%" + "Coach01" + "%%" + "Seat" + (i + 1), false);
            trainSeats1NamesOnly2.add(trainSeats2.get(i).getSeatFullName());    //= trainSeats1[i].getSeatFullName();

            trainSeats3.add(new TrainSeat(TrainName + "%%" + "Coach03", TrainName + "%%" + "Coach03" + "%%" + "Seat" + (i + 1), false));    // = new TrainSeat(TrainName + "%%" + "Coach01", TrainName + "%%" + "Coach01" + "%%" + "Seat" + (i + 1), false);
            trainSeats1NamesOnly3.add(trainSeats3.get(i).getSeatFullName());   //= trainSeats1[i].getSeatFullName();


        }

        TrainCoach trainCoach1 = new TrainCoach(1, TrainName + "%%" + 01, trainSeats1NamesOnly1, TrainName, "Good");
        TrainCoach trainCoach2 = new TrainCoach(2, TrainName + "%%" + 02, trainSeats1NamesOnly2, TrainName, "Good");
        TrainCoach trainCoach3 = new TrainCoach(3, TrainName + "%%" + 03, trainSeats1NamesOnly3, TrainName, "Good");

        AddTrainCoachToOnlineDatabase(trainCoach1);
        AddTrainCoachToOnlineDatabase(trainCoach2);
        AddTrainCoachToOnlineDatabase(trainCoach3);
    }

    public void LoadCoachDataFromDatabase() {
        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Coaches");

        ReferenceNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                for (DataSnapshot child : iterable) {

                    TrainCoach trainCoach = child.getValue(TrainCoach.class);
                    Toast.makeText(MainActivity.this, trainCoach.getTrainSeats().get(4), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void AddTrainToOnlineDatabase(Train train) {
        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Trains");

        String key = ReferenceNode.push().getKey();

        ReferenceNode.child(key).setValue(train);


    }

    public void AddTrainStaionToOnlineDatabase(TrainStation trainStation) {
        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("TrainStations");

        String key = ReferenceNode.push().getKey();

        ReferenceNode.child(key).setValue(trainStation);


    }

    public void LoadAllTrainsFromOnlineDatabase() {

        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Trains");

        ReferenceNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();

                for (DataSnapshot child : iterable) {

                    Train train = child.getValue(Train.class);
                    File file = new File("TrainList");


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void AddTrainStopsOtherDirectionToOnlineDatabase(TrainStop trainStop) {
        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("TrainStopsUP");

        String key = ReferenceNode.push().getKey();

        ReferenceNode.child(key).setValue(trainStop);


    }

    public void AddTrainStopsToOnlinwDatabase(TrainStop trainStop) {

        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("TrainStops");

        String key = ReferenceNode.push().getKey();

        ReferenceNode.child(key).setValue(trainStop);


    }

    void LoadAllTrainStopsFromOnlineDataBase() {

        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("TrainStops");

        ReferenceNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();

                for (DataSnapshot child : iterable) {

                    TrainStop trainStop = child.getValue(TrainStop.class);

                    String nametr = trainStop.getTrainName();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH-mm-ss");

                    String arrival = dateFormat.format(trainStop.getTrainArrivalTime());
                    String departure = dateFormat.format(trainStop.getTraindepartureTime());


                    Toast.makeText(MainActivity.this, nametr + "\n" + arrival + "\n" + departure, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void LoadTrainAccordingToStation(final String firsetStation, final String secondStation) {


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
                            Toast.makeText(MainActivity.this, "Added " + train.getTrainName(), Toast.LENGTH_SHORT).show();

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

    public void getSelectedTrainsFromLocalData() {
        try {
            FileInputStream fis = openFileInput("SelectedTrains");
            int s = fis.available();
            byte[] data = new byte[s];
            fis.read(data);
            String text = new String(data);
            fis.close();
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();


//            String [] trainFound = text.split("\n",0);
//            Toast.makeText(this, trainFound.length, Toast.LENGTH_SHORT).show();
//
//            for (String trainName:trainFound ){
//                Toast.makeText(this, trainName, Toast.LENGTH_SHORT).show();
//            }


        } catch (IOException ex) {

        }


    }


}


//        Username = findViewById(R.id.ETUsername);
//        SaveButton = findViewById(R.id.BTNSaveDatabase);
//        LoadButton = findViewById(R.id.BTNUpdateDatabase);
//        ShowData = findViewById(R.id.TVShowingData);
//        MainDatabase = FirebaseDatabase.getInstance();
//        Nodereference = MainDatabase.getReference("Users");
//
//
//
//
//
//
//        SaveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String username = Username.getText().toString();
//                Nodereference.child("User").setValue(username);
//                Toast.makeText(MainActivity.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        LoadButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Nodereference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        String data = dataSnapshot.getValue().toString();
//                        ShowData.setText(data);
//                        Toast.makeText(MainActivity.this, "Loading automatically", Toast.LENGTH_SHORT).show();
//
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                        Toast.makeText(MainActivity.this, "Reading failed", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//            }
//        });


//    TrainStaionMaster trainStaionMaster = new TrainStaionMaster("Zohaib Salman", "password", "Fujairah", "Male", 7743445, new Date(2015, 5, 11), 125030);
//    AddStationMasterToDatabase(trainStaionMaster);

////////////////////////////////////////////////////////////////////////////////

////Train Adding Pattern


//
//    Train train1 = new Train("Jaffar EXPRESS", "Khizar Hayyat");
//    Train train2 = new Train("Green Line", "Raza Haris");
//    Train train3 = new Train("Pak Business", "Waqas Zia");
//    Train train4 = new Train("Zakaria Express ", "Ali Muneer");
//    Train train5 = new Train("Tehreek Express", "Ahmad Ghulam");
//    Train train6 = new Train("RAK Express", "Omar");
//
//    AddTrainToOnlineDatabase(train1);
//    AddTrainToOnlineDatabase(train2);
//    AddTrainToOnlineDatabase(train3);
//    AddTrainToOnlineDatabase(train4);
//    AddTrainToOnlineDatabase(train5);
//    AddTrainToOnlineDatabase(train6);


//////////////////////////////////////////////////////

//    List<String> stopscitylist = new ArrayList<String>();
//
//    String Fujairah = "Fujairah";
//    String Dubai = "Dubai";
//    String Bahawalpur = "Bahawalpur";
//    String Lodhran = "Lodhran";
//    String Abu Dabhi = "Abu Dabhi";
//
//
////stopscitylist.add(Fujairah);
//                stopscitylist.add(Dubai);
//                        //stopscitylist.add(Lodhran);
//                        stopscitylist.add(Bahawalpur);
//                        //stopscitylist.add(Abu Dabhi);
//
//
//                        Train train = new Train("Zakaria Express", "Haseeb Atif", stopscitylist);
//                        AddTrainToOnlineDatabase(train);
//



