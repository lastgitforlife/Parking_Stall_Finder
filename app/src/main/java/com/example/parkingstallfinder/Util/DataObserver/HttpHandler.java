package com.example.parkingstallfinder.Util.DataObserver;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Handles Http downloads for JSON.
 */
public class HttpHandler {
    private static final String TAG = HttpHandler.class.getSimpleName();
    public HttpHandler(){}
    public String makeServiceCall(String reqUrl){
        String response = null;
        try{
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        }catch(MalformedURLException e){
            Log.e(TAG, "MalformedURLException: " + e.getLocalizedMessage());
        }catch(ProtocolException e){
            Log.e(TAG, "ProtocolException: " + e.getLocalizedMessage());
        }catch(IOException e){
            Log.e(TAG, "IOException: ", e);
        }catch(Exception e){
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    /**
     * Converts an input stream into a multiline string.
     * @param is InputStream to be read.
     * @return String of the InputStream.
     */
    private String convertStreamToString(InputStream is){
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try{
            while ((line = reader.readLine()) != null){
                sb.append(line).append('\n');
            }
        }catch(IOException e){
            e.printStackTrace();
        } finally {
            try{
                is.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
