package com.example.login18april.TrainClasses;

import java.util.Date;


public class TrainStop {
    private String TrainName;
    private String stopAtStationName;
    private Date TraindepartureTime;
    private Date TrainArrivalTime;

    public TrainStop() {
    }

    public TrainStop(String trainName, String stopAtStationName, Date traindepartureTime, Date trainArrivalTime) {
        TrainName = trainName;
        this.stopAtStationName = stopAtStationName;
        TraindepartureTime = traindepartureTime;
        TrainArrivalTime = trainArrivalTime;
    }

    public TrainStop(String trainName, String stopAtStationName) {
        TrainName = trainName;
        this.stopAtStationName = stopAtStationName;
    }

    public TrainStop(Date traindepartureTime, Date trainArrivalTime) {
        TraindepartureTime = traindepartureTime;
        TrainArrivalTime = trainArrivalTime;
    }


    public String getTrainName() {
        return TrainName;
    }

    public void setTrainName(String trainName) {
        TrainName = trainName;
    }

    public String getStopAtStationName() {
        return stopAtStationName;
    }

    public void setStopAtStationName(String stopAtStationName) {
        this.stopAtStationName = stopAtStationName;
    }

    public Date getTraindepartureTime() {
        return TraindepartureTime;
    }

    public void setTraindepartureTime(Date traindepartureTime) {
        TraindepartureTime = traindepartureTime;
    }

    public Date getTrainArrivalTime() {
        return TrainArrivalTime;
    }

    public void setTrainArrivalTime(Date trainArrivalTime) {
        TrainArrivalTime = trainArrivalTime;
    }


}