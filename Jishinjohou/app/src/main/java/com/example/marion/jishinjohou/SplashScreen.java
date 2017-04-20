package com.example.marion.jishinjohou;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {
    ArrayList<Seisme> listeDeSeisme = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Si le wifi fonctionne, on lance une asynTask.
        if (TestWifiData()) {
            DownloadTask task = new DownloadTask();
            task.execute("http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_day.geojson");
        }
    }

    public boolean TestWifiData() {
        boolean isConnected = false, isWiFi = false;

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        try {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

            assert activeNetwork != null;
            isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;

        } catch (Exception e) {
            Toast.makeText(SplashScreen.this, "Vous êtes hors-ligne!", Toast.LENGTH_LONG).show();
        }

        if (isConnected) {
            if (isWiFi) {
                Toast.makeText(SplashScreen.this, "Vous êtes en Wifi", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(SplashScreen.this, "Vous êtes en 3G", Toast.LENGTH_LONG).show();
            }
        }
        return isConnected;
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection;

            //On se connecte et récupére les données
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data = reader.read();

                while ((data != -1)) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //On fait un try car les data pourrait ne pas être un JSON
            try {
                JSONObject jsonObject = new JSONObject(result);

                String typeFeatures = jsonObject.getString("features");
                JSONArray arrayFeatures = new JSONArray(typeFeatures);

                //On récupère nos Json
                for (int i = 0; i < arrayFeatures.length(); i++) {
                    JSONObject jsonPart = arrayFeatures.getJSONObject(i);

                    String JSONProperties = jsonPart.getString("properties");
                    JSONObject ObjProperties = new JSONObject(JSONProperties);

                    String JSONGeometry = jsonPart.getString("geometry");
                    JSONObject ObjGeometry = new JSONObject(JSONGeometry);


                    //On crée des Seismes
                    listeDeSeisme.add(
                            new Seisme(
                                    ObjProperties.getString("title"),
                                    ObjProperties.getString("place"),
                                    ObjProperties.getString("code"),
                                    ObjProperties.getLong("time"),
                                    ObjProperties.getString("url"),
                                    ObjGeometry.getString("coordinates")
                            )
                    );
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Puis on passe à la Main
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            intent.putExtra("ListeSeisme", listeDeSeisme);
            startActivity(intent);
            finish();

        }
    }
}