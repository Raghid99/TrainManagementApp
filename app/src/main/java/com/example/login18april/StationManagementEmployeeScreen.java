package com.example.login18april;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.login18april.TrainClasses.TrainStationEmployee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StationManagementEmployeeScreen extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterForShowingEmployeeToStationManagement adapterForShowingEmployeeToStationManagement;
    Button AddNewEmployee;


    static List<TrainStationEmployee> trainStationEmployeeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_management_employee_screen);
        try{
            addEmployeesOfSpecificStationToLocalDatabase();


        }catch (Exception ex){

        }finally {
            addEmployeesOfSpecificStationToLocalDatabase();
        }


        recyclerView = findViewById(R.id.RVStationManagementShowingAllEmployees);
        AddNewEmployee = findViewById(R.id.BTNStationManagementEmployeeScreenAddEmployee);


        //Layout Manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        addEmloyeeToRecyclerList();


        adapterForShowingEmployeeToStationManagement = new AdapterForShowingEmployeeToStationManagement(trainStationEmployeeList);
        recyclerView.setAdapter(adapterForShowingEmployeeToStationManagement);


        adapterForShowingEmployeeToStationManagement.notifyDataSetChanged();


        AddNewEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    trainStationEmployeeList.clear();
                    Intent it = new Intent(StationManagementEmployeeScreen.this,AddNewEmployeeByStationMaster.class);
                    startActivity(it);
            }
        });


    }


    public void addEmployeesOfSpecificStationToLocalDatabase() {

        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Employees");


        ReferenceNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {

                    FileOutputStream fos = openFileOutput("EmployeeListForStationMaster", MODE_PRIVATE);
                    fos.close();

                } catch (Exception ex) {


                }

                String stationName = getTrainStaion();

                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();

                for (DataSnapshot child : iterable) {


                    Map<String, Object> perEmployeeData = (Map<String, Object>) child.getValue();
                    String employeeStation = (String) perEmployeeData.get("workingatStation");
                    String employeeName = (String) perEmployeeData.get("nameOfEmployee");


                    if (employeeStation.equals(stationName)) {
                        try {
                            FileOutputStream fos = openFileOutput("EmployeeListForStationMaster", MODE_APPEND);
                            fos.write((employeeName + "\n").getBytes());
                            fos.close();


                        } catch (Exception ex) {
                            Toast.makeText(StationManagementEmployeeScreen.this, "writng error", Toast.LENGTH_SHORT).show();

                        }

                    }else{
                        continue;
                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();

        }
        return returner;
    }


    public void updateEmployeeUserLocalDatabase() {
        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Employees");


        try {
            ReferenceNode.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    //Used for reseeting all the old text in the file
                    try {

                        FileOutputStream fos = openFileOutput("EmployeeDataLocal", Context.MODE_PRIVATE);
                        fos.close();

                    } catch (Exception ex) {
                    }


                    try {

                        Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();

                        for (DataSnapshot child : iterable) {

                            Map<String, Object> perUserData = (Map<String, Object>) child.getValue();

                            String username_temp = (String) perUserData.get("nameOfEmployee");
                            String password_temp = (String) perUserData.get("Password");

                            String singleEmployeeData = username_temp + "%%%%%" + password_temp + "\n";


                            try {
                                FileOutputStream fos = openFileOutput("EmployeeDataLocal", Context.MODE_APPEND);
                                fos.write(singleEmployeeData.getBytes());
                                fos.close();

                            } catch (Exception e) {
                            }

                        }

                        try {
                            FileInputStream fis = openFileInput("EmployeeDataLocal");
                            int size = fis.available();

                            byte[] data = new byte[size];
                            fis.read(data);
                            String text = new String(data);
                            fis.close();
                            Toast.makeText(StationManagementEmployeeScreen.this, text, Toast.LENGTH_SHORT).show();

                        } catch (Exception ex) {

                        }


                    } catch (Exception e) {
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } catch (Exception ex) {

        }


    }


    public boolean checkIfEmployeeUserAlreadyExist(TrainStationEmployee trainStationEmployee) {
        boolean returner = false;

        try {

            //For reading data from thr file
            FileInputStream fis = openFileInput("EmployeeDataLocal");
            int size = fis.available();

            byte[] data = new byte[size];
            fis.read(data);
            String text = new String(data);
            fis.close();


            String[] usersArray = text.split("\n", 0);
            int numberOfUsers = usersArray.length;

            String[] usernameArray = new String[numberOfUsers];

            //Creating usename array of each user
            for (int i = 0; i < usersArray.length; i++) {
                String[] usernametemp = usersArray[i].split("%%%%%", 0);
                usernameArray[i] = usernametemp[0];

            }


            //Checking in the username array if the username is already taken
            for (String s : usernameArray) {
                if (trainStationEmployee.getNameOfEmployee().equals(s)) {
//                    UsernameET.setText("");
//                    PasswordET.setText("");
//                    ConfirmPasswordET.setText("");
//                    //Only will return true in this condition
                    returner = true;
                }
            }
            return returner;


        } catch (Exception ex) {
            return true;
        }


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
            ReferenceNode.child(key).removeValue();

        }

    }


    public void addEmloyeeToRecyclerList() {
        try {

            //For reading data from thr file
            FileInputStream fis = openFileInput("EmployeeListForStationMaster");
            int size = fis.available();


            byte[] data = new byte[size];
            fis.read(data);
            String text = new String(data);
            fis.close();


            //Splitting data of all users into same array
            String[] usersArray = text.split("\n", 0);
            int numberOfUsers = usersArray.length;

            boolean isFound = false;


            for (int i = 0; i < usersArray.length; i++) {
                String usernametemp = usersArray[i];

                for (TrainStationEmployee s : trainStationEmployeeList) {
                    if (usernametemp.equals(s)) {
                        isFound = true;
                    }
                }
                if (!isFound) {

                    StationManagementEmployeeScreen.trainStationEmployeeList.add(new TrainStationEmployee(usernametemp));
                }

            }
            adapterForShowingEmployeeToStationManagement.notifyDataSetChanged();

        } catch (Exception ex) {

        }


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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        trainStationEmployeeList.clear();
    }
}
