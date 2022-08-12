package com.example.login18april;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.login18april.TrainClasses.TrainTest;

public class TrainAdding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_adding);

        TrainTest t = new TrainTest();
        //t.doAdd();
    }
}
