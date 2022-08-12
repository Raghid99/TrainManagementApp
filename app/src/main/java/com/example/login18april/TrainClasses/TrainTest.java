package com.example.login18april.TrainClasses;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class TrainTest {

//    public void doAdd() {
//
//
//        TrainSeat[] trainSeats1 = new TrainSeat[10];
//        for (int i = 0; i < trainSeats1.length; i++) {
//            trainSeats1[i] = new TrainSeat("Seat A" + (i + 1), false);
//        }
//
//        TrainSeat[] trainSeats2 = new TrainSeat[10];
//        for (int i = 0; i < trainSeats1.length; i++) {
//            trainSeats2[i] = new TrainSeat("Seat B" + (i + 1), false);
//        }
//
//        TrainSeat[] trainSeats3 = new TrainSeat[10];
//        for (int i = 0; i < trainSeats1.length; i++) {
//            trainSeats3[i] = new TrainSeat("Seat C" + (i + 1), false);
//        }
//
//
//        TrainCoach[] trainCoaches = new TrainCoach[3];
//
//        trainCoaches[0] = new TrainCoach(1, trainSeats1);
//        trainCoaches[1] = new TrainCoach(2, trainSeats2);
//        trainCoaches[2] = new TrainCoach(3, trainSeats3);
//
//        TrainStation[] trainStations = new TrainStation[3];
//        trainStations[0] = new TrainStation("Fujairah old Station", "Äli Iqbal", "Fujairah");
//        trainStations[1] = new TrainStation("Dubai Station", "Arif Saif", "Dubai");
//        trainStations[2] = new TrainStation("Main Abu Dabhi Station", "Sohaib dawood", "Abu Dabhi");
//
//
//        TrainStop[] trainStops = new TrainStop[3];
//        trainStops[0] = new TrainStop(trainStations[0], new Date(2012, 1, 1, 10, 10), new Date(2012, 1, 1, 10, 30));
//        trainStops[1] = new TrainStop(trainStations[1], new Date(2012, 1, 1, 15, 30), new Date(2012, 1, 1, 15, 50));
//        trainStops[2] = new TrainStop(trainStations[2], new Date(2012, 1, 1, 2, 10), new Date(2012, 1, 1, 2, 20));
//
//
//        Train t  = new Train("Jaffar EXPRESS","Saad Ali",trainCoaches,trainStops);
//
//        ////////////////////////////////////////////////////////////////
//
//        try {
//            FirebaseDatabase OnlineDatabase = FirebaseDatabase.getInstance();
//            DatabaseReference ReferenceNode = OnlineDatabase.getReference("Trains");
//
//            String Trainkey = ReferenceNode.push().getKey();
//
//            ReferenceNode.child(Trainkey).child("DriverName").setValue(t.getTrainDriverName());
//            ReferenceNode.child(Trainkey).child("TrainName").setValue(t.getTrainName());
//
//            for (int i = 0; i <trainStops.length;i++){
//                String StopsKey = ReferenceNode.child(Trainkey).child("Stops").push().getKey();
//
//                ReferenceNode.child(Trainkey).child("Stops").child(StopsKey).child("ArrivalTime").setValue(t.getTrainStops()[i].getTrainArrivalTime().getTime());
//                ReferenceNode.child(Trainkey).child("Stops").child(StopsKey).child("DepartureTime").setValue(t.getTrainStops()[i].getTraindepartureTime().getTime());
//                ReferenceNode.child(Trainkey).child("Stops").child(StopsKey).child("StationName").setValue(t.getTrainStops()[i].getStopTrainStation().getStationCityName());
//            }
//
//
//
//            for (int i = 0; i < t.getNumberOfCoaches(); i++) {
//                String CoachKey = ReferenceNode.child(Trainkey).child("Coaches").push().getKey();
//                ReferenceNode.child(Trainkey).child("Coaches").child(CoachKey).child("CoachNumber").setValue(t.getTrainCoaches()[i].getCoachNumber());
//                ReferenceNode.child(Trainkey).child("Coaches").child(CoachKey).child("NumberOfSeats").setValue(t.getTrainCoaches()[i].getNumberOfSeats());
//
//                for (int j = 0; j < t.getTrainCoaches()[i].getNumberOfSeats(); j++) {
//
//                    String SeatKey = ReferenceNode.child(Trainkey).child("Coaches").child(CoachKey).child("Seats").push().getKey();
//                    ReferenceNode.child(Trainkey).child("Coaches").child(CoachKey).child("Seats").child(SeatKey).child("SeatNumber").setValue(t.getTrainCoaches()[i].getTrainSeats()[j].getSeatnumber());
//                    ReferenceNode.child(Trainkey).child("Coaches").child(CoachKey).child("Seats").child(SeatKey).child("IsBooked").setValue(false);
//
//                }
//
//
//            }
//        }catch (Exception e){
//
//        }
//
//
//
//
//
//    }
//

}
