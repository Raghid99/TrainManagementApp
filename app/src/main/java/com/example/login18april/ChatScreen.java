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

public class ChatScreen extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageButton SendChat;
    EditText SendMessage;
    TextView Username;
    static String UsernameName;
    AdapterForChatScreen adapterForChatScreen;
    FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
    static List<Chat> chatsList = new ArrayList<>();

//ETEmployeeToStationMasterChatSendMessage
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        recyclerView = findViewById(R.id.RVCEmployeeToStationMasterChat);
        SendChat = findViewById(R.id.BTNEmployeeToStationMasterChatsendText1);
        Username = findViewById(R.id.TVEmployeeToStationMasterChatUsernameShowing);


        SendMessage = findViewById(R.id.ETEmployeeToStationMasterChatSendMessage);

        Intent it = getIntent();
        this.UsernameName = it.getStringExtra("username");
        Username.setText("With "+UsernameName);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.getLayoutManager().scrollToPosition(chatsList.size()-1);


        adapterForChatScreen = new AdapterForChatScreen(chatsList);
        recyclerView.setAdapter(adapterForChatScreen);
        adapterForChatScreen.notifyDataSetChanged();
        recyclerView.scrollToPosition(adapterForChatScreen.getItemCount()-1);
        recyclerView.setVerticalScrollBarEnabled(true);
        ImportOnlineChat();
        adapterForChatScreen.notifyDataSetChanged();



        SendChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SendMessage = findViewById(R.id.ETEmployeeToStationMasterChatSendMessage);
                String chatData = SendMessage.getText().toString();
                if (chatData.length() == 0) {

                } else {

                    try {

                        Chat c = new Chat("AdminAccount",UsernameName, new Date(), chatData);
                        chatsList.add(c);

                        SendMessage.setText("");

                        String referenceToSpecificChat = "AdminToClientChat" + "/" + "AdminAccountTo" + UsernameName;

                        OnlineDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference ReferenceNode = OnlineDatabase.getReference(referenceToSpecificChat);

                        String key = ReferenceNode.child(referenceToSpecificChat).push().getKey();

                        ReferenceNode.child(key).child("from").setValue(c.getFrom());
                        ReferenceNode.child(key).child("to").setValue("AdminAccount");
                        ReferenceNode.child(key).child("chatData").setValue(c.getChatData());
                        ReferenceNode.child(key).child("dateOfsend").setValue(c.getDateOfsend());
                        ReferenceNode.child(key).child("chatID").setValue(c.getChatID() + "");

                    } catch (Exception ex) {
                        Toast.makeText(ChatScreen.this, "Adding to database error", Toast.LENGTH_SHORT).show();
                    }

                }





                adapterForChatScreen.notifyDataSetChanged();
                recyclerView.scrollToPosition(adapterForChatScreen.getItemCount()-1);
                AddChattoDataBase();



            }
        });

    }


    public void AddChattoDataBase(){
        SendMessage = findViewById(R.id.ETEmployeeToStationMasterChatSendMessage);
        String chatData = SendMessage.getText().toString();
        if (chatData.length() == 0) {

        } else {

            try {
                Chat c = new Chat("AdminAccount",UsernameName,new Date(),chatData);
                chatsList.add(c);
                SendMessage.setText("");
                Intent it = getIntent();
                String referenceToSpecificChat = "AdminAccount" + "To" + UsernameName;


                FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
                String initialReference = "AdminToClientChat" + "/" + "AdminAccountTo" + UsernameName;
                DatabaseReference ReferenceNode = OnlineDatabase.getReference(initialReference);

                String key = ReferenceNode.child(referenceToSpecificChat).push().getKey();

                ReferenceNode.child(key).child("from").setValue(c.getFrom());
                ReferenceNode.child(key).child("to").setValue(UsernameName);
                ReferenceNode.child(key).child("chatData").setValue(c.getChatData());
                ReferenceNode.child(key).child("dateOfsend").setValue(c.getDateOfsend());
                ReferenceNode.child(key).child("chatID").setValue(c.getChatID() + "");

            }catch (Exception ex){
                Toast.makeText(ChatScreen.this, "Failed to add", Toast.LENGTH_SHORT).show();
            }

        }

    }

    public void ImportOnlineChat(){

        OnlineDatabase = FirebaseDatabase.getInstance();
        String initialReference = "AdminToClientChat" + "/" + "AdminAccountTo" + UsernameName;
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
                        adapterForChatScreen.notifyDataSetChanged();

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        chatsList.clear();

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
}












//        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
//        String initialReference = "AdminToClientChat"+"/"+"AdminAccountTo"+UsernameName;
//        DatabaseReference ReferenceNode = OnlineDatabase.getReference(initialReference);
//
//        ReferenceNode.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
//
//                for (DataSnapshot child: iterable){
//                    Map<String, Object> perChatData = (Map<String, Object>) child.getValue();
//                    try {
//
//                        String from = (String) perChatData.get("from");
//                        String to = (String) perChatData.get("to");
//                        String chatData = (String) perChatData.get("chatData");
//                        String chatID_temp = (String) perChatData.get("chatID");
//                        int chatID = Integer.parseInt(chatID_temp);
//
//
//                        Chat c = new Chat(from, to, chatData, chatID);
//                        chatsList.add(c);
//                        adapterForChatScreen.notifyDataSetChanged();
//                    }catch (Exception e){
//                        Toast.makeText(ChatScreen.this, "Yes caught!!", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(ChatScreen.this, "Internet Connection Error", Toast.LENGTH_SHORT).show();
//
//            }
//        });

