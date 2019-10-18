package com.example.parkingstallfinder;

import androidx.fragment.app.FragmentActivity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.example.parkingstallfinder.Util.Meter;
import com.example.parkingstallfinder.Util.MeterFilter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

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

        // Add a marker in Sydney and move the camera
        LatLng van = new LatLng(49.3, -123.9805344);
        LatLng van2 = new LatLng(49.0504, -122.3905344);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(van));
        MeterFilter mf = new MeterFilter();
        mf.search(van, van2);
        for(int i = 0; mf.gettingData();){
            i++;
            i--;
        }
        ArrayList<Meter> mL = mf.getMeterList();
        for(int i = 0; i < mL.size()/4; i++){
            addMarker(mL.get(i));
        }
    }

    /**
     * Easy add marker.
     * @param location LatLng of location you want a marker at.
     */
    private void addMarker(LatLng location){
        String msg = String.format(Locale.CANADA, "Location: Lat: %4.3f  Lon: %4.3f",
                location.latitude, location.longitude);

        mMap.addMarker(new MarkerOptions().position(location).title(msg));

        float zoomLevel = 13.0f;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));
    }

    /**
     * Easy add marker for Meter data.
     * @param meter Meter object.
     */
    private void addMarker(Meter meter){
        LatLng location = meter.getLocation();
        String msg = String.format(Locale.CANADA, "Meter: Lat: %4.3f  Lon: %4.3f Price: %s ",
                location.latitude, location.longitude, meter.getPrice());

        mMap.addMarker(new MarkerOptions().position(location).title(msg));

        float zoomLevel = 13.0f;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));
    }
}
