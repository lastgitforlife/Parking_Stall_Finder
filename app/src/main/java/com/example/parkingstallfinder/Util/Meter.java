package com.example.parkingstallfinder.Util;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOError;
import java.util.HashMap;

public class Meter {
    /**
     * Coordinates, Date, Time, Rate
     */
    //TODO Add New West Meters. Add Max time allowed at meter

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
     * Returns the price as a formatted string.
     * @param type "price" or "type"
     * @return String formated with $ sign. Ex: $1.50
     */
    public String getInfo(String type, String day, float time){
        String key = keyCode(type, day, time);
        return info.get(key);
    }

    /**
     *
     * @param type String "price" or "time". use the one that is relevant to the HashMap your accessing.
     * @param day lowercase String full name of day.
     * @param time float 24 hour clock.
     * @return String keycode needed to access a hashmap.
     */
    public static String keyCode(String type, String day, float time){
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

    private static String dayCode(String day){
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

    private static String timeCode(float time){
        if(time >= 9 && time <= 18){
            return "9a_6p";
        }else if(time >18 && time <= 22){
            return"6p_10";
        }
        else if (time < 0 || time >= 24){
            return "";
        }
        throw new IllegalArgumentException("Invalid time code");
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

    void setTime(String day, float time, String price){
        info.put(keyCode("time", day, time), price);
    }
}
