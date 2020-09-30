package com.example.premierandroid.Threads;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.premierandroid.activities.MainActivity;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetWebServThreadJSON extends Thread {

    private MainActivity mainActivity;

    public GetWebServThreadJSON(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }


    public void run(){ //appelé avec le start()
        System.out.println("le thread JSON s'execute : ");

        try {
            URL url = new URL("https://jsonplaceholder.typicode.com/todos/1");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//            System.out.println(readStream(in));


            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this.mainActivity); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();

            editor.putString("retourRequeteJSON", readStream(in));
            editor.apply();


            urlConnection.disconnect();

        } catch (IOException e){
            System.out.println("Error : " + e);
        }
    }

    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }



}
