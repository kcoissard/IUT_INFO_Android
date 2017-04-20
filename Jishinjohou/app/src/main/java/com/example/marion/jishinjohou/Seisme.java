package com.example.marion.jishinjohou;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Marion on 03/06/16.
 */
public class Seisme implements Serializable {


    private String title;
    private String place;
    private String code;
    private long timeEpoch;
    private String date;
    private String detail;
    private String coordinates;
    private float latitude;
    private float longitude;


    public Seisme(String title, String place, String code, long timeEpoch, String detail, String coordinates) {
        this.title = title;
        this.place = place;
        this.code = code;
        this.timeEpoch = timeEpoch;
        this.detail = detail;
        this.coordinates = coordinates;
        date = transformDate();
        setLatitudeLongitude(coordinates);
    }

    public String transformDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.FRANCE);
        return simpleDateFormat.format(new Date(timeEpoch));
    }


    public void setLatitudeLongitude(String coordinates) {
        String tempo = coordinates.substring(1, coordinates.length() - 1);
        String[] decoupe = tempo.split(",");
        longitude = Float.valueOf(decoupe[0]);
        latitude = Float.valueOf(decoupe[1]);
    }


    public float getLongitude() {
        return longitude;
    }

    public String getTitle() {
        return title;
    }

    public String getPlace() {
        return place;
    }

    public String getCode() {
        return code;
    }

    public String getDetail() {
        return detail;
    }

    public String getDate() {
        return date;
    }

    public float getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        return title;
    }
}
