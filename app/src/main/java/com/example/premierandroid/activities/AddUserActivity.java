package com.example.premierandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.premierandroid.R;
import com.example.premierandroid.parcelables.Contact;

public class AddUserActivity extends AppCompatActivity {

    Button boutonValider, boutonAnnuler;
    EditText etNom, etPrenom, etTelephone, etMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user); //vue principale

        boutonValider = (Button) findViewById(R.id.buttonValider);
        boutonAnnuler = (Button) findViewById(R.id.buttonAnnuler);

        etNom = (EditText) findViewById(R.id.editTextNom);
        etPrenom = (EditText) findViewById(R.id.editTextPrenom);
        etTelephone = (EditText) findViewById(R.id.editTextPhone);
        etMail = (EditText) findViewById(R.id.editTextTextEmailAddress);




        boutonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( TextUtils.isEmpty(etNom.getText())){

                    etNom.setError( "Un nom est requis!" );

                } else if( TextUtils.isEmpty(etPrenom.getText())){

                    etPrenom.setError( "Un prenom est requis!" );

                } else if( TextUtils.isEmpty(etTelephone.getText())){

                    etTelephone.setError( "Un numéro de téléphone est requis!" );

                } else if( TextUtils.isEmpty(etMail.getText())){

                    etMail.setError( "Un mail est requis!" );

                }   else{
                    Intent addUserActivityIntent = new Intent(AddUserActivity.this, MainActivity.class);
                    Contact contact = new Contact(etNom.getText().toString(),etPrenom.getText().toString(),etTelephone.getText().toString(),etMail.getText().toString());
                    addUserActivityIntent.putExtra("contact", contact);
                    startActivity(addUserActivityIntent);
                }


//                AddUserActivity.super.onBackPressed();


            }
        });



        boutonAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddUserActivity.super.onBackPressed();
            }
        });
    }
}
