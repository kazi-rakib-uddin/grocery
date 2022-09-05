package com.example.erashop.Session;

import android.content.Context;
import android.content.SharedPreferences;

public class OBSession {

    SharedPreferences sharedPreferences;
    private Context context;
    String install;

    public OBSession(Context context) {

        this.context=context;

        sharedPreferences=context.getSharedPreferences("On_Boarding", Context.MODE_PRIVATE);

    }

    public String getInstall() {
        install=sharedPreferences.getString("install","");
        return install;
    }

    public void setInstall(String install) {
        sharedPreferences.edit().putString("install",install).commit();
        this.install = install;
    }
}
