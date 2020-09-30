package com.example.premierandroid.parcelables;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {

    private String nom;
    private String prenom;
    private String numero;
    private String mail;

    public Contact(String nom, String prenom, String numero, String mail) {
        this.nom = nom;
        this.prenom = prenom;
        this.numero = numero;
        this.mail = mail;
    }

    protected Contact(Parcel in) {
        nom = in.readString();
        prenom = in.readString();
        numero = in.readString();
        mail = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public int describeContents() {
        return 0; // pas de FileDescriptor
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // ordre important
        dest.writeString(nom);
        dest.writeString(prenom);
        dest.writeString(numero);
        dest.writeString(mail);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", numero='" + numero + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
