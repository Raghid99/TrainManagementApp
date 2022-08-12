package com.example.login18april;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;

public class SignUPActivity extends AppCompatActivity {

    private String SelectedAge;

    private EditText UsernameET, PasswordET, ConfirmPasswordET;
    private TextView ShowData;
    private Button SignUPBTN, SignINInstead;
    private FirebaseDatabase Database;
    private DatabaseReference ReferenceNode;
    private RadioGroup GenderAsking;
    private RadioButton Male, Female;
    private Spinner AgeSelectionSPR;
    private String[] ages = new String[48];
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        try {
            if (!hasInternetConnection()) {
                ShowDialogue("No Internet Connection available", this);
            }
        } catch (Exception ex) {
        }


        UsernameET = findViewById(R.id.ETSignUPUsername);
        PasswordET = findViewById(R.id.ETSignUPPassword);
        ConfirmPasswordET = findViewById(R.id.ETSignUPConfirmUsername);
        SignUPBTN = findViewById(R.id.BTNSignUPSignUp);
        SignINInstead = findViewById(R.id.BTNSignUPSignInInstead);

        AgeSelectionSPR = findViewById(R.id.SPRSignUPAgeSelect);

        AgeSelectionSPR.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedAge = ages[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        GenderAsking = findViewById(R.id.RGSignUPGenderOptions);
        Male = findViewById(R.id.RBSignUPaleGender);
        Female = findViewById(R.id.RBSignUPFemaleGender);


        ShowData = findViewById(R.id.TVShowingData);

        //AgeSelection
        for (int i = 0; i < 48; i++) {
            ages[i] = (i + 12 + "");
        }


        ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ages);
        AgeSelectionSPR.setAdapter(ageAdapter);

        Database = FirebaseDatabase.getInstance();
        ReferenceNode = Database.getReference("Users");

        SignINInstead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(SignUPActivity.this, SignInActivity.class);
                startActivity(it);


            }
        });


        SignUPBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username_temp = UsernameET.getText().toString();
                String password_temp = PasswordET.getText().toString();
                String confirmPassword_temp = ConfirmPasswordET.getText().toString();

                if (username_temp.length() == 0) {
                    PasswordET.setText("");
                    ConfirmPasswordET.setText("");
                    ShowDialogue("Please enter Username and all fields", SignUPActivity.this);

                } else if (password_temp.length() == 0) {
                    PasswordET.setText("");
                    ConfirmPasswordET.setText("");
                    ShowDialogue("Please enter Password and all fields", SignUPActivity.this);

                } else if (CheckIfPasswordHasSpaces(password_temp)) {

                    ShowDialogue("Password must not contain white spaces", SignUPActivity.this);

                } else if (CheckIfeveryCharacterIsSpace(username_temp)) {

                    ShowDialogue("Username Are All spaces", SignUPActivity.this);

                } else if (confirmPassword_temp.length() == 0) {

                    PasswordET.setText("");
                    ConfirmPasswordET.setText("");
                    ShowDialogue("Please Confirm Password and fill all fields", SignUPActivity.this);


                } else if (username_temp.length() < 8) {

                    PasswordET.setText("");
                    ConfirmPasswordET.setText("");
                    UsernameET.setText("");
                    ShowDialogue("Please enter Username of more than 8 characters", SignUPActivity.this);


                } else if (password_temp.length() < 8) {

                    PasswordET.setText("");
                    ConfirmPasswordET.setText("");
                    ShowDialogue("Please enter Password of more than 8 characters", SignUPActivity.this);


                } else if (confirmPassword_temp.length() < 8) {

                    PasswordET.setText("");
                    ConfirmPasswordET.setText("");
                    ShowDialogue("Password dind't match. Try Again", SignUPActivity.this);


                } else if (!password_temp.equals(confirmPassword_temp)) {

                    PasswordET.setText("");
                    ConfirmPasswordET.setText("");
                    ShowDialogue("Password dind't match. Try Again", SignUPActivity.this);

                } else if (!Male.isChecked() && !Female.isChecked()) {

                    ShowDialogue("Please select Gender", SignUPActivity.this);


                } else {


                    try {
                        String gender;

                        if (Male.isChecked()) {
                            gender = "Male";

                        } else if (Female.isChecked()) {
                            gender = "Female";
                        } else {
                            gender = "Unknown";
                        }


                        ClientUser clientUser = new ClientUser(username_temp, password_temp, Integer.parseInt(SelectedAge), "Nothing", false, gender);


                        try {
                            updateClienUserLocalDatabase();
                        } catch (Exception ex) {
                            Toast.makeText(context, "Error while updating local database", Toast.LENGTH_SHORT).show();
                        }

                        try {
                            checkIfClientUserAlreadyExist(clientUser);
                        } catch (Exception ex) {
                            Toast.makeText(SignUPActivity.this, "Error while checking if user alreafy exist", Toast.LENGTH_SHORT).show();
                        }


                        if (!checkIfClientUserAlreadyExist(clientUser)) {
                            try {
                                AddClientUserToOnlineDatabase(clientUser);
                                setAccounLocalDataForClientUsers(clientUser);

                            } catch (Exception ex) {
                                Toast.makeText(SignUPActivity.this, "Adding user To Online Database failed", Toast.LENGTH_SHORT).show();

                            }



                            Intent it = new Intent(SignUPActivity.this, ClientUserHomeScreenFinal.class);
                            startActivity(it);

                        }

                    } catch (Exception ex) {
                        Toast.makeText(SignUPActivity.this, "Unknown Error", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(SignUPActivity.this, "Unknown error occured", Toast.LENGTH_SHORT).show();

        }


    }

    public boolean checkIfClientUserAlreadyExist(ClientUser clientUser) {
        boolean returner = false;

        try {

            //For reading data from thr file
            FileInputStream fis = null;
            try {
                fis = openFileInput("UserDataLocal");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                File file = new File(getBaseContext().getFilesDir(), "UserDataLocal");
                file.createNewFile();
                fis = new FileInputStream(file);
            }
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
                if (clientUser.getUsername().equals(s)) {
                    ShowDialogue("User Already Exist! Try other username", this);
                    UsernameET.setText("");
                    PasswordET.setText("");
                    ConfirmPasswordET.setText("");
                    //Only will return true in this condition
                    returner = true;
                }
            }
            return returner;


        } catch (Exception ex) {
            Log.d(getLocalClassName(), "checkIfClientUserAlreadyExist:", ex);
            return false;
        }


    }

    public void AddClientUserToOnlineDatabase(ClientUser clientUser) {
        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Users");

        String key = ReferenceNode.push().getKey();

        try {

            ReferenceNode.child(key).child("Username").setValue(clientUser.getUsername());
            ReferenceNode.child(key).child("Password").setValue(clientUser.getPassword());
            ReferenceNode.child(key).child("Age").setValue(clientUser.getAge());
            ReferenceNode.child(key).child("HasBooking").setValue(clientUser.isHasBooking());
            ReferenceNode.child(key).child("BookingStatus").setValue(clientUser.getBookingStatus());
            ReferenceNode.child(key).child("Gender").setValue(clientUser.getGender());
            ReferenceNode.child(key).child("ClientUserID").setValue(clientUser.getClientUserID());


        } catch (Exception ex) {
            ReferenceNode.child(key).removeValue();
            Log.d(getLocalClassName(), "AddClientUserToOnlineDatabase: ", ex);
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

    public String getUserGenderFromLocalFile() {
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
                return accountInfo[2];

            }

        } catch (Exception ex) {
            return null;
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
