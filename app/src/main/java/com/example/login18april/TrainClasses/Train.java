package com.example.login18april.TrainClasses;

import java.util.List;

public class Train {

    private String TrainName;
    private String TrainDriverName;
    private List<String> trainStationStops;


    public Train() {
    }

    public Train(String trainName, String trainDriverName, List<String> trainStationStops) {
        TrainName = trainName;
        TrainDriverName = trainDriverName;
        this.trainStationStops = trainStationStops;
    }

    public Train(List<String> trainStationStops) {
        this.trainStationStops = trainStationStops;
    }

    public Train(String trainName, String trainDriverName) {
        TrainName = trainName;
        TrainDriverName = trainDriverName;
    }


    public String getTrainName() {
        return TrainName;
    }

    public void setTrainName(String trainName) {
        TrainName = trainName;
    }

    public String getTrainDriverName() {
        return TrainDriverName;
    }

    public void setTrainDriverName(String trainDriverName) {
        TrainDriverName = trainDriverName;
    }

    public List<String> getTrainStationStops() {
        return trainStationStops;
    }

    public void setTrainStationStops(List<String> trainStationStops) {
        this.trainStationStops = trainStationStops;
    }
}
