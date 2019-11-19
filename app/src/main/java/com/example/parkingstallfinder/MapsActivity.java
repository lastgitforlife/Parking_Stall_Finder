package com.example.parkingstallfinder;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import com.example.parkingstallfinder.Util.Meter;
import com.example.parkingstallfinder.Util.MeterFilter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Spinner spinner;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when thez map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        MapFragment mapFragment1 = MapFragment.newInstance();
//        android.app.FragmentTransaction trans = getFragmentManager().beginTransaction();
//        trans.add(R.id.meters, mapFragment1);
//        trans.commit();
//        mapFragment.getMapAsync(this);


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
        //TODO: Get rid of soft lock.
        while (mf.gettingData());
        mf.search(van, van2);
        ArrayList<Meter> mL = mf.getMeterList();
        // Fill map with markers. Adjust for loop end condition to display more/less meters
        for(int i = 0; i < mL.size(); i++){
            addMarker(mL.get(i), "price");
        }
        float zoomLevel = 13.0f;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mL.get(0).getLocation(), zoomLevel));
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

        Spinner time = findViewById(R.id.time);
//        float time = time.tofloat();
        String msg = String.format(Locale.CANADA, "Meter: Lat: %4.3f  Lon: %4.3f %s: %s ",
                location.latitude, location.longitude, filter,  meter.getInfo(filter, "day.lower", time));

        mMap.addMarker(new MarkerOptions().position(location).title(msg));
    }
    public void onChangeMapType(View v) {
        if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL)
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        else
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
    public void onZoom(View v) {
        if (v.getId() == R.id.btnZoomIn)
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
        else
            mMap.animateCamera(CameraUpdateFactory.zoomOut());
    }

}
