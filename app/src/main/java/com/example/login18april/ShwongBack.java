package com.example.login18april;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShwongBack extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shwong_back);
        Button b = findViewById(R.id.buttontesting);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
                DatabaseReference ReferenceNode = OnlineDatabase.getReference("AdminAccountTesting");
                try {
                    ReferenceNode.child("A").setValue("Yes Working");
                }catch (DatabaseException e){
                    Toast.makeText(ShwongBack.this, "Writing error", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
