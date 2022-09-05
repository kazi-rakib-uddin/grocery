package com.example.erashop.Session;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    private String user_id;

    private Context context;

    SharedPreferences sharedPreferences;


    public Session(Context context) {

        this.context=context;

        sharedPreferences=context.getSharedPreferences("login_details", Context.MODE_PRIVATE);

    }



    public String getUser_id() {
        user_id=sharedPreferences.getString("user_id","");
        return user_id;
    }

    public void setUser_id(String user_id) {
        sharedPreferences.edit().putString("user_id",user_id).commit();
        this.user_id = user_id;
    }



    public  void  remove(){
        sharedPreferences.edit().clear().commit();
    }
}
