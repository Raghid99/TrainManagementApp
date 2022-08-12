package com.example.login18april;

import android.accounts.Account;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login18april.TrainClasses.TrainStaionMaster;
import com.example.login18april.TrainClasses.TrainStationEmployee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {
    EditText Username, Password;
    Button SignIN, SignUPInstead;
    Spinner AccountType;
    String[] accountTypersArray = {"User", "Station Master", "Employee", "Support Assist"};
    String SelectedAccountType;
    String EmployeeStationGlobal = "";

    Context context;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        AccountType = findViewById(R.id.SPRSignInSelectAccountType);

        try {
            updateClienUserLocalDatabase();
            updateStationMasterLocalDatabase();
            updateEmployeeLocalDatabase();
        } catch (Exception ex) {

        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, accountTypersArray);

        AccountType.setAdapter(arrayAdapter);

        AccountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedAccountType = accountTypersArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Username = findViewById(R.id.ETSignInUsername);
        Password = findViewById(R.id.ETSignInPassword);


        SignIN = findViewById(R.id.BTNSignInSignIN);
        SignUPInstead = findViewById(R.id.BTNSignInSignUPInstead);


        SignUPInstead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SignInActivity.this, SignUPActivity.class);
                startActivity(it);

            }
        });


        SignIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username_temp = Username.getText().toString();
                final String password_temp = Password.getText().toString();
                final String SelectedAccountType = AccountType.getSelectedItem().toString();


                if (username_temp.length() == 0) {
                    ShowDialogue("Please enter Name", SignInActivity.this);

                    Password.setText("");

                } else if (username_temp.length() < 8) {

                    Password.setText("");

                } else if (password_temp.length() == 0) {

                    ShowDialogue("Please Password Name", SignInActivity.this);


                } else if (password_temp.length() < 8) {

                    ShowDialogue("Please enter password Name", SignInActivity.this);

                    Password.setText("");

                } else if (CheckIfeveryCharacterIsSpace(username_temp)) {

                    ShowDialogue("Please enter proper name", SignInActivity.this);


                } else if (CheckIfPasswordHasSpaces(password_temp)) {

                    ShowDialogue("Please enter password without spaces", SignInActivity.this);

                } else if (!hasInternetConnection()) {

                    ShowDialogue("No Internet Connection", SignInActivity.this);


                } else {

                    if (SelectedAccountType.equals(accountTypersArray[0])) {
                        try {
                            updateClienUserLocalDatabase();
                        } catch (Exception ex) {

                        }
                        ClientUser clientUser = new ClientUser(username_temp, password_temp);

                        String userStatus = checkIfUserExist(clientUser);

                        if (userStatus.equals("userNotFound")) {

                            Password.setText("");

                            ShowDialogue("User Not Found", SignInActivity.this);

                        } else if (userStatus.equals("Error")) {
                            try {
                                updateClienUserLocalDatabase();
                            } catch (Exception ex) {

                            }
                            ShowDialogue("Error Communicating", SignInActivity.this);

                        } else if (userStatus.equals("userFound")) {
                            Password.setText("");

                            ShowDialogue("Password didn'nt match", SignInActivity.this);
                        } else {

                            setAccounLocalDataForClientUsers(clientUser);

                            Intent it = new Intent(SignInActivity.this, ClientUserHomeScreenFinal.class);
                            startActivity(it);

                        }


                    } else if (SelectedAccountType.equals(accountTypersArray[1])) {

                        try {
                            updateStationMasterLocalDatabase();
                        } catch (Exception ex) {

                        }


                        TrainStaionMaster trainStaionMaster = new TrainStaionMaster(username_temp, password_temp);

                        String userStatus = checkIfStationMasterExist(trainStaionMaster);


                        if (userStatus.equals("userNotFound")) {

                            Password.setText("");

                            ShowDialogue("User Not Found", SignInActivity.this);
                        } else if (userStatus.equals("Error")) {

                            try {
                                updateStationMasterLocalDatabase();
                            } catch (Exception ex) {

                            }
                            ShowDialogue("Error Communicating", SignInActivity.this);

                        } else if (userStatus.equals("userFound")) {

                            Password.setText("");
                            ShowDialogue("Password didn'nt match", SignInActivity.this);

                        } else {

                            try {
                                setAccountLocalDataForStationMaster(trainStaionMaster);
                            } catch (Exception ex) {

                            }

                            try {
                                getWorkingStationForStationMaster(trainStaionMaster);
                            } catch (Exception ex) {

                            }
                            Toast.makeText(SignInActivity.this, "Goining", Toast.LENGTH_SHORT).show();
                            Intent it = new Intent(SignInActivity.this, StationMasterHomeScreenFinal.class);
                            startActivity(it);


                        }


                    } else if (SelectedAccountType.equals(accountTypersArray[2])) {

                        updateEmployeeLocalDatabase();

                        TrainStationEmployee trainStationEmployee = new TrainStationEmployee(username_temp, password_temp);

                        String userStatus = checkIfEmployeeExist(trainStationEmployee);


                        if (userStatus.equals("userNotFound")) {

                            Password.setText("");

                            ShowDialogue("User Not Found", SignInActivity.this);
                        } else if (userStatus.equals("Error")) {

                            ShowDialogue("Error Communicating", SignInActivity.this);

                        } else if (userStatus.equals("userFound")) {

                            Password.setText("");
                            ShowDialogue("Password didn'nt match", SignInActivity.this);

                        } else {

                            setAccounLocalDataForEmployees(trainStationEmployee);
                            getWorkingStationForEmployee(trainStationEmployee);

                            Intent it = new Intent(SignInActivity.this, EmploeeHomeScreenFinal.class);
                            startActivity(it);

                        }


                    } else if (SelectedAccountType.equals(accountTypersArray[3])) {
                        if (username_temp.equals("AdminAccount") && password_temp.equals("password")) {
                            Intent it = new Intent(SignInActivity.this, AdminHomeScreen.class);
                            startActivity(it);
                        } else {
                            ShowDialogue("Please enter the account provided", SignInActivity.this);
                        }
                    }


                }

            }
        });
    }


    boolean CheckIfPasswordHasSpaces(String string) {
        boolean returner = false;

        char[] passwordCharArray = string.toCharArray();

        for (char c : passwordCharArray) {
            if (c == ' ') {
                returner = true;
            }
        }

        return returner;

    }

    boolean CheckIfeveryCharacterIsSpace(String string) {

        boolean returnner = true;

        char[] array = string.toCharArray();

        for (char c : array) {
            if (c != ' ') {
                returnner = false;
            }

        }
        return returnner;


    }


    public void updateClienUserLocalDatabase() {
        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Users");


        try {
            ReferenceNode.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    //Used for reseeting all the old text in the file
                    try {

                        FileOutputStream fos = openFileOutput("UserDataLocal", Context.MODE_PRIVATE);
                        fos.close();

                    } catch (Exception ex) {
                    }


                    try {

                        Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();

                        for (DataSnapshot child : iterable) {

                            Map<String, Object> perUserData = (Map<String, Object>) child.getValue();

                            String username_temp = (String) perUserData.get("Username");
                            String password_temp = (String) perUserData.get("Password");

                            String singleUserData = username_temp + "%%%%%" + password_temp + "\n";


                            try {
                                FileOutputStream fos = openFileOutput("UserDataLocal", Context.MODE_APPEND);
                                fos.write(singleUserData.getBytes());
                                fos.close();

                            } catch (Exception e) {
                            }

                        }

                        try {
                            FileInputStream fis = openFileInput("UserDataLocal");
                            int size = fis.available();

                            byte[] data = new byte[size];
                            fis.read(data);
                            String text = new String(data);
                            fis.close();

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

    public String checkIfUserExist(ClientUser clientUser) {
        String returner = "userNotFound";


        try {
            FileInputStream fis = openFileInput("UserDataLocal");
            int size = fis.available();
            byte[] data = new byte[size];
            fis.read(data);
            String text = new String(data);
            fis.close();


            String[] usersArray = text.split("\n", 0);

            int numberOfUsers = usersArray.length;

            String[] usernameArray = new String[numberOfUsers];
            String[] passwordArray = new String[numberOfUsers];

            for (int i = 0; i < usersArray.length; i++) {
                String[] usernametemp = usersArray[i].split("%%%%%", 0);
                usernameArray[i] = usernametemp[0];
                passwordArray[i] = usernametemp[1];
            }


            for (int i = 0; i < usersArray.length; i++) {
                if (clientUser.getUsername().equals(usernameArray[i])) {
                    returner = "userFound";
                    if (clientUser.getPassword().equals(passwordArray[i])) {
                        returner = usernameArray[i];
                        break;
                    }

                }
            }
            return returner;

        } catch (Exception e) {
            Toast.makeText(SignInActivity.this, "Data Verification failed", Toast.LENGTH_SHORT).show();
            return "Error";

        }


    }

    ///////////////////////////

    public void updateEmployeeLocalDatabase() {
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

                            String singleUserData = username_temp + "%%%%%" + password_temp + "\n";


                            try {
                                FileOutputStream fos = openFileOutput("EmployeeDataLocal", Context.MODE_APPEND);
                                fos.write(singleUserData.getBytes());
                                fos.close();

                            } catch (Exception e) {
                                ShowDialogue("Sigle writing error", SignInActivity.this);
                            }

                        }

                        try {

                            FileInputStream fis = openFileInput("UserDataLocal");
                            int size = fis.available();

                            byte[] data = new byte[size];
                            fis.read(data);
                            String text = new String(data);
                            fis.close();

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
            Toast.makeText(SignInActivity.this, "Unknown error occured", Toast.LENGTH_SHORT).show();

        }


    }

    public String checkIfEmployeeExist(TrainStationEmployee trainStationEmployee) {
        String returner = "userNotFound";


        try {
            FileInputStream fis = openFileInput("EmployeeDataLocal");
            int size = fis.available();
            byte[] data = new byte[size];
            fis.read(data);
            String text = new String(data);
            fis.close();


            String[] usersArray = text.split("\n", 0);

            int numberOfUsers = usersArray.length;

            String[] usernameArray = new String[numberOfUsers];
            String[] passwordArray = new String[numberOfUsers];

            for (int i = 0; i < usersArray.length; i++) {
                String[] usernametemp = usersArray[i].split("%%%%%", 0);
                usernameArray[i] = usernametemp[0];
                passwordArray[i] = usernametemp[1];
            }


            for (int i = 0; i < usersArray.length; i++) {
                if (trainStationEmployee.getNameOfEmployee().equals(usernameArray[i])) {
                    returner = "userFound";
                    if (trainStationEmployee.getPaassword().equals(passwordArray[i])) {
                        returner = usernameArray[i];
                        break;
                    }

                }
            }
            return returner;

        } catch (Exception e) {
            ShowDialogue(e.toString(), SignInActivity.this);
            return "Error";

        }


    }
    ///////////////////////////

    public void updateStationMasterLocalDatabase() {
        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("StationMasters");


        try {
            ReferenceNode.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    //Used for reseeting all the old text in the file
                    try {

                        FileOutputStream fos = openFileOutput("StationMasterDataLocal", Context.MODE_PRIVATE);
                        fos.close();

                    } catch (Exception ex) {
                    }


                    try {

                        Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();

                        for (DataSnapshot child : iterable) {

                            Map<String, Object> perUserData = (Map<String, Object>) child.getValue();

                            String username_temp = (String) perUserData.get("stationMasterName");
                            String password_temp = (String) perUserData.get("Password");

                            String singleUserData = username_temp + "%%%%%" + password_temp + "\n";


                            try {
                                FileOutputStream fos = openFileOutput("StationMasterDataLocal", Context.MODE_APPEND);
                                fos.write(singleUserData.getBytes());
                                fos.close();

                            } catch (Exception e) {
                            }

                        }

                        try {
                            FileInputStream fis = openFileInput("StationMasterDataLocal");
                            int size = fis.available();

                            byte[] data = new byte[size];
                            fis.read(data);
                            String text = new String(data);
                            fis.close();

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
            Toast.makeText(SignInActivity.this, "Unknown error occured", Toast.LENGTH_SHORT).show();

        }

    }

    public String checkIfStationMasterExist(TrainStaionMaster trainStaionMaster) {
        String returner = "userNotFound";


        try {
            FileInputStream fis = openFileInput("StationMasterDataLocal");
            int size = fis.available();
            byte[] data = new byte[size];
            fis.read(data);
            String text = new String(data);
            fis.close();


            String[] usersArray = text.split("\n", 0);

            int numberOfUsers = usersArray.length;

            String[] usernameArray = new String[numberOfUsers];
            String[] passwordArray = new String[numberOfUsers];

            for (int i = 0; i < usersArray.length; i++) {
                String[] usernametemp = usersArray[i].split("%%%%%", 0);
                usernameArray[i] = usernametemp[0];
                passwordArray[i] = usernametemp[1];
            }


            for (int i = 0; i < usersArray.length; i++) {
                if (trainStaionMaster.getStationMasterName().equals(usernameArray[i])) {
                    returner = "userFound";
                    if (trainStaionMaster.getPassword().equals(passwordArray[i])) {
                        returner = usernameArray[i];
                        break;
                    }

                }
            }
            return returner;

        } catch (Exception e) {
            ShowDialogue(e.toString(), SignInActivity.this);
            return "Error";

        }

    }
    ///////////////////////////

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

    public void setAccounLocalDataForClientUsers(ClientUser clientUser) {
        try {

            FileOutputStream fos = openFileOutput("AccountInfo", Context.MODE_PRIVATE);
            String accoutInfo = "ClientUser" + "%%%%%" + clientUser.getUsername() + "%%%%%" + clientUser.getGender();
            fos.write(accoutInfo.getBytes());
            fos.close();

        } catch (Exception ex) {


        }

    }


    //Setting ang getting local Account data for Employees
    public void setAccounLocalDataForEmployees(TrainStationEmployee trainStationEmployee) {
        try {

            FileOutputStream fos = openFileOutput("AccountInfo", Context.MODE_PRIVATE);

            String accountInfo = "Employee" + "%%%%%" + trainStationEmployee.getNameOfEmployee() + "%%%%%" + trainStationEmployee.getGender();
            //String accoutInfo = "ClientUser" + "%%%%%" + clientUser.getUsername() + "%%%%%" + clientUser.getGender();
            fos.write(accountInfo.getBytes());
            fos.close();

        } catch (Exception ex) {


        }

    }


    //Methods for getting details of account

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


    //Setting and getting local Account data for StationMaster

    public void setAccountLocalDataForStationMaster(TrainStaionMaster trainStaionMaster) {
        try {

            FileOutputStream fos = openFileOutput("AccountInfo", Context.MODE_PRIVATE);

            String accountInfo = "StationMaster" + "%%%%%" + trainStaionMaster.getStationMasterName() + "%%%%%" + trainStaionMaster.getGender();
            fos.write(accountInfo.getBytes());
            fos.close();

        } catch (Exception ex) {


        }


    }


/////////////////////////////////


    //Gerring working stations from Employee and StationMaster
    public void getWorkingStationForEmployee(final TrainStationEmployee trainStationEmployee) {

        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Employees");


        final String name_temp = trainStationEmployee.getNameOfEmployee();
        final String[] Station = new String[1];


        ReferenceNode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();


                for (DataSnapshot child : iterable) {
                    Map<String, Object> perEmployeeData = (Map<String, Object>) child.getValue();

                    String username_on_database = (String) perEmployeeData.get("nameOfEmployee");

                    if (username_on_database.equals(name_temp)) {
                        String EmployeeStationl = (String) perEmployeeData.get("workingatStation");

                        try {
                            FileOutputStream fos = openFileOutput("WorkingStation", MODE_PRIVATE);
                            fos.write(EmployeeStationl.getBytes());
                            fos.close();
                        } catch (Exception ex) {
                            Toast.makeText(SignInActivity.this, "Writning error", Toast.LENGTH_SHORT).show();

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

    public void getWorkingStationForStationMaster(final TrainStaionMaster trainStaionMaster) {

        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("StationMasters");


        final String name_temp = trainStaionMaster.getStationMasterName();
        final String[] Station = new String[1];


        ReferenceNode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();


                for (DataSnapshot child : iterable) {
                    Map<String, Object> perStationMasterData = (Map<String, Object>) child.getValue();

                    String username_on_database = (String) perStationMasterData.get("stationMasterName");

                    if (username_on_database.equals(name_temp)) {
                        String EmployeeStationl = (String) perStationMasterData.get("workingStationName");

                        try {
                            FileOutputStream fos = openFileOutput("WorkingStation", MODE_PRIVATE);
                            fos.write(EmployeeStationl.getBytes());
                            fos.close();
                        } catch (Exception ex) {
                            Toast.makeText(SignInActivity.this, "Writning error", Toast.LENGTH_SHORT).show();

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


    public boolean hasInternetConnection() {

        boolean hasWifi = false;
        boolean hasMobileData = false;


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo[] allNetworksInfo = connectivityManager.getAllNetworkInfo();


        for (NetworkInfo info : allNetworksInfo) {
            if (info.getTypeName().equalsIgnoreCase("WIFI")) {
                if (info.isConnected()) {
                    hasWifi = true;
                }

            } else if (info.getTypeName().equalsIgnoreCase("MOBILE")) {
                if (info.isConnected()) {
                    hasMobileData = true;
                }


            }

        }

        return (hasMobileData || hasWifi);


    }


}

