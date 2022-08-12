package com.example.login18april;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.login18april.TrainClasses.TrainStationEmployee;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.util.Date;

public class AddNewEmployeeByStationMaster extends AppCompatActivity {


    private EditText NewEmployeeName, NewEmployeePassword, NewEmployeeSalary;
    private Spinner NewEmployeeAge, NewEmployeeGender;

    SignInActivity sign = new SignInActivity();


    private String SelectedAge;
    private String SelectedGender;

    private Button AddNewEmployee;
    private String[] genders = {"Male", "Female"};

    private String[] ages = new String[48];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_employee_by_station_master);

        NewEmployeeName = findViewById(R.id.ETAddNewEmployeeByStationMasterNameAsking);
        NewEmployeePassword = findViewById(R.id.ETAddNewEmployeeByStationMasterPasswordAsking);
        NewEmployeeSalary = findViewById(R.id.ETAddNewEmployeeByStationMasterSalaryAsking);

        AddNewEmployee = findViewById(R.id.BTNAddNewEmployeeByStationMasterAddEmployeeToDatabase);

        NewEmployeeAge = findViewById(R.id.SPRAddNewEmployeeByStationMasterAgeAsking);
        NewEmployeeGender = findViewById(R.id.SPRAddNewEmployeeByStationMasterGenderAsking);


        NewEmployeeAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedAge = ages[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                SelectedAge = "Nothing";

            }
        });
        NewEmployeeGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedGender = genders[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                SelectedGender = "Nothing";

            }
        });


        for (int i = 0; i < 48; i++) {
            ages[i] = (i + 12 + "");
        }

        ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ages);
        NewEmployeeAge.setAdapter(ageAdapter);


        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genders);
        NewEmployeeGender.setAdapter(genderAdapter);


        AddNewEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String employeeNameTemp = NewEmployeeName.getText().toString();
                String employeePasswordTemp = NewEmployeePassword.getText().toString();
                String employeeGenderTemp = SelectedGender;
                String employeeAgeTemp = SelectedAge;
                String salary = NewEmployeeSalary.getText().toString();


                if (employeeNameTemp.equals("")) {
                    sign.ShowDialogue("Please enter all filds", AddNewEmployeeByStationMaster.this);


                } else if (employeePasswordTemp.equals("")) {
                    sign.ShowDialogue("Please enter all filds", AddNewEmployeeByStationMaster.this);


                } else if (employeeNameTemp.length() < 8) {

                    sign.ShowDialogue("Name must be more than 8 characters", AddNewEmployeeByStationMaster.this);


                } else if (employeePasswordTemp.length() < 8) {
                    sign.ShowDialogue("Password must be more than 8 characters", AddNewEmployeeByStationMaster.this);


                } else if (employeeNameTemp.length() > 15 || employeePasswordTemp.length() > 15) {

                    sign.ShowDialogue("Character limit exceeded", AddNewEmployeeByStationMaster.this);

                } else if (employeeAgeTemp.equals("Nothing")) {

                    sign.ShowDialogue("Please select age", AddNewEmployeeByStationMaster.this);


                } else if (employeeGenderTemp.equals("Nothing")) {

                    sign.ShowDialogue("Please select gender", AddNewEmployeeByStationMaster.this);


                } else if (salary.length() == 0) {

                    sign.ShowDialogue("Please enter salary", AddNewEmployeeByStationMaster.this);

                } else {
                    int employeeSalaryTemp = Integer.parseInt(salary);

                    if (employeeSalaryTemp < 20000 || employeeSalaryTemp > 100000) {
                        sign.ShowDialogue("Salary can be between 1 lac and twenty thousands", AddNewEmployeeByStationMaster.this);

                    } else {

                        int employeeAgeTemp1 = Integer.parseInt(employeeAgeTemp);
                        TrainStationEmployee trainStationEmployee = new TrainStationEmployee(employeeNameTemp, employeePasswordTemp, employeeSalaryTemp, getTrainStaion(), new Date(), employeeGenderTemp, employeeAgeTemp1);
                        AddEmployeeUserToOnlineDatabase(trainStationEmployee);
                        sign.ShowDialogue("Employee Successfully added to " + getTrainStaion() + " Station", AddNewEmployeeByStationMaster.this);
                        NewEmployeeName.setText("");
                        NewEmployeePassword.setText("");
                        NewEmployeeSalary.setText("");
                    }

                }


            }
        });


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

        }
        return returner;
    }

    public void AddEmployeeUserToOnlineDatabase(TrainStationEmployee trainStationEmployee) {
        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Employees");

        String key = ReferenceNode.push().getKey();

        try {

            ReferenceNode.child(key).child("nameOfEmployee").setValue(trainStationEmployee.getNameOfEmployee());
            ReferenceNode.child(key).child("Password").setValue(trainStationEmployee.getPaassword());
            ReferenceNode.child(key).child("Age").setValue(trainStationEmployee.getAge());
            ReferenceNode.child(key).child("Gender").setValue(trainStationEmployee.getGender());
            ReferenceNode.child(key).child("Salary").setValue(trainStationEmployee.getSalary());
            ReferenceNode.child(key).child("workingatStation").setValue(trainStationEmployee.getWorkingatStation());


        } catch (Exception ex) {
            Toast.makeText(AddNewEmployeeByStationMaster.this, "Failed", Toast.LENGTH_SHORT).show();
            //ReferenceNode.child(key).removeValue();

        }

    }


}
