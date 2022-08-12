package com.example.login18april;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
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

public class Splash extends AppCompatActivity {
    Button button;
    ImageView train;
    TextView rms;
    Animation frombottom, fromtop;
    SignInActivity sign = new SignInActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AddTrainsToSpinnerListsFile();
        AddTrainsToLocalFile();


        button = findViewById(R.id.BTNSplashGetStarted);


        button = findViewById(R.id.BTNSplashGetStarted);
        train = findViewById(R.id.IVSplashTrainShowing);
        rms = findViewById(R.id.TVSplashWelcomeText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!hasInternetConnection()) {
                    ShowDialogue("Please check your internet connection ):", Splash.this);
                } else {
//
//                    Intent it = new Intent(Splash.this, SignInActivity.class);
//                    startActivity(it);


                    String appUserAccountType = "";


                    try {
                        appUserAccountType = getUserTypeFromLocalFile();

                    } catch (Exception ex) {
                        Toast.makeText(Splash.this, "Error reading account status", Toast.LENGTH_SHORT).show();

                    }


                    if (appUserAccountType == null) {
                        Intent it = new Intent(Splash.this, SignInActivity.class);
                        startActivity(it);
                    } else {

                        Toast.makeText(Splash.this, appUserAccountType, Toast.LENGTH_SHORT).show();

                        if (appUserAccountType.equals("ClientUser")) {

                            Intent it = new Intent(Splash.this, ClientUserHomeScreenFinal.class);
                            startActivity(it);

                        } else if (appUserAccountType.equals("Employee")) {

                            Intent it = new Intent(Splash.this, EmploeeHomeScreenFinal.class);
                            startActivity(it);

                        } else if (appUserAccountType.equals("StationMaster")) {

                            Intent it = new Intent(Splash.this, StationMasterHomeScreenFinal.class);
                            startActivity(it);

                        } else {
                            Intent it = new Intent(Splash.this, SignInActivity.class);
                            startActivity(it);
                        }
                    }


                }


            }


        });


        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        fromtop = AnimationUtils.loadAnimation(this, R.anim.fromtop);

        button.setAnimation(frombottom);
        train.setAnimation(fromtop);
        rms.setAnimation(fromtop);
    }


    public boolean hasInternetConnection() {

        boolean hasWifi = false;
        boolean hasMobileData = false;


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo[] allNetworksInfo = connectivityManager.getAllNetworkInfo();


        for (NetworkInfo info : allNetworksInfo) {
            if (info.getTypeName().equalsIgnoreCase("WIFI")) {
                if (info.isConnected()) {
                    hasWifi = true;
                }

            } else if (info.getTypeName().equalsIgnoreCase("MOBILE")) {
                if (info.isConnected()) {
                    hasMobileData = true;
                }


            }

        }

        return (hasMobileData || hasWifi);


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

    public void AddTrainsToSpinnerListsFile() {

        try {
            FileOutputStream fos = openFileOutput("StationListNew", MODE_PRIVATE);
            fos.close();

        } catch (Exception ex) {
            Toast.makeText(Splash.this, "Clearing error", Toast.LENGTH_SHORT).show();

        }

        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("TrainStations");

        ReferenceNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();

                for (DataSnapshot childs : iterable) {

                    TrainStation trainStation = childs.getValue(TrainStation.class);
                    try {

                        FileOutputStream fos = openFileOutput("StationListNew", MODE_APPEND);
                        fos.write((trainStation.getStationCityName() + "\n").getBytes());
                        fos.close();

                    } catch (Exception ex) {
                        Toast.makeText(Splash.this, "Wrinting error", Toast.LENGTH_SHORT).show();

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void AddTrainsToLocalFile() {
        try {
            FileOutputStream fos = openFileOutput("TrainForTimming", MODE_PRIVATE);
            fos.close();

        } catch (Exception ex) {
            Toast.makeText(Splash.this, "Clearing error", Toast.LENGTH_SHORT).show();

        }

        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Trains");

        ReferenceNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();


                for (DataSnapshot childs : iterable) {

                    Train train = childs.getValue(Train.class);

                    try {

                        FileOutputStream fos = openFileOutput("TrainForTimming", MODE_APPEND);
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


}
