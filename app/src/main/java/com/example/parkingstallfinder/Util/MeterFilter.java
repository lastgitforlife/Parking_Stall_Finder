package com.example.parkingstallfinder.Util;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Class used to get and filter meter data.
 */
public class MeterFilter {

    /** Index 0 is top left of search area
     * Index 1 is bottom Right of search area.
     **/
    private LatLng[] searchArea = new LatLng[2];
    private ArrayList<Meter> currentScope; // Meters currently in search area.
    private String[] datasets; // JSON Datasets.

    /**
     * Constructor for Meter Filter.
     * @param inputData Strings that have JSON data.
     */
    public MeterFilter(String[] inputData){
        datasets = inputData;
    }

    /**
     * Changes the current search area for meters.
     * @param topLeft LatLng Coordinate object.
     * @param btmRight LatLng Coordinate object.
     */
    public void Search(LatLng topLeft, LatLng btmRight){
        searchArea[0] = topLeft;
        searchArea[1] = btmRight;
    }

    /**
     * Returns the Search area. Index 0 is TopLeft, Index 1 is BottomRight.
     * @return LatLng[] of size 2.
     */
    public LatLng[] getSearchArea(){
        return searchArea;
    }

    /**
     * Returns the meter at a specific LatLng. If no meter found will throw NoMeterException.
     * @param location LatLng Object.
     * @throws NoMeterException
     * @return Meter Object that is at that location.
     */
    public Meter getMeter(LatLng location){
        return null;
    }

    /**
     * Returns all meters in the search area. If no meters are found
     * then will throw NoMeterException.
     * @throws NoMeterException
     * @return
     */
    public Meter[] getMeterList(){
        return null;
    }
}
