package com.example.login18april;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

public class AdminLogIN extends AppCompatActivity {
    final static String TAG = "AdminScreen";
    EditText AdminUsername,AdminPassword;
    Button AdminSignIN,UserSignIN,SignUPInstead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_log_in);

        AdminUsername = findViewById(R.id.ETAdminLogINUsername);
        AdminPassword = findViewById(R.id.ETAdminLogINPassword);

        AdminSignIN = findViewById(R.id.BTNAdminLogINAdminLogIn);
        UserSignIN = findViewById(R.id.BTNAdminLogINUserSignIN);
        SignUPInstead = findViewById(R.id.BTAdminLogINSignUPInstead);

        UserSignIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(AdminLogIN.this,SignInActivity.class);
                startActivity(it);
            }
        });
        SignUPInstead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(AdminLogIN.this,SignUPActivity.class);
                startActivity(it);
            }
        });
        AdminSignIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAdminLocalDatabase();
                Intent it = new Intent(AdminLogIN.this,AdminHomeScreen.class);
                startActivity(it);

            }
        });




    }

    public void updateAdminLocalDatabase() {
        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Admins");


        try {
            ReferenceNode.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    //Used for reseeting all the old text in the file
                    try {
                        FileOutputStream fos = openFileOutput("AdminDataLocal", Context.MODE_PRIVATE);
                        fos.close();
                        Log.d(TAG,"Clearing successfull");

                    } catch (Exception ex) {
                        Log.d(TAG, "File resetting failed");
                    }


                    try {

                        Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();

                        for (DataSnapshot child : iterable) {

                            Map<String, Object> perUserData = (Map<String, Object>) child.getValue();

                            String username_temp = (String) perUserData.get("Username");
                            String password_temp = (String) perUserData.get("Password");

                            String singleUserData = username_temp + "%%%%%" + password_temp + "\n";


                            try {
                                FileOutputStream fos = openFileOutput("AdminDataLocal", Context.MODE_APPEND);
                                fos.write(singleUserData.getBytes());
                                fos.close();


                            } catch (Exception e) {
                                Log.d(TAG, "'Data insertion failed");


                            }
                            Log.d(TAG,"All adding successfull");



                        }

                        try {
                            FileInputStream fis = openFileInput("AdminDataLocal");
                            int size = fis.available();

                            byte[] data = new byte[size];
                            fis.read(data);
                            String text = new String(data);
                            fis.close();
                            Toast.makeText(AdminLogIN.this, text, Toast.LENGTH_SHORT).show();
                            Log.d(TAG,"Reading after writing successfull");

                        } catch (Exception ex) {
                            Log.d(TAG, "Final CHECKING FAILED");


                        }


                    } catch (Exception e) {
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } catch (Exception ex) {

        }


    }
}
