package com.example.login18april;

import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class EmployeeToStationMasterChat extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageButton SendChat;
    EditText SendMessage;
    TextView Username;
    AdapterForEmployeeToStationMasterChat adapter;
    static List<Chat> chatsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_to_station_master_chat);

        recyclerView = findViewById(R.id.RVCEmployeeToStationMasterChat);
        SendChat = findViewById(R.id.BTNEmployeeToStationMasterChatsendText);
        Username = findViewById(R.id.TVEmployeeToStationMasterChatUsernameShowing);

        Username.setText(getAppUserNameFromLocaFile());

        SendMessage = findViewById(R.id.ETEmployeeToStationMasterChatSendMessage);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new AdapterForEmployeeToStationMasterChat(getAppUserNameFromLocaFile(), chatsList);

        recyclerView.setAdapter(adapter);


        setStationMasterNameForEmployeeToLocalData();

        if (LoadStationMasterForEmpployee().equals("Error")) {

            setStationMasterNameForEmployeeToLocalData();

        }

        ImportOnlineChat();
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);




        SendChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddChattoDataBase();
                recyclerView.scrollToPosition(adapter.getItemCount());

            }
        });

    }

    public void ImportOnlineChat() {
        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("StationMasterToEmployeeChat" + "/" + LoadStationMasterForEmpployee() + "To" + getAppUserNameFromLocaFile());

        ReferenceNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();


                for (DataSnapshot child : iterable) {
                    Map<String, Object> perChatData = (Map<String, Object>) child.getValue();


                    String from = (String) perChatData.get("from");
                    String to = (String) perChatData.get("to");
                    String chatData = (String) perChatData.get("chatData");
                    String chatID_temp = (String) perChatData.get("chatID");
                    int chatID;

                    try {
                        chatID = Integer.parseInt(chatID_temp);

                    } catch (Exception ex) {
                        chatID = 11;

                    }

                    if (chatID == 11) {
                        continue;
                    }


                    Chat c = new Chat(from, to, chatData, chatID);
                    boolean chatAlreadyExists = false;

                    for (Chat t : chatsList) {
                        if (c.getChatID() == t.getChatID()) {
                            chatAlreadyExists = true;
                            break;
                        }

                    }

                    if (!chatAlreadyExists) {
                        chatsList.add(c);
                        adapter.notifyDataSetChanged();
                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void AddChattoDataBase() {
        SendMessage = findViewById(R.id.ETEmployeeToStationMasterChatSendMessage);
        String chatData = SendMessage.getText().toString();
        if (chatData.length() == 0) {

        } else {

            try {
                Chat c = new Chat(getAppUserNameFromLocaFile(), LoadStationMasterForEmpployee(), new Date(), chatData);
                chatsList.add(c);
                SendMessage.setText("");
                String referenceToSpecificChat = "StationMasterToEmployeeChat" + "/" + LoadStationMasterForEmpployee() + "To" + getAppUserNameFromLocaFile();


                FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
                DatabaseReference ReferenceNode = OnlineDatabase.getReference("StationMasterToEmployeeChat" + "/" + LoadStationMasterForEmpployee() + "To" + getAppUserNameFromLocaFile());


                String key = ReferenceNode.child(referenceToSpecificChat).push().getKey();

                ReferenceNode.child(key).child("from").setValue(c.getFrom());
                ReferenceNode.child(key).child("to").setValue(LoadStationMasterForEmpployee());
                ReferenceNode.child(key).child("chatData").setValue(c.getChatData());
                ReferenceNode.child(key).child("dateOfsend").setValue(c.getDateOfsend());
                ReferenceNode.child(key).child("chatID").setValue(c.getChatID() + "");

            } catch (Exception ex) {
                Toast.makeText(EmployeeToStationMasterChat.this, "Failed to add", Toast.LENGTH_SHORT).show();
            }

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

    public void setStationMasterNameForEmployeeToLocalData() {
        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("StationMasters");


        ReferenceNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();

                for (DataSnapshot child : iterable) {
                    Map<String, Object> perStationMasterData = (Map<String, Object>) child.getValue();


                    String stationMasterName = (String) perStationMasterData.get("stationMasterName");
                    String workingStation = (String) perStationMasterData.get("workingStationName");

                    if (workingStation.equals(getTrainStaion())) {
                        try {
                            FileOutputStream fos = openFileOutput("MyStationMaster", MODE_PRIVATE);
                            fos.write(stationMasterName.getBytes());
                            fos.close();
                            break;

                        } catch (Exception ex) {

                        }
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public String LoadStationMasterForEmpployee() {
        try {
            FileInputStream fis = openFileInput("MyStationMaster");
            int size = fis.available();

            byte[] data = new byte[size];
            fis.read(data);

            String text = new String(data);
            return text;


        } catch (Exception ex) {
            return "Error";
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


}


//    public void AddEmployeeUserToOnlineDatabase(TrainStationEmployee trainStationEmployee) {
//        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference ReferenceNode = OnlineDatabase.getReference("TrainStations"+"/"+"-LdzC0tEUsoszCNf-0-S"+"/"+"Employees");
//
//        String key = ReferenceNode.push().getKey();
//
//        try {
//
//            ReferenceNode.child(key).child("nameOfEmployee").setValue(trainStationEmployee.getNameOfEmployee());
//            ReferenceNode.child(key).child("Password").setValue(trainStationEmployee.getPaassword());
//            ReferenceNode.child(key).child("Age").setValue(trainStationEmployee.getAge());
//            ReferenceNode.child(key).child("Gender").setValue(trainStationEmployee.getGender());
//            ReferenceNode.child(key).child("Salary").setValue(trainStationEmployee.getSalary());
//            ReferenceNode.child(key).child("workingatStation").setValue(trainStationEmployee.getWorkingatStation());
//
//
//        } catch (Exception ex) {
//            ReferenceNode.child(key).removeValue();
//
//        }
//
//    }


