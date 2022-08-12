package com.example.login18april;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FareAddingByStationMaster extends AppCompatActivity {
    EditText Dummy;
    Button AddDummy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fare_adding_by_station_master);
        Dummy = findViewById(R.id.ETDummy);
        AddDummy = findViewById(R.id.BTNAddDummy);

        AddDummy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // int x = Dummy.
            }
        });





    }

    void AddMultiplier(){

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



}
