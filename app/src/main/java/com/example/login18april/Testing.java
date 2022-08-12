package com.example.login18april;

import android.app.DatePickerDialog;
import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Date;

public class Testing extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Button button;
    TextView textView;

    Calendar calendar;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);


        button = findViewById(R.id.BTNDatePicker);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addObject(new Chat("AdminAccount","Ali","Hi)",22424));


            }
        });

///////////////////////////////////Seawfkainfaksnfknfaskfnaskfnaskfnaskf////////////////////
        textView = findViewById(R.id.TVDatePicker);

        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);


        datePickerDialog = new DatePickerDialog(this, this, year, month, day);
        long now = System.currentTimeMillis();
        long after = (now+1000*60*60*24*7);

        Date nowDate = new Date(now);
        Date afterDate = new Date(after);

        datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
        datePickerDialog.getDatePicker().setMaxDate(afterDate.getTime());


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datePickerDialog.show();

            }
        });

    }


    public void addObject(Chat chat){
        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Testings");

        File file = new File("Fielssa");
        FileOutputStream fileOutputStream;

        try {

            Toast.makeText(Testing.this, "1", Toast.LENGTH_SHORT).show();

            fileOutputStream = openFileOutput("FESE",Context.MODE_PRIVATE);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);


            objectOutputStream.writeObject(chat);
            Toast.makeText(Testing.this, "2", Toast.LENGTH_SHORT).show();

            fileOutputStream.close();




            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            Toast.makeText(Testing.this, "3", Toast.LENGTH_SHORT).show();

            int size = fileInputStream.available();

            byte[] data = new byte[size];
            fileInputStream.read(data);
            String text = new String(data);
            fileInputStream.close();
            Toast.makeText(Testing.this, "Object String is: "+text, Toast.LENGTH_SHORT).show();

            Toast.makeText(Testing.this, "4", Toast.LENGTH_SHORT).show();



            FileInputStream fis = openFileInput("UserDataLocal");

            Toast.makeText(Testing.this, "5", Toast.LENGTH_SHORT).show();


            ReferenceNode.setValue(text);

            Toast.makeText(Testing.this, "6", Toast.LENGTH_SHORT).show();


        }catch (Exception EX){
            Toast.makeText(Testing.this, "Error bro", Toast.LENGTH_SHORT).show();
        }





    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Date date = new Date(year, month, dayOfMonth);
        textView.setText(date+"");


    }


}

