package com.example.login18april;

import android.content.Intent;
import androidx.annotation.NonNull;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class StationMasterToEmployeeChat extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageButton SendChat;
    EditText SendMessage;
    TextView Username;
    String EmployeeNameForChatting;
    AdapterForStationMasterToEmployeeChat adapter;
    static List<Chat> chatsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_master_to_employee_chat);


        recyclerView = findViewById(R.id.RVStationMasterToEmployeeChatScreen);
        SendChat = findViewById(R.id.BTNEmployeeToStationMasterChatsendText);
        SendMessage = findViewById(R.id.ETStationMasterToEmployeeSendMessage);
        Username = findViewById(R.id.TVStationMasterToEmployeeUsernameShowing);


        Intent it = getIntent();
        this.EmployeeNameForChatting = it.getStringExtra("EmployeeName");
        Username.setText("Chat with "+EmployeeNameForChatting);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        adapter = new AdapterForStationMasterToEmployeeChat(getAppUserNameFromLocaFile(), chatsList);
        recyclerView.setAdapter(adapter);

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
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("StationMasterToEmployeeChat" + "/" + getAppUserNameFromLocaFile() + "To" + EmployeeNameForChatting);

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
        SendMessage = findViewById(R.id.ETStationMasterToEmployeeSendMessage);
        String chatData = SendMessage.getText().toString();
        if (chatData.length() == 0) {

        } else {

            try {
                Chat c = new Chat(getAppUserNameFromLocaFile(), EmployeeNameForChatting, new Date(), chatData);
                chatsList.add(c);
                SendMessage.setText("");
                String referenceToSpecificChat = getAppUserNameFromLocaFile() + "To" + EmployeeNameForChatting;


                FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
                DatabaseReference ReferenceNode = OnlineDatabase.getReference("StationMasterToEmployeeChat" + "/" + getAppUserNameFromLocaFile() + "To" + EmployeeNameForChatting);


                String key = ReferenceNode.child(referenceToSpecificChat).push().getKey();

                ReferenceNode.child(key).child("from").setValue(c.getFrom());
                ReferenceNode.child(key).child("to").setValue(EmployeeNameForChatting);
                ReferenceNode.child(key).child("chatData").setValue(c.getChatData());
                ReferenceNode.child(key).child("dateOfsend").setValue(c.getDateOfsend());
                ReferenceNode.child(key).child("chatID").setValue(c.getChatID() + "");

            } catch (Exception ex) {
                Toast.makeText(StationMasterToEmployeeChat.this, "Failed to add", Toast.LENGTH_SHORT).show();
            }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        chatsList.clear();

    }

}
