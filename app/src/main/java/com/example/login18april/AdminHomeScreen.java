package com.example.login18april;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

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

public class AdminHomeScreen extends AppCompatActivity {
    private final static String TAG = "AdminScreen";
    RecyclerView recyclerView;
    static List<ClientUser> clientUsersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_screen);



        recyclerView = findViewById(R.id.RVShowAllEmployeesToStationMasterScreenRecyclerForShowingEmployees);

        //Layout added
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Adapter adding
        //clientUsersList = new ArrayList<>();
        updateClienUserLocalDatabase();
        AddToList();

        AdapterForShowingUsers adapterForShowingUsers = new AdapterForShowingUsers(AdminHomeScreen.clientUsersList);
        recyclerView.setAdapter(adapterForShowingUsers);


    }



    public void AddToList(){
        try{

            //For reading data from thr file
            FileInputStream fis = openFileInput("UserDataLocal");
            int size = fis.available();


            byte[] data = new byte[size];
            fis.read(data);
            String text = new String(data);
            fis.close();


            //Splitting data of all users into same array
            String[] usersArray = text.split("\n", 0);
            int numberOfUsers = usersArray.length;


            String[] usernameArray = new String[numberOfUsers];
            String[] passwordArray = new String[numberOfUsers];


            for (int i = 0; i < usersArray.length; i++) {
                String[] usernametemp = usersArray[i].split("%%%%%", 0);
                usernameArray[i] = usernametemp[0];
                passwordArray[i] = usernametemp[1];

            }





            for (int i = 0;i<usersArray.length;i++){
                AdminHomeScreen.clientUsersList.add(new ClientUser(usernameArray[i],passwordArray[i]));
            }


        }catch (Exception ex){

        }


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
            Toast.makeText(AdminHomeScreen.this, "Unknown error occured", Toast.LENGTH_SHORT).show();

        }


    }
}
