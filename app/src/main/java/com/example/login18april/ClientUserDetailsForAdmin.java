package com.example.login18april;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ClientUserDetailsForAdmin extends AppCompatActivity {
    private String Username,BookingStatus,Gender,Age;

    private boolean hasBooking;

    private ImageView UserImage;
    private TextView UsernameShowing, GenderShowing,BookingDetailsShowing;
    private Button ButtonToStartChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_user_details_for_admin);


        Intent it = getIntent();
        this.Username = it.getStringExtra("username");


        BookingDetailsShowing = findViewById(R.id.TVClientUserDetailsForAdminBookingDetailsShowing);
        UserImage = findViewById(R.id.IVClientUserDetailsForAdminUserImageShowing);
        UsernameShowing = findViewById(R.id.TVClientUserDetailsForAdminUsernameShowing);
        GenderShowing = findViewById(R.id.TVEmployeeDetailsForStationMasterGenderShowing);
        ButtonToStartChat = findViewById(R.id.BTNClientUserDetailsForAdminStartChat);
        UserImage.setImageResource(R.drawable.male1);

        UsernameShowing.setText(Username);


        LoadClientUserData();
        ButtonToStartChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ClientUserDetailsForAdmin.this,ChatScreen.class);
                it.putExtra("username",Username);
                startActivity(it);
            }
        });


//        if (Gender.equals("Male".toString())){
//            GenderShowing.setText("M");
//
//        }else{
//            GenderShowing.setText("F");
//        }


//        if (hasBooking){
//            BookingDetailsShowing.setText(BookingStatus);
//        }else{
//            BookingDetailsShowing.setText("No Booking yet");
//        }





    }



    public void LoadClientUserData(){

        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Users");


        ReferenceNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> iterable  = dataSnapshot.getChildren();

                for (DataSnapshot child: iterable ) {

                    Map<String, Object> perUserData = (Map<String, Object>) child.getValue();

                    String username_temp = (String) perUserData.get("Username");
                    if (username_temp.equals(Username)) {
                        String age_temp = perUserData.get("Age").toString();
                        String gender_temp = perUserData.get("Gender").toString();
                        String bookingStatus_temp = (String) perUserData.get("BookingStatus").toString();
                        boolean hasBooking_temp = (boolean) perUserData.get("HasBooking");

                        try{


                        if (gender_temp.equals("Male")){
                            GenderShowing.setText("M");
                        }else{
                            GenderShowing.setText("F");
                        }}catch (Exception ex){
                            GenderShowing.setText("E");

                        }

                        if (hasBooking_temp){
                            BookingDetailsShowing.setText(bookingStatus_temp);
                        }else{
                            BookingDetailsShowing.setText("No booking yet");
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


    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getBookingStatus() {
        return BookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        BookingStatus = bookingStatus;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public boolean isHasBooking(boolean hasBooking) {
        return this.hasBooking;
    }

    public void setHasBooking(boolean hasBooking) {
        this.hasBooking = hasBooking;
    }
}
