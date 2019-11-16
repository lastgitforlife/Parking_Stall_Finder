package com.example.parkingstallfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
//    public void onMapsActivityClick(View v) {
//        Intent i = new Intent(this, MapsActivity.class);
//        startActivity(i);
//    }
}
