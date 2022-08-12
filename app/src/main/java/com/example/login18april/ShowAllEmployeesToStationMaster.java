package com.example.login18april;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;

public class ShowAllEmployeesToStationMaster extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterForShowingEmployeeToStationManagement adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_employees_to_station_master);

        //recyclerView = findViewById(R.id.RV)

    }


}
