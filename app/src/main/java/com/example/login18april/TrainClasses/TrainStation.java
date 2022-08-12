package com.example.login18april.TrainClasses;

import java.util.Date;

public class TrainStation {

    private String StationName;
    private String StationCityName;
    private String StationMaster;
    private Date dateOfConstruction;

    public TrainStation() {
    }

    public TrainStation(String stationName, String stationCityName, String stationMaster, Date dateOfConstruction) {
        StationName = stationName;
        StationCityName = stationCityName;
        StationMaster = stationMaster;
        this.dateOfConstruction = dateOfConstruction;
    }

    public TrainStation(String stationName, String stationCityName, String stationMaster) {
        StationName = stationName;
        StationCityName = stationCityName;
        StationMaster = stationMaster;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String stationName) {
        StationName = stationName;
    }

    public String getStationCityName() {
        return StationCityName;
    }

    public void setStationCityName(String stationCityName) {
        StationCityName = stationCityName;
    }

    public String getStationMaster() {
        return StationMaster;
    }

    public void setStationMaster(String stationMaster) {
        StationMaster = stationMaster;
    }

    public Date getDateOfConstruction() {
        return dateOfConstruction;
    }

    public void setDateOfConstruction(Date dateOfConstruction) {
        this.dateOfConstruction = dateOfConstruction;
    }
}
