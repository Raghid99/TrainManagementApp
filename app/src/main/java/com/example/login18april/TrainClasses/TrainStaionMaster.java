package com.example.login18april.TrainClasses;

import java.util.Date;

public class TrainStaionMaster {

    private String stationMasterName;
    private String Password;
    private String WorkingStationName;
    private String Gender;
    private int contactNumber;
    private Date dateOfJoin;
    private double salary;

    public TrainStaionMaster(String stationMasterName, String Password, String workingStationName, String Gender, int contactNumber, Date dateOfJoin, double Salary) {
        this.stationMasterName = stationMasterName;
        this.Password = Password;
        WorkingStationName = workingStationName;
        this.Gender = Gender;
        this.contactNumber = contactNumber;
        this.dateOfJoin = dateOfJoin;
        this.salary = Salary;
    }

    public TrainStaionMaster(String stationMasterName, String password) {
        this.stationMasterName = stationMasterName;
        Password = password;
    }

    public TrainStaionMaster(String stationMasterName, String password, String gender) {
        this.stationMasterName = stationMasterName;
        Password = password;
        Gender = gender;
    }

    public TrainStaionMaster(String stationMasterName, String password, String gender, Date dateOfJoin) {
        this.stationMasterName = stationMasterName;
        Password = password;
        Gender = gender;
        this.dateOfJoin = dateOfJoin;
    }


    public String getStationMasterName() {
        return stationMasterName;
    }

    public void setStationMasterName(String stationMasterName) {
        this.stationMasterName = stationMasterName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getWorkingStationName() {
        return WorkingStationName;
    }

    public void setWorkingStationName(String workingStationName) {
        WorkingStationName = workingStationName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public int getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Date getDateOfJoin() {
        return dateOfJoin;
    }

    public void setDateOfJoin(Date dateOfJoin) {
        this.dateOfJoin = dateOfJoin;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
