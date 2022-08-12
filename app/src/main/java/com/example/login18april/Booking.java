package com.example.login18april;

import android.widget.Toast;

import com.example.login18april.TrainClasses.TrainStation;

import java.util.Date;
import java.util.Random;

public class Booking {

    private int bookingID;
    private String trainName;
    private Date dateOfBooking;
    private Date dateOfTraveling;
    private String startingStation;
    private String endingStation;
    private double bookingPrice;
    private String bookingPersonName;
    private int TrainCoach;
    private int SeatNumber;

    public Booking() {
    }

    public Booking( String trainName, Date dateOfBooking, Date dateOfTraveling, String startingStation, String endingStation, double bookingPrice, String bookingPersonName, int trainCoach,int seatNumber) {
        this.bookingID = (new Random()).nextInt(10000)+10000;
        this.trainName = trainName;
        this.dateOfBooking = dateOfBooking;
        this.dateOfTraveling = dateOfTraveling;
        this.startingStation = startingStation;
        this.endingStation = endingStation;
        this.bookingPrice = bookingPrice;
        this.bookingPersonName = bookingPersonName;
        TrainCoach = trainCoach;
        this.SeatNumber =seatNumber;
    }
    //Loading
    public Booking(int bookingID, String trainName, Date dateOfBooking, Date dateOfTraveling, String startingStation, String endingStation, double bookingPrice, String bookingPersonName, int trainCoach, int seatNumber) {
        this.bookingID = bookingID;
        this.trainName = trainName;
        this.dateOfBooking = dateOfBooking;
        this.dateOfTraveling = dateOfTraveling;
        this.startingStation = startingStation;
        this.endingStation = endingStation;
        this.bookingPrice = bookingPrice;
        this.bookingPersonName = bookingPersonName;
        TrainCoach = trainCoach;
        this.SeatNumber = seatNumber;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public Date getDateOfBooking() {
        return dateOfBooking;
    }

    public void setDateOfBooking(Date dateOfBooking) {
        this.dateOfBooking = dateOfBooking;
    }

    public Date getDateOfTraveling() {
        return dateOfTraveling;
    }

    public void setDateOfTraveling(Date dateOfTraveling) {
        this.dateOfTraveling = dateOfTraveling;
    }

    public String getStartingStation() {
        return startingStation;
    }

    public void setStartingStation(String startingStation) {
        this.startingStation = startingStation;
    }

    public String getEndingStation() {
        return endingStation;
    }

    public void setEndingStation(String endingStation) {
        this.endingStation = endingStation;
    }

    public double getBookingPrice() {
        return bookingPrice;
    }

    public void setBookingPrice(double bookingPrice) {
        this.bookingPrice = bookingPrice;
    }

    public String getBookingPersonName() {
        return bookingPersonName;
    }

    public void setBookingPersonName(String bookingPersonName) {
        this.bookingPersonName = bookingPersonName;
    }

    public int getTrainCoach() {
        return TrainCoach;
    }

    public void setTrainCoach(int trainCoach) {
        TrainCoach = trainCoach;
    }

    public int getSeatNumber() {
        return SeatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        SeatNumber = seatNumber;
    }
}
