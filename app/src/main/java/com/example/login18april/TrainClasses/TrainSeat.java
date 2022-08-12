package com.example.login18april.TrainClasses;

import java.util.Date;

public class TrainSeat {

    private String CoachName;
    private String SeatFullName;
    private boolean isBooked;
    private String bookingPersonName;

    public TrainSeat(String coachName, String seatFullName, boolean isBooked, String bookingPersonName) {
        CoachName = coachName;
        SeatFullName = seatFullName;
        this.isBooked = isBooked;
        this.bookingPersonName = bookingPersonName;
    }

    public TrainSeat(boolean isBooked) {
        this.isBooked = isBooked;
    }

    public TrainSeat(String seatFullName) {
        SeatFullName = seatFullName;
    }

    public TrainSeat(String coachName, String seatFullName, boolean isBooked) {
        CoachName = coachName;
        SeatFullName = seatFullName;
        this.isBooked = isBooked;
    }

    public String getCoachName() {
        return CoachName;
    }

    public void setCoachName(String coachName) {
        CoachName = coachName;
    }

    public String getSeatFullName() {
        return SeatFullName;
    }

    public void setSeatFullName(String seatFullName) {
        SeatFullName = seatFullName;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public String getBookingPersonName() {
        return bookingPersonName;
    }

    public void setBookingPersonName(String bookingPersonName) {
        this.bookingPersonName = bookingPersonName;
    }



}

