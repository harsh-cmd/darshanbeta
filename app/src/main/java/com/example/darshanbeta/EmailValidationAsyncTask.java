package com.example.darshanbeta;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.MainThread;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EmailValidationAsyncTask extends android.os.AsyncTask<String, Void, String> {

    private Context context;
    private static final String isValid = "isValid";

    EmailValidationAsyncTask(Context context) {
        this.context = context;
    }

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

   /* @Override
    protected void onPostExecute(String s) {
        JSONObject jsonObject;
        String domainName;
        Boolean formatValid;
        Boolean smtpCheck;
        try {
            jsonObject = new JSONObject(s);
            domainName = jsonObject.getString("domain");
            formatValid = jsonObject.getBoolean("format_valid");
            smtpCheck = jsonObject.getBoolean("smtp_check");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

}
