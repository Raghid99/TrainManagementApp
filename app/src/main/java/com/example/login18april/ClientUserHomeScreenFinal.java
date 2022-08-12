package com.example.login18april;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login18april.TrainClasses.Train;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ClientUserHomeScreenFinal extends AppCompatActivity {
    Button AccountOption,CheckFare,CheckTimmings,LogOut;
    ImageButton AddBooking,OnlineSupport,BookingStatus;
    TextView UsernameShowing;



    static List<String> trainFound = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_user_home_screen_final);

        AccountOption = findViewById(R.id.BTNClientUserHomeScreenAccountOptions);
        CheckFare = findViewById(R.id.BTNClientUserHomeScreenCheckFare);
        CheckTimmings = findViewById(R.id.BTNClientUserHomeScreenCheckTimmings);
        LogOut = findViewById(R.id.BTNClientUserHomeScreenLogOut);
        OnlineSupport = findViewById(R.id.BTNOnlineSupportNew);

        AddBooking = findViewById(R.id.IBClientUserHomeScreenAddBooking);
        BookingStatus = findViewById(R.id.IBClientUserHomeScreenBookingStatus);

        UsernameShowing = findViewById(R.id.TVClientUserHomeScreenUserNameShowing);

        OnlineSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ClientUserHomeScreenFinal.this,UserEndChat.class);
                startActivity(it);
            }
        });

        CheckFare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ClientUserHomeScreenFinal.this,AddClientBooking.class);
                startActivity(it);

            }
        });


        ///////////////////////////////////////////////////////////////////////


        String usernameForShowing = getAppUserNameFromLocaFile();
        UsernameShowing.setText(usernameForShowing);

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ClientUserHomeScreenFinal.this);
                builder.setMessage("Are you sure you want to logout");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Intent it = new Intent(ClientUserHomeScreenFinal.this,SignInActivity.class);
                        ClearLocalData();    ClearLocalData();

                        startActivity(it);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        });

        AccountOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ClientUserHomeScreenFinal.this,EmployeeAcountInfo.class);
                startActivity(it);
            }
        });

        CheckTimmings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ClientUserHomeScreenFinal.this,ShowTiming.class);
                startActivity(it);
            }
        });

        AddBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ClientUserHomeScreenFinal.this,AddClientBooking.class);
                startActivity(it);

            }
        });

        BookingStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ClientUserHomeScreenFinal.this,CLientUserBookingStatusChecking.class);
                startActivity(it);
            }
        });





    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ClientUserHomeScreenFinal.this);
        builder.setMessage("Are you sure you want to exit");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }



    public String getUserTypeFromLocalFile() {
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
                return accountInfo[0];

            }

        } catch (Exception ex) {
            return null;
        }


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

    void ClearLocalData() {
        try {

            FileOutputStream fos = openFileOutput("AccountInfo", Context.MODE_PRIVATE);

            String accountInfo = "";
            fos.write(accountInfo.getBytes());
            fos.close();
            Toast.makeText(this, "Cleared Data", Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            Toast.makeText(this, "FAILED", Toast.LENGTH_SHORT).show();

        }

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
                        trainFound.add(train.getTrainName());
                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }







}
