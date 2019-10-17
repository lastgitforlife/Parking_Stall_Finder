package com.example.parkingstallfinder.Util;

import com.google.android.gms.maps.model.LatLng;

public class Meter {

    private LatLng location;
    private String description;
    private String[] times = new String[7];
    private String price;

    /**
     * Creates the Meter with it's location.
     * @param location LatLng location of the Meter.
     */
    public Meter(LatLng location){
        this.location = location;
    }

    /**
     * Returns the description of the meter.
     * @return String of the description.
     */
    public String getDescription(){
        return description;
    }

    /**
     * Returns the open times of the meter.
     * @return String[] of size 7 that hold the times open on the days.
     */
    public String[] getTime(){
        return times;
    }

    /**
     * Returns the open time for the meter on the day indicated.
     * Index 0 = Sunday, Index 1 = Monday , Index 2 = Tuesday ... Index 6 = Saturday.
     * @param day int representation of the weekdays.
     * @return String that is a readable version of that days open times. Ex: Sunday 6am - 1pm
     */
    public String getTime(int day){
        return times[day];
    }

    /**
     * Returns the price as a formatted string.
     * @return String formated with $ sign. Ex: $1.50
     */
    public String getPrice(){
        return price;
    }

    /**
     * Returns a LatLng of the Meter's location.
     * @return LatLng of Meter.
     */
    public LatLng getLocation(){
        return location;
    }
}
