package com.example.parkingstallfinder.Util;


import android.app.Activity;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import com.example.parkingstallfinder.Util.DataObserver.HttpHandler;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Class used to get and filter meter data.
 */
public class MeterFilter{

    private LatLng[] searchArea = new LatLng[2]; // [0] = TopLeft, [1] = BtmRight.
    private ArrayList<Meter> currentScope; // Meters currently in search area.
    //TEMP TILL SEARCH IS FIXED
    private ArrayList<Meter> allMeters = new ArrayList<>(); // ALL METERS.
    private Activity activity; // Used to path to New West JSON
    private boolean dataLoaded = false;

    /**
     * Constructor for Meter Filter.
     */
    public MeterFilter(Activity activity){
        currentScope = new ArrayList<>();
        this.activity = activity;
        //TODO: FIX THIS
        GetInformation getInfo = new GetInformation();
        getInfo.execute();
    }

    /**
     * Changes the current search area for meters.
     * @param tL Top left LatLng Coordinate object.
     * @param bR Bottom Right LatLng Coordinate object.
     */
    public void search(LatLng tL, LatLng bR){
        searchArea[0] = tL;
        searchArea[1] = bR;
        if(!currentScope.isEmpty())
            currentScope.clear();
        //TODO: FIX THIS, WORKS IN DEBUG BUT NOT PRODUCTION
        //TODO: Runs with allMeters
        for(int i = 0; i < allMeters.size(); i++){
            Meter meter = allMeters.get(i);
            LatLng location = meter.getLocation();
            // Breakpoint here to get it to work -\('_')/-
            if(location.longitude > tL.longitude && location.latitude < tL.latitude &&
                    location.longitude < bR.longitude && location.latitude > bR.latitude){
                currentScope.add(meter);
            }
        }
    }

    /**
     * Returns the Search area. Index 0 is TopLeft, Index 1 is BottomRight.
     * @return LatLng[] of size 2.
     */
    public LatLng[] getSearchArea(){
        return searchArea;
    }

    //TODO
    /**
     * Returns the meter at a specific LatLng inside the search area.
     * If no meter found will throw NoMeterException.
     * @param location LatLng Object.
     * @throws NoMeterException
     * @return Meter Object that is at that location.
     */
    public Meter getMeter(LatLng location){
        return null;
    }

    //TODO
    /**
     * Returns all meters in the search area. If no meters are found
     * then will throw NoMeterException.
     * @throws NoMeterException
     * @return Meter ArrayList.
     */
    public ArrayList<Meter> getMeterList(){
        //if(currentScope.isEmpty())
            //throw new NoMeterException("");
        return currentScope;
    }

    public boolean gettingData(){
        return !dataLoaded;
    }

    // PRIVATE HELPER METHODS

    //Gets JSON Info and passes it all into current scope.
    private class GetInformation extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
//            HttpHandler sh = new HttpHandler();
            String jsonStr = "";

//          Get Vancouver meters
            try {
                AssetManager am = activity.getAssets();
                String path = "json/parking_meters_van.json";
                InputStream is = am.open(path);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                jsonStr = new String(buffer, StandardCharsets.UTF_8);
                Log.e("JSON: ", jsonStr);

            }catch (Exception e){
                e.printStackTrace();
            }
            vanData(jsonStr);

            // Get New West meters
            try {
                AssetManager am = activity.getAssets();
                String path = "json/parking_meters_nwest.json";
                InputStream is = am.open(path);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                jsonStr = new String(buffer, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            newWestData(jsonStr); // Gets New Westminster json data.

            return null;
        }

        private void vanData(String jsonStr){
            if(jsonStr != null){
                try{
                    JSONObject jsonObj;
                    JSONArray jsArray = new JSONArray(jsonStr);
                    for(int i = 0; i < jsArray.length(); i++){
                        jsonObj = jsArray.getJSONObject(i);
                        JSONObject data = jsonObj.getJSONObject("fields");
                        JSONObject dataGeom = data.getJSONObject("geom");
                        JSONArray coordinates = dataGeom.getJSONArray("coordinates");
                        LatLng latlng = new LatLng(coordinates.getDouble(1), coordinates.getDouble(0));
                        Meter meterData = new Meter(latlng);
                        try{
                            meterData.setDescription(data.getString("timeineffe"));
                        } catch (Exception e){
                            meterData.setDescription("Not Known");
                        }
                        try{
                            meterData.setPrice("monday", 9, data.getString("r_mf_9a_6p"));
                        }catch (Exception e){
                            meterData.setPrice("monday", 9,  "Not known");
                            Log.e("JSON", "Not known time.");
                        }
                        try{
                            meterData.setPrice("monday", 19, data.getString("r_mf_6p_10"));
                        }catch (Exception e){
                            meterData.setPrice("monday", 19,  "Not known");
                            Log.e("JSON", "Not known time.");
                        }
                        try{
                            meterData.setPrice("saturday", 9, data.getString("r_sa_9a_6p"));
                        }catch (Exception e){
                            meterData.setPrice("saturday", 9,  "Not known");
                            Log.e("JSON", "Not known time.");
                        }
                        try{
                            meterData.setPrice("saturday", 19, data.getString("r_sa_6p_10"));
                        }catch (Exception e){
                            meterData.setPrice("saturday", 19,  "Not known");
                            Log.e("JSON", "Not known time.");
                        }
                        try{
                            meterData.setPrice("sunday", 9, data.getString("r_su_9a_6p"));
                        }catch (Exception e){
                            meterData.setPrice("sunday", 9,  "Not known");
                            Log.e("JSON", "Not known time.");
                        }
                        try{
                            meterData.setPrice("sunday", 19, data.getString("r_su_6p_10"));
                        }catch (Exception e){
                            meterData.setPrice("sunday", 19,  "Not known");
                            Log.e("JSON", "Not known time.");
                        }
                        try{
                            meterData.setTime("monday", 9, data.getString("t_mf_9a_6p"));
                        }catch (Exception e){
                            meterData.setTime("monday", 9,  "Not known");
                            Log.e("JSON", "Not known time.");
                        }
                        try{
                            meterData.setTime("monday", 19, data.getString("t_mf_6p_10"));
                        }catch (Exception e){
                            meterData.setTime("monday", 19,  "Not known");
                            Log.e("JSON", "Not known time.");
                        }
                        try{
                            meterData.setTime("saturday", 9, data.getString("t_sa_9a_6p"));
                        }catch (Exception e){
                            meterData.setTime("saturday", 9,  "Not known");
                            Log.e("JSON", "Not known time.");
                        }
                        try{
                            meterData.setTime("saturday", 19, data.getString("t_sa_6p_10"));
                        }catch (Exception e){
                            meterData.setTime("saturday", 19,  "Not known");
                            Log.e("JSON", "Not known time.");
                        }
                        try{
                            meterData.setTime("sunday", 9, data.getString("t_su_9a_6p"));
                        }catch (Exception e){
                            meterData.setTime("sunday", 9,  "Not known");
                            Log.e("JSON", "Not known time.");
                        }
                        try{
                            meterData.setTime("sunday", 19, data.getString("t_su_6p_10"));
                        }catch (Exception e){
                            meterData.setTime("sunday", 19,  "Not known");
                            Log.e("JSON", "Not known time.");
                        }
                        allMeters.add(meterData);
                    }

                }catch(Exception e){
                    Log.e("JSON", e.getLocalizedMessage());
                }
            }
        }

        //TODO
        private void newWestData(String jsonStr){
            if(jsonStr != null){
                try{
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray jsArray = jsonObj.getJSONArray("features");
                    for(int i = 0; i < jsArray.length(); i++){
                        jsonObj = jsArray.getJSONObject(i);
                        JSONObject dataGeom = jsonObj.getJSONObject("geometry");
                        JSONArray coordinates = dataGeom.getJSONArray("coordinates");
                        LatLng latlng = new LatLng(coordinates.getDouble(1), coordinates.getDouble(0));
                        Meter meterData = new Meter(latlng);
                        try{
                            jsonObj = jsonObj.getJSONObject("properties");
                            String time = jsonObj.getString("Sign_Definition");
                            meterData.setTime("monday", 9, time);
                            meterData.setTime("tuesday", 9, time);
                            meterData.setTime("wednesday", 9, time);
                            meterData.setTime("thursday", 9, time);
                            meterData.setTime("friday", 9, time);
                            meterData.setTime("saturday", 9, time);
                            meterData.setTime("sunday", 9, time);
                            meterData.setTime("monday", 19, time);
                            meterData.setTime("tuesday", 19, time);
                            meterData.setTime("wednesday", 19, time);
                            meterData.setTime("thursday", 19, time);
                            meterData.setTime("friday", 19, time);
                            meterData.setTime("saturday", 19, time);
                            meterData.setTime("monday", 19, time);
                        } catch (Exception e){
                            meterData.setTime("monday", 9, "NA");
                            meterData.setTime("tuesday", 9, "NA");
                            meterData.setTime("wednesday", 9, "NA");
                            meterData.setTime("thursday", 9, "NA");
                            meterData.setTime("friday", 9, "NA");
                            meterData.setTime("saturday", 9, "NA");
                            meterData.setTime("sunday", 9, "NA");
                            meterData.setTime("monday", 19, "NA");
                            meterData.setTime("tuesday", 19, "NA");
                            meterData.setTime("wednesday", 19, "NA");
                            meterData.setTime("thursday", 19, "NA");
                            meterData.setTime("friday", 19, "NA");
                            meterData.setTime("saturday", 19, "NA");
                            meterData.setTime("monday", 19, "NA");
                        }
                        try{
                            meterData.setPrice("monday", 9, "NA");
                            meterData.setPrice("tuesday", 9, "NA");
                            meterData.setPrice("wednesday", 9, "NA");
                            meterData.setPrice("thursday", 9, "NA");
                            meterData.setPrice("friday", 9, "NA");
                            meterData.setPrice("saturday", 9, "NA");
                            meterData.setPrice("sunday", 9, "NA");
                            meterData.setPrice("monday", 19, "NA");
                            meterData.setPrice("tuesday", 19, "NA");
                            meterData.setPrice("wednesday", 19, "NA");
                            meterData.setPrice("thursday", 19, "NA");
                            meterData.setPrice("friday", 19, "NA");
                            meterData.setPrice("saturday", 19, "NA");
                            meterData.setPrice("monday", 19, "NA");
                        }catch (Exception e){
                            //meterData.setPrice("Not known");
                        }

                        allMeters.add(meterData);
                    }
                    dataLoaded = true;
                }catch(Exception e){
                    Log.e("JSON", e.getLocalizedMessage());
                }
            }
        }

    }
}
