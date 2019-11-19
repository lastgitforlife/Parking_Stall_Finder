package com.example.parkingstallfinder;

import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.parkingstallfinder.Util.Meter;
import com.example.parkingstallfinder.Util.MeterFilter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private boolean vanFocusBool = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Vancouver and move the camera
        LatLng van = new LatLng(49.3, -123.9805344);
        LatLng van2 = new LatLng(49.0504, -122.3905344);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(van));


        MeterFilter mf = new MeterFilter(this);

        while (mf.gettingData());
//        for(int i = 0; mf.gettingData();){
//            i++;
//            i--;
//        }
        mf.search(van, van2);
        ArrayList<Meter> mL = mf.getMeterList();
//        addMarker(mL.get(0).getLocation()); // TODO Why doesn't it work without this line?
        // Fill map with markers. Adjust for loop end condition to display more/less meters
        for(int i = 0; i < mL.size()/20; i++){
            addMarker(mL.get(i), "time");
        }
        float zoomLevel = 13.0f;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(49.283662, -123.118197), zoomLevel)); // Pacific Centre
    }


    /**
     * Easy add marker.
     * @param location LatLng of location you want a marker at.
     */
    private void addMarker(LatLng location){
        String msg = String.format(Locale.CANADA, "Location: Lat: %4.3f  Lon: %4.3f",
                location.latitude, location.longitude);

        mMap.addMarker(new MarkerOptions().position(location).title(msg));
    }


    /**
     * Easy add marker for Meter data.
     * @param meter Meter object.
     * @param filter String that is "price" or "time".
     */
    private void addMarker(Meter meter, String filter){
        LatLng location = meter.getLocation();

        //TODO Needs to be adapted for current or target day/time
        String msg = String.format(Locale.CANADA, "Meter: Lat: %4.3f  Lon: %4.3f %s: %s ",
                location.latitude, location.longitude, filter,  meter.getInfo(filter, "saturday", 19));

        mMap.addMarker(new MarkerOptions().position(location).title(msg));
//        mMap.addMarker(new MarkerOptions().position(location).title(msg).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

    }

    //TODO. Make city HashSet
    public void onChangeMapType(View v) {
//        if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL)
//            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//        else
//            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


//        if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL)
//            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//        else
//            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        float zoomLevel = 13.0f;

        if (vanFocusBool) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(49.216882, -122.906853), zoomLevel)); // Queens Park
            vanFocusBool = false;
        }
        else {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(49.283662, -123.118197), zoomLevel)); // Pacific Centre
            vanFocusBool = true;
        }

    }

    public void onZoom(View v) {
        if (v.getId() == R.id.btnZoomIn)
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
        else
            mMap.animateCamera(CameraUpdateFactory.zoomOut());
    }

}
