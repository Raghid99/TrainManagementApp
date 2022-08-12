package com.example.login18april;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.login18april.TrainClasses.Train;
import com.example.login18april.TrainClasses.TrainStop;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SelectTrainCoach extends AppCompatActivity {

    private static final int ROUTE_UP = 1;
    private static final int ROUTE_DOWN = 2;

    int UserSelectedUpOrDown = 2;


    Spinner SelectTrainSPR, SelectCoachSPR, SelectSeatSPR;
    RecyclerView ShowAllTimmings;
    Button SelectSeat;

    TextView TotalFare;
    ArrayAdapter<String> AdapterForAvailableTrains;
    String[] ArrayForAvailableTrains;
    String[] coachNumbers = {"1", "2", "3"};
    String IntentedFirstStation;
    String IntendedSecondStation;

    AdapterForShowingTime adapter;

    String SelectedTrainForOptions;

    static long Fare = 0;
    int Fare_Instance = 0;

    static List<TrainStop> trainStopList = new ArrayList<TrainStop>();

    static List<String> ListOfFoundStation = new ArrayList<>(10);

    static String[] SeatsArray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    boolean bookinAlreadyexist;

    int SelectedSeat;
    int SelectCoachByUser;
    long adder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_train_coach);

        Intent it = getIntent();
        int gettingTemp = (int) it.getIntExtra("SEL", 2);

        if (gettingTemp == ROUTE_DOWN) {
            UserSelectedUpOrDown = ROUTE_DOWN;
        } else {
            UserSelectedUpOrDown = ROUTE_UP;
        }
        IntentedFirstStation = it.getStringExtra("CITY1");
        IntendedSecondStation = it.getStringExtra("CITY2");


        SelectTrainSPR = findViewById(R.id.SPRSelectTrainAndCoachShowingAvailabeTrains);
        SelectSeatSPR = findViewById(R.id.SPRSelectTrainAndCoachSelectSeat);
        SelectSeat = findViewById(R.id.BTNSelectTrainAndCoachSelectTrainSeatAccordingly);
        SelectCoachSPR = findViewById(R.id.SPRSelectTrainAndCoachSelectCOACH);
        ShowAllTimmings = findViewById(R.id.RVSelectTrainAndCoachShowingAllRouteTimmings);
        TotalFare = findViewById(R.id.TVSelectTrainAndCoachTotalFare);

        try {

            FileInputStream fis = openFileInput("SelectedTrains");
            int size = fis.available();
            byte[] data = new byte[size];
            fis.read(data);
            String text = new String(data);
            fis.close();

            ArrayForAvailableTrains = text.split("\n", 0);
            AdapterForAvailableTrains = new ArrayAdapter<String>(SelectTrainCoach.this, android.R.layout.simple_spinner_item, ArrayForAvailableTrains);


        } catch (Exception ex) {

        }





        ArrayAdapter<String> seatAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,SeatsArray);
        SelectSeatSPR.setAdapter(seatAdapter);

        SelectSeatSPR.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedSeat = Integer.parseInt(SeatsArray[position+1]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        SelectTrainSPR.setAdapter(AdapterForAvailableTrains);

        SelectTrainSPR.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                trainStopList.clear();

                String train = ArrayForAvailableTrains[position];
                SelectedTrainForOptions = ArrayForAvailableTrains[position];
                LoadTrainDataAccordingly(train, UserSelectedUpOrDown);


                if (UserSelectedUpOrDown == ROUTE_DOWN) {
                    CheckStopStationForTheRouteAndTrainSelected(SelectedTrainForOptions, IntentedFirstStation, IntendedSecondStation);

                } else {
                    CheckStopStationForTheRouteAndTrainSelected(SelectedTrainForOptions, IntendedSecondStation, IntentedFirstStation);
                }

                CheckFareAccordingToAllCommingStops();

                TotalFare.setText("Your Total Fare is: " + Fare_Instance);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                SelectedTrainForOptions = ArrayForAvailableTrains[0];

            }
        });

        ArrayAdapter<String> adapterForCoaches = new ArrayAdapter<String>(SelectTrainCoach.this, android.R.layout.simple_spinner_item, coachNumbers);

        SelectCoachSPR.setAdapter(adapterForCoaches);
        SelectCoachSPR.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectCoachByUser = Integer.parseInt(coachNumbers[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ///////////////////////////////
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        ShowAllTimmings.setLayoutManager(linearLayoutManager);
        ///////////////////////////////////

        adapter = new AdapterForShowingTime(SelectTrainCoach.trainStopList);
        ShowAllTimmings.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        SelectSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Booking booking = new Booking(SelectedTrainForOptions,new Date(),new Date(2019,5,14),IntentedFirstStation,IntendedSecondStation,Fare_Instance,getAppUserNameFromLocaFile(),SelectCoachByUser,SelectedSeat);

                if (!bookinAlreadyexist) {
                    FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference ReferenceNode = OnlineDatabase.getReference("Bookings");

                    String key = ReferenceNode.push().getKey();

                    ReferenceNode.child(key).setValue(booking);



                    AlertDialog.Builder builder = new AlertDialog.Builder(SelectTrainCoach.this);
                    builder.setMessage("Your Booking has been added successfully");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent it = new Intent(SelectTrainCoach.this,ClientUserHomeScreenFinal.class);
                            startActivity(it);
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();






                }else{
                    ShowDialogue("Seat Already booked", SelectTrainCoach.this);


                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    void LoadTrainDataAccordingly(final String TrainName, int from) {

        if (from == ROUTE_DOWN) {

            FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
            DatabaseReference ReferenceNode = OnlineDatabase.getReference("TrainStops");

            ReferenceNode.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();

                    for (DataSnapshot child : iterable) {

                        TrainStop trainStop = child.getValue(TrainStop.class);

                        if (trainStop.getTrainName().equals(TrainName)) {
                            SelectTrainCoach.trainStopList.add(trainStop);
                            adapter.notifyDataSetChanged();

                        } else {
                            continue;
                        }


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        } else if (from == ROUTE_UP) {


            FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
            DatabaseReference ReferenceNode = OnlineDatabase.getReference("TrainStopsUP");

            ReferenceNode.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();

                    for (DataSnapshot child : iterable) {

                        TrainStop trainStop = child.getValue(TrainStop.class);

                        if (trainStop.getTrainName().equals(TrainName)) {
                            SelectTrainCoach.trainStopList.add(trainStop);
                            adapter.notifyDataSetChanged();

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


    public void CheckStopStationForTheRouteAndTrainSelected(final String trainName, final String fromStation, final String toStation) {

        ListOfFoundStation.clear();
        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Trains");

        ReferenceNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();

                for (DataSnapshot child : iterable) {

                    Train train = child.getValue(Train.class);


                    if (train.getTrainName().equals(trainName) && train.getTrainName().equals(trainName)) {

                        boolean startadding = false;

                        for (String s : train.getTrainStationStops()) {

                            if (s.equals(fromStation)) {
                                startadding = true;
                            }
                            if (startadding) {
                                ListOfFoundStation.add(s);
                            }
                            if (s.equals(toStation)) {
                                startadding = false;
                                break;

                            }

                        }
                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void CheckFareAccordingToAllCommingStops() {
        Fare_Instance = 0;

        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Rates");

        ReferenceNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                for (DataSnapshot child : iterable) {
                    String station = child.getKey();

                    for (String s : ListOfFoundStation) {
                        if (s.equals(station)) {

                            long fare_string_temp = (long) child.getValue();
                            Fare_Instance += fare_string_temp;

                        }
                    }
                }


                FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
                DatabaseReference ReferenceNode = OnlineDatabase.getReference("Multi");

                ReferenceNode.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        adder = (long) dataSnapshot.getValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                TotalFare.setText("Fare is : "+(Fare_Instance+adder));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void CheckWhichSeatIsAlreadyBooked(final String trainName, final int coachNumber, final int seatNumber) {
        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Bookings");

        ReferenceNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                for (DataSnapshot child : iterable) {

                    Booking booking = child.getValue(Booking.class);

                    if (booking.getTrainName().equals(trainName) && booking.getTrainCoach() == coachNumber && booking.getSeatNumber() == seatNumber) {

                        bookinAlreadyexist = true;
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


    public String getAppUserNameFromLocaFile() {

        try {

            FileInputStream fis = openFileInput("AccountInfo");
            int size = fis.available();


            byte[] data = new byte[size];
            fis.read(data);


            String text = new String(data);
            fis.close();

            String[] accountInfo = text.split("%%%%%", 0);
            if (accountInfo.length == 0) {

                return null;
            } else {
                return accountInfo[1];

            }

        } catch (Exception ex) {
            return "AdminAccount";

        }




    }



}


//    static String [] SeatsArray = {"1","2","3","4","5","6","7","8","9","10"};