package com.example.login18april;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;

public class AdminToClientChat extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterForChatScreen adapterForChatScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_to_client_chat);



    }
}
