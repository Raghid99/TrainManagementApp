package com.example.login18april;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.login18april.TrainClasses.TrainStationEmployee;

import java.util.List;

public class AdapterForShowingEmployeeToStationManagement extends RecyclerView.Adapter<AdapterForShowingEmployeeToStationManagement.viewHolder>{
    List<TrainStationEmployee> trainStationEmployeeList;

    public AdapterForShowingEmployeeToStationManagement(List<TrainStationEmployee> trainStationEmployeeList) {
        this.trainStationEmployeeList = trainStationEmployeeList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_for_showing_employees_to_stationmaster,viewGroup,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int position) {
        final String name = trainStationEmployeeList.get(position).getNameOfEmployee();
        viewHolder.setData(name);

        viewHolder.EmployeeTextShowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(v.getContext(),EmloyeeDetailsForStationMaster.class);
                it.putExtra("nameOfEmployee",name);
                v.getContext().startActivity(it);
            }
        });



    }

    @Override
    public int getItemCount() {
        return trainStationEmployeeList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        ImageView EmployeeImageShowing;
        TextView EmployeeTextShowing;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            EmployeeImageShowing = itemView.findViewById(R.id.IVStationManagementShowingImage);
            EmployeeTextShowing = itemView.findViewById(R.id.TVStationManagementShowingNameRecycler);

        }

        void setData(String name){
            EmployeeTextShowing.setText(name);
            EmployeeImageShowing.setImageResource(R.drawable.male1);

        }
    }
}
