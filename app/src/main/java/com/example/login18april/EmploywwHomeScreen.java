package com.example.login18april;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

public class EmploywwHomeScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employww_home_screen);

    }

    public void addEmployeesOfSpecificStationToLocalDatabase(){

        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Employees");


        ReferenceNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String stationName = getTrainStaion();

                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();

                for (DataSnapshot child: iterable){


                    Map<String,Object> perEmployeeData = (Map<String, Object>) child.getValue();
                    String employeeStation = (String) perEmployeeData.get("workingatStation");
                    String employeeName = (String) perEmployeeData.get("nameOfEmployee");

                    if (employeeStation.equals(stationName)){
                        try{
                            FileOutputStream fos = openFileOutput("EmployeeListForStationMaster",MODE_PRIVATE);
                            fos.write(employeeName.getBytes());


                        } catch (Exception ex){
                            Toast.makeText(EmploywwHomeScreen.this, "writng error", Toast.LENGTH_SHORT).show();

                        }

                    }




                }







            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        try {


        }catch (Exception ex){

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



}
