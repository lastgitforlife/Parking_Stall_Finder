package com.example.parkingstallfinder.Util;

import android.content.Context;
import android.content.res.AssetManager;
import android.renderscript.ScriptGroup;
import android.util.Log;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;

public class UtilJson {

    private Context context;

    public UtilJson(Context context){
        try{
            this.context = context;
        }catch(Exception e){
            Log.e("ERROR", e.getLocalizedMessage());
        }
    }

    public String readJSONFromAsset(String fileName) {
        String json = null;
        try{
            InputStream in = context.getAssets().open(fileName);
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            json = new String(buffer, "UTF-8");
        }catch(IOException e){
            Log.e("Error", e.getLocalizedMessage());
        }

        return json;
    }
}
