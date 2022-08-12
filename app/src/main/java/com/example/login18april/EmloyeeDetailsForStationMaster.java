package com.example.login18april;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class EmloyeeDetailsForStationMaster extends AppCompatActivity {
    private String NameOfEmployee;

    private ImageView EmployeeImage;
    private TextView EmployeeNameShowing, GenderShowing, SalaryShowing, DateOfJoinShowing;
    private Button ButtonToStartChat, ButtonToUpdateSalary;
    private EditText WriteNewSalary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emloyee_details_for_station_master);

        Intent it = getIntent();
        this.NameOfEmployee = it.getStringExtra("nameOfEmployee");


        EmployeeImage = findViewById(R.id.IVEmployeeDetailsForStationMasterEmployeeImageShowing);
        EmployeeNameShowing = findViewById(R.id.TVEmployeeDetailsForStationMasterEmployeenameShowing);
        EmployeeNameShowing.setText(NameOfEmployee);

        GenderShowing = findViewById(R.id.TVEmployeeDetailsForStationMasterGenderShowing);
        ButtonToStartChat = findViewById(R.id.BTNEmployeeDetailsForStationMasterStartChat);
        ButtonToUpdateSalary = findViewById(R.id.BTNEmployeeDetailsForStationMasterUpdateSalary);
        SalaryShowing = findViewById(R.id.TVEmployeeDetailsForStationMasterSalaryShowing);
        DateOfJoinShowing = findViewById(R.id.TVEmployeeDetailsForStationMasterDateOfJoinShowing);
        WriteNewSalary = findViewById(R.id.ETEmployeeDetailsForStationMasterEnterNewSalary);

        LoadEmployeeData();

        ButtonToUpdateSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (WriteNewSalary.getVisibility() == View.INVISIBLE) {
                    WriteNewSalary.setVisibility(View.VISIBLE);
                } else {

                    String newSalaryTemp = WriteNewSalary.getText().toString();

                    if (newSalaryTemp.equals("")) {
                        ShowDialogue("Please enter new Salary for the user", EmloyeeDetailsForStationMaster.this);

                    }
                    try {
                        int salary = Integer.parseInt(newSalaryTemp);
                        if (salary < 20000) {
                            ShowDialogue("Salary cant be less than 10 thosands", EmloyeeDetailsForStationMaster.this);

                        }
                        if (salary > 100000) {
                            ShowDialogue("Salary cant be more than one lac", EmloyeeDetailsForStationMaster.this);
                        }else{
                            updateEmployeeSalary(salary);
                        }


                    } catch (Exception ex) {
                        ShowDialogue("Please enter proper Salary value", EmloyeeDetailsForStationMaster.this);
                    }
                }
            }
        });


        ButtonToStartChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(EmloyeeDetailsForStationMaster.this,StationMasterToEmployeeChat.class);
                it.putExtra("EmployeeName",NameOfEmployee);
                startActivity(it);




            }
        });


    }


    public void LoadEmployeeData() {

        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Employees");


        ReferenceNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();

                for (DataSnapshot child : iterable) {

                    Map<String, Object> perUserData = (Map<String, Object>) child.getValue();

                    String username_temp = (String) perUserData.get("nameOfEmployee");
                    if (username_temp.equals(NameOfEmployee)) {
                        String age_temp = perUserData.get("Age").toString();
                        String gender_temp = perUserData.get("Gender").toString();
                        String salary_temp = perUserData.get("Salary").toString();

                        SalaryShowing.setText("Salary is:" + salary_temp);



                        try {

                            if (gender_temp.equals("Male")) {
                                GenderShowing.setText("M");
                                EmployeeImage.setImageResource(R.drawable.male1);
                            } else {
                                GenderShowing.setText("F");
                                EmployeeImage.setImageResource(R.drawable.female1);
                            }
                        } catch (Exception ex) {

                            GenderShowing.setText("E");

                        }

                        break;
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void updateEmployeeSalary(final double salary){

        final FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Employees");


        ReferenceNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();

                for (DataSnapshot child: iterable){

                    Map<String, Object> perEmployeeData = (Map<String, Object>) child.getValue();

                    String employeeName = (String) perEmployeeData.get("nameOfEmployee");
                    if (employeeName.equals(NameOfEmployee)){


                        DatabaseReference databaseReference = OnlineDatabase.getReference("Employees"+"/"+child.getKey()+"/"+"Salary");
                        databaseReference.setValue(salary);
                        ShowDialogue("Salary Updated",EmloyeeDetailsForStationMaster.this);
                        break;

                    }
                }


//                        String age_temp = perUserData.get("Age").toString();
//                        String gender_temp = perUserData.get("Gender").toString();
//                        String salary_temp = perUserData.get("Salary").toString();
//
//                        SalaryShowing.setText("Salary is:" + salary_temp);


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


}
