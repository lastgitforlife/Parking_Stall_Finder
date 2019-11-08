package com.example.parkingstallfinder.Util;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOError;
import java.util.HashMap;

public class Meter {

    private LatLng location;
    private String description;
    /**
     * HashMap that contains times in effect and price.
     * Keys: use KeyCode() method to access.
     * Values: String of price or hours.
     */
    private HashMap<String, String> info;

    /**
     * Creates the Meter with it's location.
     * @param location LatLng location of the Meter.
     */
    public Meter(LatLng location){
        this.location = location;
        info = new HashMap<>();
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

        return output;
    }

    /**
     * Returns the price as a formatted string.
     * @return String formated with $ sign. Ex: $1.50
     */
    public String getPrice(String day, float time){
        return info.get(keyCode("price", day, time));
    }

    /**
     *
     * @param type String "price" or "time". use the one that is relevant to the HashMap your accessing.
     * @param day lowercase String full name of day.
     * @param time float 24 hour clock.
     * @return String keycode needed to access a hashmap.
     */
    private String keyCode(String type, String day, float time){
        String key;
        if(type.equals("price")){
            key = "r_";
        }else if(type.equals("time")){
            key = "t_";
        }else{
            throw new IllegalArgumentException("Not a valid type was entered");
        }
        return key + dayCode(day) + timeCode(time);
    }

    private String dayCode(String day){
        switch (day){
            case "monday":
            case "tuesday":
            case "wednesday":
            case "thursday":
            case "friday":
                return "mf_";
            case "saturday":
                return "sa_";
            case "sunday":
                return "su_";
        }
        throw new IllegalArgumentException("Invalid day");
    }

    private String timeCode(float time){
        String timeKey;
        if(time >= 9 && time <= 18){
            return "9a_6p";
        }else if(time >18 && time <= 22){
            return"6p_10";
        }
        throw new IllegalArgumentException("Invalid time");
    }

    /**
     * Returns a LatLng of the Meter's location.
     * @return LatLng of Meter.
     */
    public LatLng getLocation(){
        return location;
    }

    void setDescription(String desc){
        description = desc;
    }

    void setPrice(String day, float time, String price){
        info.put(keyCode("price", day, time), price);
    }
}
