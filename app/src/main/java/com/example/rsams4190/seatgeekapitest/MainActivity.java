package com.example.rsams4190.seatgeekapitest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

   // private CurrentEvents mCurrentEvents;

    private ListView mlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mlistView = (ListView) findViewById(R.id.list_view);

        String clientID = "OTY3NjUxOHwxNTEwOTU0MjA2LjE0";

      //location issue
        double lat = 45.5007;
        double lon = -122.57;


        final String seatGeekUrl = "https://api.seatgeek.com/2/events?client_id=" + clientID + "&geoip=true&lat=" + lat + "&lon=" + lon;

        if (isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(seatGeekUrl)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        final String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   try{
                                       getCurrentDetails(jsonData);
                                   } catch (JSONException e) {
                                       e.printStackTrace();
                                   }
                               }
                           });


                        } else {
                            alertUserAboutError();
                        }
                    }
                    catch (IOException e){
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        }
        else{
            Toast.makeText(this, getString(R.string.network_unavailable_message),
                    Toast.LENGTH_LONG).show();
        }

        Log.d(TAG, "Main UI is running!");
    }

    private void getCurrentDetails(String jsonData) throws JSONException {
        JSONObject funevents = new JSONObject(jsonData);

        JSONArray eventsJSON= funevents.getJSONArray("events");
        CurrentEvents[] currentevents = new CurrentEvents[eventsJSON.length()];

        String[] titleArray = new String[eventsJSON.length()];

        for (int i = 0; i < eventsJSON.length(); i++) {
            JSONObject currenteventJSON = eventsJSON.getJSONObject(i);
            //get title
            String title = currenteventJSON.getString("title");

            //get image (from performers)
            ArrayList<String> performers = new ArrayList<>();
            JSONArray performersJSON = currenteventJSON.getJSONArray("performers");
            for (int y = 0; y < performersJSON.length(); y++) {
                performers.add(performersJSON.getJSONObject(y).getString("image"));
            }

            //get address
            String address = (String)currenteventJSON.getJSONObject("venue").getString("address");
            //get extended_address
            String extended_address = (String)currenteventJSON.getJSONObject("venue").getString("extended_address");
            //get type (from events)
            String type = currenteventJSON.getString("type");

            String image = String.valueOf(performers);
            //String image = currenteventJSON.getString("image");


            Log.d(TAG, title); //title of event

            //Log.d(TAG, String.valueOf(performers));

            Log.d(TAG, image); //image url Log.d(TAG, String.valueOf(performers));
            Log.d(TAG, address); //first part of the address (the numbers)
            Log.d(TAG, extended_address); //city, state and zip code
            Log.d(TAG, type); //ex. rodeo, concert, etc.

            //CurrentEvents currentevent = new CurrentEvents(title, performers, address, extended_address, type);
            CurrentEvents currentevent = new CurrentEvents(title, performers, address, extended_address, type, image);
            currentevents[i] = currentevent;
            titleArray[i] = currentevents[i].getTitle();
        }

        EventAdapter adapter = new EventAdapter(this, currentevents);
        mlistView.setAdapter(adapter);




        String meta = funevents.getString("meta");
        Log.i(TAG, "From JSON: " + meta );





    }

    private boolean isNetworkAvailable(){
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }
}
