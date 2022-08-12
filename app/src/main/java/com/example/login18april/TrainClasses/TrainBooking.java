package com.example.login18april.TrainClasses;

import java.util.Date;
import java.util.Random;

public class TrainBooking {

    private String TrainName;
    private String BookingUserName;
    private Date dateOfBooking;
    private Date dateOfExpiryOfBooking;
    private int bookingID;
    private String BookingFrom;
    private String BookingTo;


    public TrainBooking(String trainName, String bookingUserName, Date dateOfBooking, Date dateOfExpiryOfBooking, int bookingID, String bookingFrom, String bookingTo) {
        TrainName = trainName;
        BookingUserName = bookingUserName;
        this.dateOfBooking = dateOfBooking;
        this.dateOfExpiryOfBooking = dateOfExpiryOfBooking;
        this.bookingID = bookingID;
        BookingFrom = bookingFrom;
        BookingTo = bookingTo;
    }

    public TrainBooking(String trainName, String bookingUserName, String bookingFrom, String bookingTo) {
        TrainName = trainName;
        BookingUserName = bookingUserName;
        BookingFrom = bookingFrom;
        BookingTo = bookingTo;
    }
}
