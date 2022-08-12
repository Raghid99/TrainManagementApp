package com.example.login18april;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
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

public class UserEndChat extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageButton SendChat;
    EditText SendMessage;
    TextView Username;
    AdapterForUserEndChatScrenn adapterForUserEndChatScrenn;
    FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
    static List<Chat> chatsList = new ArrayList<>();
    SignUPActivity signUPActivity = new SignUPActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_end_chat);
        recyclerView = findViewById(R.id.RVStationMasterToEmployeeChatScreen);
        SendChat = findViewById(R.id.BTNEmployeeToStationMasterChatsendText);
        Username = findViewById(R.id.TVStationMasterToEmployeeUsernameShowing);


        SendMessage = findViewById(R.id.ETStationMasterToEmployeeSendMessage);

        Intent it = getIntent();
        Username.setText(getUsernameFromLocaFile());


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.getLayoutManager().scrollToPosition(chatsList.size()-1);
        ImportOnlineChat();


        adapterForUserEndChatScrenn = new AdapterForUserEndChatScrenn(chatsList);
        recyclerView.setAdapter(adapterForUserEndChatScrenn);
        adapterForUserEndChatScrenn.notifyDataSetChanged();
        recyclerView.scrollToPosition(adapterForUserEndChatScrenn.getItemCount() - 1);
        recyclerView.setVerticalScrollBarEnabled(true);
        //ImportOnlineChat();
        adapterForUserEndChatScrenn.notifyDataSetChanged();


        SendChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterForUserEndChatScrenn.notifyDataSetChanged();
                recyclerView.scrollToPosition(adapterForUserEndChatScrenn.getItemCount() - 1);
                AddChattoDataBase();


            }
        });

    }


    public void AddChattoDataBase() {
        SendMessage = findViewById(R.id.ETStationMasterToEmployeeSendMessage);
        String chatData = SendMessage.getText().toString();
        if (chatData.length() == 0) {

        } else {

            try {
                Chat c = new Chat(getUsernameFromLocaFile(), "AdminAccount", new Date(), chatData);
                chatsList.add(c);

                SendMessage.setText("");
//                Intent it = getIntent();
//                String username = it.getStringExtra("username");

                String referenceToSpecificChat = "AdminToClientChat" + "/" + "AdminAccountTo" + getUsernameFromLocaFile();

                OnlineDatabase = FirebaseDatabase.getInstance();
                DatabaseReference ReferenceNode = OnlineDatabase.getReference(referenceToSpecificChat);

                String key = ReferenceNode.child(referenceToSpecificChat).push().getKey();

                ReferenceNode.child(key).child("from").setValue(c.getFrom());
                ReferenceNode.child(key).child("to").setValue("AdminAccount");
                ReferenceNode.child(key).child("chatData").setValue(c.getChatData());
                ReferenceNode.child(key).child("dateOfsend").setValue(c.getDateOfsend());
                ReferenceNode.child(key).child("chatID").setValue(c.getChatID() + "");

            } catch (Exception ex) {
                Toast.makeText(UserEndChat.this, "Adding to database error", Toast.LENGTH_SHORT).show();
            }

        }

    }

    public void ImportOnlineChat() {

        OnlineDatabase = FirebaseDatabase.getInstance();
        String initialReference = "AdminToClientChat" + "/" + "AdminAccountTo" + getUsernameFromLocaFile();
        final DatabaseReference ReferenceNode = OnlineDatabase.getReference(initialReference);

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

                    }catch (Exception ex){
                        chatID = 11;

                    }

                    if (chatID == 11){
                        continue;
                    }






                    Chat c = new Chat(from,to,chatData,chatID);
                    boolean chatAlreadyExists = false;

                    for (Chat t : chatsList){
                        if (c.getChatID() == t.getChatID() ){
                            chatAlreadyExists = true;
                            break;
                        }

                    }

                    if (!chatAlreadyExists){
                        chatsList.add(c);
                        adapterForUserEndChatScrenn.notifyDataSetChanged();

                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void validateChats() {
        for (int i = 0; i < chatsList.size(); i++) {
            for (int j = 0; j < chatsList.size() - 1; j++) {
                if (chatsList.get(j).getChatID() > chatsList.get(j + 1).getChatID()) { //if the current number is less than the one next to it
                    Chat temp = chatsList.get(j); //save the current number
                    chatsList.set(j, chatsList.get(j + 1)); //put the one next to it in its spot
                    chatsList.set(j + 1, temp); //put the current number in the next spot
                }
            }


        }
        for (Chat c : chatsList) {
            Log.d("CHAT", c.getChatID() + "");
        }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        chatsList.clear();

    }
}
