package com.example.parkingstallfinder.Util;

import android.os.AsyncTask;
import android.util.Log;

import com.example.parkingstallfinder.Util.DataObserver.HttpHandler;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Class used to get and filter meter data.
 */
public class MeterFilter{

    private LatLng[] searchArea = new LatLng[2]; // [0] = TopLeft, [1] = BtmRight.
    private ArrayList<Meter> currentScope; // Meters currently in search area.
    //TEMP TILL SEARCH IS FIXED
    private ArrayList<Meter> allMeters = new ArrayList<>(); // ALL METERS.

    /**
     * Constructor for Meter Filter.
     */
    public MeterFilter(){
        currentScope = new ArrayList<>();
        //TODO: FIX THIS
        GetInformation getInfo = new GetInformation();
        getInfo.execute();
    }

    /**
     * Changes the current search area for meters.
     * @param tL LatLng Coordinate object.
     * @param bR LatLng Coordinate object.
     */
    public void search(LatLng tL, LatLng bR){
        searchArea[0] = tL;
        searchArea[1] = bR;
        if(!currentScope.isEmpty())
            currentScope.clear();
        //TODO: FIX THIS
        double ulLat = tL.latitude;
        double ulLong = tL.longitude;
        double lrLat = bR.latitude;
        double lrLon = bR.longitude;
        currentScope = allMeters;
    }

    /**
     * Returns the Search area. Index 0 is TopLeft, Index 1 is BottomRight.
     * @return LatLng[] of size 2.
     */
    public LatLng[] getSearchArea(){
        return searchArea;
    }

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
        if(allMeters.size() < 100){
            return true;
        }
        return false;
    }

    // PRIVATE HELPER METHODS

    //Gets JSON Info and passes it all into current scope.
    private class GetInformation extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = null;

            // Making a request ot url and getting response
            jsonStr = sh.makeServiceCall("https://opendata.vancouver.ca/api/records/1.0/search/?dataset=parking-meters&rows=9999&facet=r_mf_9a_6p&facet=r_mf_6p_10&facet=r_sa_9a_6p&facet=r_sa_6p_10&facet=r_su_9a_6p&facet=r_su_6p_10&facet=timeineffe&facet=t_mf_9a_6p&facet=t_mf_6p_10&facet=t_sa_9a_6p&facet=t_sa_6p_10&facet=t_su_9a_6p&facet=t_su_6p_10&facet=creditcard&facet=geo_local_area");
            Log.e("JSON:" , jsonStr);
            vanData(jsonStr); //Gets vancouver json data.
            //newWestData(); // Gets New Westminster json data.

            return null;
        }

        private void vanData(String jsonStr){
            if(jsonStr != null){
                try{
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray jsArray = jsonObj.getJSONArray("records");
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
                            // r_mf_6p_10
                            // r_sa_9a_6p
                            // r_mf_9a_6p
                            // r_su_8p_10
                            // r_sa_6p_10
                            meterData.setPrice("monday", 9, data.getString("r_mf_9a_6p"));
                        }catch (Exception e){
                            meterData.setPrice("monday", 0,  "Not known");
                            Log.e("JSON", "Not known time.");
                        }

                        allMeters.add(meterData);
                    }
                }catch(Exception e){
                    Log.e("JSON", e.getLocalizedMessage());
                }
            }
        }

        private void newWestData(String jsonStr){
            if(jsonStr != null){
                try{
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray jsArray = jsonObj.getJSONArray("records");
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
                            //meterData.setPrice(data.getString("r_mf_9a_6p"));
                        }catch (Exception e){
                            //meterData.setPrice("Not known");
                        }

                        allMeters.add(meterData);
                    }
                }catch(Exception e){
                    Log.e("JSON", e.getLocalizedMessage());
                }
            }
        }
    }
}
