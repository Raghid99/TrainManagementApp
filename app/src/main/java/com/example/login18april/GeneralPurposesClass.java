package com.example.login18april;


import android.app.Application;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.appcompat.app.AppCompatActivity;

public class GeneralPurposesClass extends Application {


    public boolean hasInternetConnection() {

        boolean hasWifi = false;
        boolean hasMobileData = false;


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo[] allNetworksInfo = connectivityManager.getAllNetworkInfo();


        for (NetworkInfo info : allNetworksInfo) {
            if (info.getTypeName().equalsIgnoreCase("WIFI")) {
                if (info.isConnected()) {
                    hasWifi = true;
                }

            } else if (info.getTypeName().equalsIgnoreCase("MOBILE")){
                if (info.isConnected()) {
                    hasMobileData = true;
                }


            }

        }

        return (hasMobileData || hasWifi);


    }
}
