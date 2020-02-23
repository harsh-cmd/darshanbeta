package com.example.darshanbeta;

import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProductAsyncTask extends android.os.AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... strings) {
        String result;
        String inputLine;
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuilder builder = new StringBuilder();
            while ((inputLine = reader.readLine()) != null) {
                builder.append(inputLine);
            }
            reader.close();
            inputStreamReader.close();
            result = builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }
}
