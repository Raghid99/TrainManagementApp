package com.example.login18april;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.login18april.TrainClasses.TrainStationEmployee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;

public class ShowingPayToEmployee extends AppCompatActivity {
    TextView NameOFEmployee,SalaryOfEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showing_pay_to_employee);
        NameOFEmployee = findViewById(R.id.TVSaamelaryCheckingName);
        NameOFEmployee.setText(getAppUserNameFromLocaFile());

        SalaryOfEmployee = findViewById(R.id.TVSaamelaryCheckingSalary);

        CheckSalary();





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

    public void CheckSalary(){
        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Employees");

        ReferenceNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();

                for (DataSnapshot child : iterable) {

                    TrainStationEmployee trainStationEmployee = child.getValue(TrainStationEmployee.class);
                    if (trainStationEmployee.getNameOfEmployee().equals(getAppUserNameFromLocaFile())){

                        SalaryOfEmployee.setText("Your current salary is: "+trainStationEmployee.getSalary());
                        break;

                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

}
