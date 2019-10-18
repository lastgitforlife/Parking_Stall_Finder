package com.example.parkingstallfinder.Util;

import com.google.android.gms.maps.model.LatLng;

public class Meter {

    private LatLng location;
    private String description;
    private String[][] times = new String[7][];
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
     * Returns the open time for the meter on the day indicated.
     * Index 0 = Sunday, Index 1 = Monday , Index 2 = Tuesday ... Index 6 = Saturday.
     * @param day int representation of the weekdays.
     * @return String that is a readable version of that days open times. Ex: Sunday 6am - 1pm
     */
    public String getTime(int day){
        String output = "";
        for(int i = 0; i < times[day].length; i++){
            output += times[day][i];
        }
        return output;
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

    public void setTime(int day, String[] times){
        this.times[day] = times;
    }

    public void setDescription(String desc){
        description = desc;
    }

    public void setPrice(String price){
        this.price = price;
    }
}
