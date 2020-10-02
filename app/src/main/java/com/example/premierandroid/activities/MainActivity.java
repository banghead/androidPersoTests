package com.example.premierandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.premierandroid.R;
import com.example.premierandroid.Threads.GetWebServThread;
import com.example.premierandroid.Threads.GetWebServThreadJSON;
import com.example.premierandroid.parcelables.Contact;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;

    Button button, button2, button3, button4,button5, button6, button7, button8, button9, button10;

    ImageView androidImage;

    GifImageView androidGif;

    TextView textNom, textPrenom, textMail, textNum, json1, json12, json2, json22, json3, json32, json4;

    CheckBox json42;

    TableLayout table, tableJson;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //vue principale

        final MainActivity that = this;

        button = (Button) findViewById(R.id.button5);
        button2 = (Button) findViewById(R.id.button6);
        button3 = (Button) findViewById(R.id.button7);
        button4 = (Button) findViewById(R.id.button8);
        button5 = (Button) findViewById(R.id.button9);
        button6 = (Button) findViewById(R.id.button10);
        button7 = (Button) findViewById(R.id.button11);
        button8 = (Button) findViewById(R.id.button12);
        button9 = (Button) findViewById(R.id.button13);
        button10 = (Button) findViewById(R.id.button14);

        androidImage = (ImageView) findViewById(R.id.imageView7);
        androidGif = (GifImageView) findViewById(R.id.imageGif);

        textNom = (TextView) findViewById(R.id.textNom);
        textPrenom = (TextView) findViewById(R.id.textPrenom);
        textMail = (TextView) findViewById(R.id.textEmailAddress);
        textNum = (TextView) findViewById(R.id.textPhone);


        table = (TableLayout) findViewById(R.id.table);

        tableJson = (TableLayout) findViewById(R.id.tableJSON);


        json1 = (TextView) findViewById(R.id.textViewJSON1);
        json12 = (TextView) findViewById(R.id.textViewJSON12);
        json2 = (TextView) findViewById(R.id.textViewJSON2);
        json22 = (TextView) findViewById(R.id.textViewJSON22);
        json3 = (TextView) findViewById(R.id.textViewJSON3);
        json32 = (TextView) findViewById(R.id.textViewJSON32);
        json4 = (TextView) findViewById(R.id.textViewJSON4);
        json42 = (CheckBox) findViewById(R.id.checkBoxJSON42);

        //Récupération du Spinner déclaré dans le fichier main.xml de res/layout
        spinner = findViewById(R.id.spinner);


        //Création d'une liste d'élément à mettre dans le Spinner(pour l'exemple)
        ArrayList<String> exempleList = new ArrayList<>();
        exempleList.add("Lundi");
        exempleList.add("Jeudi");
        exempleList.add("Week-end");
        exempleList.add("Base de données");


        /*Le Spinner a besoin d'un adapter pour sa presentation alors on lui passe le context(this) et
                un fichier de presentation par défaut( android.R.layout.simple_spinner_item)
        Avec la liste des elements (exemple) */
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                exempleList
        );


        /* On definit une présentation du spinner quand il est déroulé  (android.R.layout.simple_spinner_dropdown_item) */
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner et c'est tout
        spinner.setAdapter(adapter);


        /* ********* database
         *
         *
         *
         */

        SQLiteDatabase dataBase = openOrCreateDatabase("databaseExemple",MODE_PRIVATE, null);


        /* ***** Fin database
         *
         *
         *
         *
         */


        //affiche la table uniquement si extras non vide

        // recupération de l'intent.
        Intent intent = getIntent();

        // recuperation des extras si il y en a
        Bundle extras = intent.getExtras();
        if (extras != null && extras.containsKey("contact")){
            table.setVisibility(View.VISIBLE);

            Contact contact = (Contact) extras.getParcelable("contact");


            assert contact != null;
            textNom.setText(contact.getNom());
            textNum.setText(contact.getNumero());
            textPrenom.setText(contact.getPrenom());
            textMail.setText(contact.getMail());
        }



        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Uri gmmIntentUri = Uri.parse("geo:0,0?q=");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                }, 1000);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Thread getServ = new GetWebServThread();
                getServ.start();

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent addUserActivity = new Intent(MainActivity.this, AddUserActivity.class);
                startActivity(addUserActivity);

            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getIntent().removeExtra("contact");
                table.setVisibility(View.GONE );
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Thread getServ = new GetWebServThreadJSON(MainActivity.this);
                getServ.start();


                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.apply();

                String str;

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                str = prefs.getString("retourRequeteJSON", "no return");


                try {
                    tableJson.setVisibility(View.VISIBLE);

                    JSONObject json = new JSONObject(str);

                    Iterator<String> iter = json.keys(); //mise en place des clées
                    json1.setText(iter.next());
                    json2.setText(iter.next());
                    json3.setText(iter.next());
                    json4.setText(iter.next());

                    json12.setText(String.valueOf(json.getInt("userId"))); // values
                    json22.setText(String.valueOf(json.getInt("id")));
                    json32.setText(json.getString("title"));
                    json42.setChecked(json.getBoolean("completed"));


                    //fonction ligne dynamique

                    Iterator<String> iter2 = json.keys(); //mise en place des clées
                    while (iter2.hasNext()) {
                        TableRow tr = new TableRow(MainActivity.this);
                        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                        String obj = iter2.next();

                        TextView tv = new TextView(MainActivity.this);
                        tv.setText(obj);
                        tr.addView(tv);

                        TextView tv2;


                        try { //is boolean

                            tv2 = new CheckBox(MainActivity.this);
                            ((CheckBox) tv2).setChecked(json.getBoolean(obj));
                            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                            tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                            tr.addView(tv2);

                        } catch (Exception e) { //is int
                            try {
                                tv2 = new TextView(MainActivity.this);
                                tv2.setText(String.valueOf(json.getInt(obj)));
                                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                                tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                                tr.addView(tv2);
                            } catch (Exception exc) { // else string

                                tv2 = new TextView(MainActivity.this);
                                try {
                                    tv2.setText(json.getString(obj));
                                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                                    tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                                    tr.addView(tv2);
                                } catch (JSONException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }

                        tableJson.addView(tr, new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    }
                } catch (Exception globalEx) {
                    globalEx.printStackTrace();
                }
            }

        });

        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                androidImage.setVisibility(View.GONE); //invisible mais ne laisse pas d'espace blanc vide
                androidGif.setVisibility(View.VISIBLE); // rendre visible

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        androidImage.setVisibility(View.VISIBLE);
                        androidGif.setVisibility(View.GONE);
                    }
                }, 10000);   //10 secondes



            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


            }
        });

        final IntentIntegrator intentIntegrator = new IntentIntegrator(this);

        button9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                intentIntegrator.initiateScan();

            }
        });


        button10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                intentIntegrator.initiateScan();


            }
        });






        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (spinner.getSelectedItem() == "Lundi"){
                    button.setVisibility(View.VISIBLE); // fait apparaitre
                    button2.setVisibility(View.VISIBLE); // fait apparaitre
                    button3.setVisibility(View.GONE); // fait disparaitre
                    button4.setVisibility(View.GONE); // fait disparaitre
                    button5.setVisibility(View.GONE); // fait disparaitre
                    button6.setVisibility(View.GONE); // fait disparaitre
                    button7.setVisibility(View.GONE); // fait disparaitre
                    button8.setVisibility(View.GONE); // fait disparaitre
                } else if(spinner.getSelectedItem() == "Jeudi"){ //récupère le nom == "xxx"
                    button.setVisibility(View.GONE); // fait apparaitre
                    button2.setVisibility(View.GONE); // fait apparaitre
                    button3.setVisibility(View.VISIBLE); // fait disparaitre
                    button4.setVisibility(View.VISIBLE); // fait disparaitre
                    button5.setVisibility(View.GONE); // fait disparaitre
                    button6.setVisibility(View.GONE); // fait disparaitre
                    button7.setVisibility(View.GONE); // fait disparaitre
                    button8.setVisibility(View.GONE); // fait disparaitre
                } else if (spinner.getSelectedItem() == "Week-end"){
                    button.setVisibility(View.GONE); // fait apparaitre
                    button2.setVisibility(View.GONE); // fait apparaitre
                    button3.setVisibility(View.GONE); // fait disparaitre
                    button4.setVisibility(View.GONE); // fait disparaitre
                    button5.setVisibility(View.VISIBLE); // fait disparaitre
                    button6.setVisibility(View.VISIBLE); // fait disparaitre
                    button7.setVisibility(View.GONE); // fait disparaitre
                    button8.setVisibility(View.GONE); // fait disparaitre
                } else {
                    button.setVisibility(View.GONE); // fait apparaitre
                    button2.setVisibility(View.GONE); // fait apparaitre
                    button3.setVisibility(View.GONE); // fait disparaitre
                    button4.setVisibility(View.GONE); // fait disparaitre
                    button5.setVisibility(View.GONE); // fait disparaitre
                    button6.setVisibility(View.GONE); // fait disparaitre
                    button7.setVisibility(View.VISIBLE); // fait disparaitre
                    button8.setVisibility(View.VISIBLE); // fait disparaitre
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                System.out.println("il n'y a rien ici");
            }



        });


    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        try {
//            super.onActivityResult(requestCode, resultCode, data);
//
//            if (requestCode == REQUEST_CODE  && resultCode  == RESULT_OK) {
//
//                String requiredValue = data.getStringExtra("key");
//            }
//        } catch (Exception ex) {
//            Toast.makeText(Activity.this, ex.toString(),
//                    Toast.LENGTH_SHORT).show();
//        }
//
//    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case IntentIntegrator.REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {

                    System.out.println("je passe ici");

                    IntentResult intentResult =
                            IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

                    if (intentResult != null) {

                        System.out.println("je ne suis pas null");

                        String contents ="Valeur du code : " + intentResult.getContents();
                        String format = intentResult.getFormatName();

                        contents += "\nFormat : "+ format;

                        System.out.println("SEARCH_EAN"+ "OK, EAN: " + contents + ", FORMAT: " + format);
                        Toast.makeText(this, contents, Toast.LENGTH_SHORT).show();
                    } else {
                        System.out.println("je suis null");
                        Log.e("SEARCH_EAN", "IntentResult je NULL!");
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    System.out.println("je suis cancel");
                    Log.e("SEARCH_EAN", "CANCEL");
                }
        }
    }


}



