package com.example.login18april;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;


public class EmploeeHomeScreenFinal extends AppCompatActivity {
    TextView EmployeeName,StationName;
    Button ChatWithStationMaster,AccountInfo,LogOut,CheckSalary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emploee_home_screen_final);

        EmployeeName = findViewById(R.id.TVEmployeeHomeScreenNameShowing);
        StationName = findViewById(R.id.TVEmployeeHomeScreenStationName);

        ChatWithStationMaster = findViewById(R.id.BTNEmployeeHomeScreenMessage);

        AccountInfo = findViewById(R.id.BTNEmployeeHomeScreenCheckCurrentPay);
        CheckSalary = findViewById(R.id.BTNEmployeeHomeScreenCheckpay1234567);

        LogOut = findViewById(R.id.BTNEmployeeHomeScreenLogOut);

        EmployeeName.setText(getAppUserNameFromLocaFile());
        StationName.setText(getTrainStaion());

        ChatWithStationMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(EmploeeHomeScreenFinal.this,EmployeeToStationMasterChat.class);
                startActivity(it);

            }
        });

        AccountInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(EmploeeHomeScreenFinal.this,EmployeeAcountInfo.class);
                startActivity(it);
            }
        });










        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(EmploeeHomeScreenFinal.this,SignInActivity.class);
                ClearLocalData();
                startActivity(it);


            }
        });


        AccountInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        CheckSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(EmploeeHomeScreenFinal.this,ShowingPayToEmployee.class);
                startActivity(it);
            }
        });
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EmploeeHomeScreenFinal.this);
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

    public String getTrainStaion() {
        String returner = "";

        try {
            FileInputStream fis = openFileInput("WorkingStation");
            int size = fis.available();

            byte[] data = new byte[size];
            fis.read(data);
            String text = new String(data);
            returner = text;


        } catch (
                Exception ex) {
            returner = "Error";
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();

        }
        return returner;
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

    public String getUsernameFromLocaFile() {

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

    public String getUserGenderFromLocalFile() {
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
                return accountInfo[2];

            }

        } catch (Exception ex) {
            return null;
        }


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


}
