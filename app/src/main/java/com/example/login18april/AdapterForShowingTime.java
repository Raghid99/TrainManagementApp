package com.example.login18april;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.login18april.TrainClasses.TrainStop;

import java.text.SimpleDateFormat;
import java.util.List;

public class AdapterForShowingTime  extends RecyclerView.Adapter<AdapterForShowingTime.viewHolder>{
    List<TrainStop> trainStopList;

    public AdapterForShowingTime(List<TrainStop> trainStopList) {
        this.trainStopList = trainStopList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_for_showing_timming,viewGroup,false);
        return new viewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int position) {
        TrainStop trainStop = trainStopList.get(position);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH-mm-ss");
        String arrival = simpleDateFormat.format(trainStop.getTrainArrivalTime());
        String departure = simpleDateFormat.format(trainStop.getTraindepartureTime());

        viewHolder.setData(trainStop.getStopAtStationName(),arrival,departure);

    }

    @Override
    public int getItemCount() {
        return trainStopList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{

        TextView StopName,ArrivalTime,DepartureTime;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            StopName = itemView.findViewById(R.id.TVRecyclerForShowingTimmingStopName);
            ArrivalTime = itemView.findViewById(R.id.TVRecyclerForShowingTimmingArrivalDate);
            DepartureTime = itemView.findViewById(R.id.TVRecyclerForShowingTimmingDepartureDate);

        }

        void setData(String stationName,String arrivalTimming,String departureTimming){

            StopName.setText(stationName);
            ArrivalTime.setText(departureTimming);
            DepartureTime.setText(arrivalTimming);

        }
    }
}
