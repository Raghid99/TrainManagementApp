package com.example.login18april;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NoBookings extends AppCompatActivity {
    TextView BookingShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_bookings);
        BookingShow = findViewById(R.id.TVBookingStatusAdding123);
        CheckBookingStatus();
    }


    public void CheckBookingStatus() {
        FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ReferenceNode = OnlineDatabase.getReference("Bookings");

        ReferenceNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();

                for (DataSnapshot child : iterable) {

                    Booking booking = child.getValue(Booking.class);
                    String s = "Name: " + booking.getBookingPersonName() + "\nBooking ID: " + booking.getBookingID() + "\nFrom: " + booking.getStartingStation()
                            + "\nTo: " + booking.getEndingStation() + "\nPrice: " + booking.getBookingPrice() + "\nTrain: " + booking.getTrainName() + "\nCoach: " + booking.getTrainCoach() + "\nSeat: " + booking.getSeatNumber()+"\n";

                    BookingShow.setText(s);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
