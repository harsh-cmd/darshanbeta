package com.example.darshanbeta;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MyPreference {
    private static final MyPreference ourInstance = new MyPreference();
    SharedPreferences preferences;

    public static MyPreference getInstance() {
        return ourInstance;
    }

    private MyPreference() {
    }

    void setMyPreferece(Context context) {
        preferences = context.getSharedPreferences("validEmailPreference", Context.MODE_PRIVATE);
    }

    boolean getPreference(String key) {
        return preferences.getBoolean(key, true);
    }


    void setPreferences(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
        editor.commit();
    }


}
