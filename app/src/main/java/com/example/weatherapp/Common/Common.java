package com.example.weatherapp.Common;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    public static final String API_ID = "2f0f36d117811ff4bd947ecf9c4f5813";
    public static Location currant_location = null;

   public static String convertUnixToDate(long dt){
       Date date = new Date(dt*1000L);
       SimpleDateFormat sdt = new SimpleDateFormat("HH:mm dd EEE MM yyyy");
       String formatted = sdt.format(date);
       return formatted;
   }

    public static String convertUnixToHour(long dt){
        Date date = new Date(dt*1000L);
        SimpleDateFormat sdt = new SimpleDateFormat("HH:mm");
        String formatted = sdt.format(date);
        return formatted;
    }
}
