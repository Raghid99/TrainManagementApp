package com.example.login18april.TrainClasses;

import java.util.Date;

public class TrainStationEmployee {

    private String nameOfEmployee;
    private String Paassword;
    private int salary;
    private String workingatStation;
    private Date dateOfJoin;
    private String Gender;
    private int Age;

    public TrainStationEmployee() {}

    public TrainStationEmployee(String nameOfEmployee, String workingatStation, String gender) {
        this.nameOfEmployee = nameOfEmployee;
        this.workingatStation = workingatStation;
        Gender = gender;
    }

    public TrainStationEmployee(String nameOfEmployee) {
        this.nameOfEmployee = nameOfEmployee;
    }

    public TrainStationEmployee(String nameOfEmployee, String paassword, int salary, String workingatStation, Date dateOfJoin, String gender, int age) {
        this.nameOfEmployee = nameOfEmployee;
        Paassword = paassword;
        this.salary = salary;
        this.workingatStation = workingatStation;
        this.dateOfJoin = dateOfJoin;
        Gender = gender;
        Age = age;
    }

    public TrainStationEmployee(String nameOfEmployee, String paassword) {
        this.nameOfEmployee = nameOfEmployee;
        Paassword = paassword;
    }

    public String getPaassword() {
        return Paassword;
    }

    public void setPaassword(String paassword) {
        this.Paassword = paassword;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getNameOfEmployee() {
        return nameOfEmployee;
    }

    public void setNameOfEmployee(String nameOfEmployee) {
        this.nameOfEmployee = nameOfEmployee;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getWorkingatStation() {
        return workingatStation;
    }

    public void setWorkingatStation(String workingatStation) {
        this.workingatStation = workingatStation;
    }

    public Date getDateOfJoin() {
        return dateOfJoin;
    }

    public void setDateOfJoin(Date dateOfJoin) {
        this.dateOfJoin = dateOfJoin;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }
}
