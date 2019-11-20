package com.example.parkingstallfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.parkingstallfinder.Util.DataObserver.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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


//    /**
//     * Async task class to get json by making HTTP call
//     */
//    private class GetContacts extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            // Showing progress dialog
//            pDialog = new ProgressDialog(MainActivity.this);
//            pDialog.setMessage("Please wait...");
//            pDialog.setCancelable(false);
//            pDialog.show();
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... arg0) {
//            HttpHandler sh = new HttpHandler();
//            String jsonStr = null;
//
//            // Making a request to url and getting response
//            jsonStr = sh.makeServiceCall(SERVICE_URL);
//
//            Log.e(TAG, "Response from url: " + jsonStr);
//
//            if (jsonStr != null) {
//                try {
//                    //JSONObject jsonObj = new JSONObject(jsonStr);
//
//                    // Getting JSON Array node
//                    JSONArray toonJsonArray = new JSONArray(jsonStr);
//
//                    // looping through All Contacts
//                    for (int i = 0; i < toonJsonArray.length(); i++) {
//                        JSONObject c = toonJsonArray.getJSONObject(i);
//
//                        String id = c.getString("id");
//                        String firstName = c.getString("firstName");
//                        String lastName = c.getString("lastName");
//                        String occupation = c.getString("occupation");
//                        String gender = c.getString("gender");
//                        String pictureUrl = c.getString("pictureUrl");
//
//                        // tmp hash map for single contact
//                        Toon toon = new Toon();
//
//                        // adding each child node to HashMap key => value
//                        toon.setId(Integer.parseInt(id));
//                        toon.setFirstName(firstName);
//                        toon.setLastName(lastName);
//                        toon.setOccupation(occupation);
//                        toon.setGender(gender);
//                        toon.setPictureUrl(pictureUrl);
//
//                        // adding contact to contact list
//                        toonList.add(toon);
//                    }
//                } catch (final JSONException e) {
//                    Log.e(TAG, "Json parsing error: " + e.getMessage());
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(),
//                                    "Json parsing error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG)
//                                    .show();
//                        }
//                    });
//                }
//            } else {
//                Log.e(TAG, "Couldn't get json from server.");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(),
//                                "Couldn't get json from server. Check LogCat for possible errors!",
//                                Toast.LENGTH_LONG)
//                                .show();
//                    }
//                });
//
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//
//            // Dismiss the progress dialog
//            if (pDialog.isShowing())
//                pDialog.dismiss();
//
//            //Toon[] toonArray = toonList.toArray(new Toon[toonList.size()]);
//
//            ToonsAdapter adapter = new ToonsAdapter(MainActivity.this, toonList);
//
//            // Attach the adapter to a ListView
//            lv.setAdapter(adapter);
//        }
//    }
//


}
