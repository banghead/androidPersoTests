package com.example.premierandroid.Threads;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetWebServThread extends Thread {

    public GetWebServThread() {
        
    }

    public void run(){ //appelé avec le start()
        System.out.println("le thread s'execute : ");

        try {
                URL url = new URL("http://10.0.2.2/IUT/testWebService/webService2.php?function=double&arg=5");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                System.out.println(readStream(in));

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
