package com.example.login18april;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;

public class SelectCoachBiiking extends AppCompatActivity {
    TextView trainName;
    Button AddFinal;
    Spinner selectSeat;
    String UserSelectedSeat = "";

    String Fi;
    String se;
    String train;

    String [] seats = {"1","2","3","4","5","6","7","8","9"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_coach_biiking);


        Intent it = getIntent();
        train = it.getStringExtra("train");
        Fi = it.getStringExtra("1st");
        se = it.getStringExtra("2nd");



        selectSeat = findViewById(R.id.SelectSeat);
        AddFinal = findViewById(R.id.AddFinalBooking);




        ArrayAdapter ad= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,seats);

        selectSeat.setAdapter(ad);

        selectSeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               UserSelectedSeat = seats[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//        AddFinal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Booking booking = new Booking(train,new Date(),Fi,se,getUsernameFromLocaFile());
//
//                AddBooking(booking);
//
//            }
//        });






    }

    public void AddBooking(Booking booking){

        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Bookings");

        String key = ReferenceNode.push().getKey();

        ReferenceNode.setValue(booking);


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

}
