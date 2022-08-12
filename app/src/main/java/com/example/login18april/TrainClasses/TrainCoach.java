package com.example.login18april.TrainClasses;

import java.util.List;

public class TrainCoach {

    private int coachNumber;
    private String CoachName;
    private List<String> trainSeats;
    private String TrainName;
    private String CoachCondition;

    public TrainCoach() {
    }

    public TrainCoach(int coachNumber, String coachName, List<String> trainSeats, String trainName, String coachCondition) {
        this.coachNumber = coachNumber;
        CoachName = coachName;
        this.trainSeats = trainSeats;
        TrainName = trainName;
        CoachCondition = coachCondition;
    }

    public TrainCoach(int coachNumber, String coachName, String trainName, String coachCondition) {
        this.coachNumber = coachNumber;
        CoachName = coachName;
        TrainName = trainName;
        CoachCondition = coachCondition;
    }

    public TrainCoach(String coachName) {
        CoachName = coachName;
    }

    public TrainCoach(int coachNumber, String coachName) {
        this.coachNumber = coachNumber;
        CoachName = coachName;
    }

    public TrainCoach(int coachNumber, String coachName, String trainName) {
        this.coachNumber = coachNumber;
        CoachName = coachName;
        TrainName = trainName;
    }


    public int getCoachNumber() {
        return coachNumber;
    }

    public void setCoachNumber(int coachNumber) {
        this.coachNumber = coachNumber;
    }

    public String getCoachName() {
        return CoachName;
    }

    public void setCoachName(String coachName) {
        CoachName = coachName;
    }

    public List<String> getTrainSeats() {
        return trainSeats;
    }

    public void setTrainSeats(List<String> trainSeats) {
        this.trainSeats = trainSeats;
    }

    public String getTrainName() {
        return TrainName;
    }

    public void setTrainName(String trainName) {
        TrainName = trainName;
    }

    public String getCoachCondition() {
        return CoachCondition;
    }

    public void setCoachCondition(String coachCondition) {
        CoachCondition = coachCondition;
    }
}
