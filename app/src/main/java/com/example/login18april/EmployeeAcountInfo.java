package com.example.login18april;

import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login18april.TrainClasses.TrainCoach;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.util.Map;

public class EmployeeAcountInfo extends AppCompatActivity {
    Button ChangePassword;
    TextView ShowName;
    EditText TypeNewPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_acount_info);

        TypeNewPassword = findViewById(R.id.ETAccountInfoNewPassword);
        ShowName = findViewById(R.id.TVAccountInfoUsername);
        ShowName.setText(getAppUserNameFromLocaFile());

        ChangePassword = findViewById(R.id.BTNAccountInfoChangePasswordByClicking);
        ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TypeNewPassword.getVisibility() == View.INVISIBLE){

                    TypeNewPassword.setVisibility(View.VISIBLE);
                }else {

                    String password_temp = TypeNewPassword.getText().toString();

                    if (password_temp.length() == 0){
                        ShowDialogue("Please enter Password and all fields", EmployeeAcountInfo.this);
                    }else if (CheckIfPasswordHasSpaces(password_temp)) {

                        ShowDialogue("Password must not contain white spaces", EmployeeAcountInfo.this);

                    }else if (password_temp.length() < 8) {

                        ShowDialogue("Please enter Password of more than 8 characters", EmployeeAcountInfo.this);


                    }else{
                        changePassword(getUserTypeFromLocalFile(),getAppUserNameFromLocaFile(),password_temp);
                        ShowDialogue("Password Changed Successfully", EmployeeAcountInfo.this);
                    }

                }


            }
        });









    }

    public void changePassword(String UserType, final String Username, final String password){
        final FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();

        if (UserType.equals("ClientUser")){


            DatabaseReference ReferenceNode = OnlineDatabase.getReference("Users");
            ReferenceNode.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                    for (DataSnapshot child : iterable) {

                        Map<String ,Object> perUserData = (Map<String, Object>) child.getValue();
                        String username_temp = (String) perUserData.get("Username");

                        if (Username.equals(username_temp)){
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReference = firebaseDatabase.getReference("Users"+"/"+child.getKey()+"/"+"Password");

                            databaseReference.setValue(password);


                        }


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        }else if (UserType.equals("Employee")){

            DatabaseReference ReferenceNode = OnlineDatabase.getReference("Employees");
            ReferenceNode.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                    for (DataSnapshot child : iterable) {

                        Map<String ,Object> perUserData = (Map<String, Object>) child.getValue();
                        String username_temp = (String) perUserData.get("nameOfEmployee");

                        if (Username.equals(username_temp)){
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReference = firebaseDatabase.getReference("Employees"+"/"+child.getKey()+"/"+"Password");

                            databaseReference.setValue(password);


                        }


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




        }else if (UserType.equals("StationMaster")){

            DatabaseReference ReferenceNode = OnlineDatabase.getReference("StationMasters");
            ReferenceNode.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                    for (DataSnapshot child : iterable) {

                        Map<String ,Object> perUserData = (Map<String, Object>) child.getValue();
                        String username_temp = (String) perUserData.get("stationMasterName");

                        if (Username.equals(username_temp)){
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReference = firebaseDatabase.getReference("StationMasters"+"/"+child.getKey()+"/"+"Password");

                            databaseReference.setValue(password);


                        }


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }else{
            ShowDialogue("Something went wrong",EmployeeAcountInfo.this);
        }




        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Coaches");

        ReferenceNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();

                for (DataSnapshot child : iterable) {

                    TrainCoach trainCoach = child.getValue(TrainCoach.class);
                    Toast.makeText(EmployeeAcountInfo.this, trainCoach.getTrainSeats().get(4), Toast.LENGTH_SHORT).show();
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

    boolean CheckIfPasswordHasSpaces(String string) {
        boolean returner = false;

        char[] passwordCharArray = string.toCharArray();

        for (char c : passwordCharArray) {
            if (c == ' ') {
                returner = true;
            }
        }

        return returner;

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


}
