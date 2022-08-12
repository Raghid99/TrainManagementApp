package com.example.login18april;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestBug extends AppCompatActivity {
Button bug;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bug);

        bug = findViewById(R.id.buttonForBug);

        bug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               AddPercentage();

            }
        });


    }

    void check(){
        File f=new File("bugTest");

        try{
            if (f.exists()){
                Toast.makeText(this, "File exists", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "No file found", Toast.LENGTH_SHORT).show();
                try{
                    f.createNewFile();
                }catch (IOException ex){
                    Toast.makeText(this, "Error while creating", Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception ex){
            Toast.makeText(this, "Overall Error", Toast.LENGTH_SHORT).show();
        }

    }

    void addText(){
        try{
            FileOutputStream fos = openFileOutput("TestBug",MODE_PRIVATE);
            fos.write("I am writing".getBytes());
            fos.close();
            Toast.makeText(this, "Successfully written", Toast.LENGTH_SHORT).show();

            try {

                FileInputStream fis = openFileInput("TestBug");
                int size = fis.available();
                byte [] data = new byte[size];
                fis.read(data);
                String x = new String(data);
                Toast.makeText(this, "Contains: "+data, Toast.LENGTH_SHORT).show();



            }catch (Exception ex){
                Toast.makeText(this, "Reading error", Toast.LENGTH_SHORT).show();

            }



        }catch (Exception ex){
            Toast.makeText(this, "Error while writinig", Toast.LENGTH_SHORT).show();
        }

    }

    void AssFareForAllNode(){
        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Rates");

        ReferenceNode.child("Dubai").setValue(500);
        ReferenceNode.child("Fujairah").setValue(600);
        ReferenceNode.child("Abu Dabhi").setValue(900);
        ReferenceNode.child("Bahawalpur").setValue(400);
        ReferenceNode.child("Lodhran").setValue(300);



    }

    void AddPercentage(){

        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Multi");

        ReferenceNode.setValue(100);
    }







}
