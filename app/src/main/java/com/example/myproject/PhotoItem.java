package com.example.myproject;

import android.net.Uri;

import java.util.Objects;


public class PhotoItem {
    long Id;
    String Name;
    String photoUri;
    String Text;
    String GeolocationPath;
    String Date;




    public PhotoItem (String Name, String photoUri, String Text, String GeolocationPath, String Date) {
        this.Name = Name;
        this.photoUri = photoUri;
        this.Text = Text;
        this.GeolocationPath = GeolocationPath;
        this.Date = Date;
    }
//  Геттеры
    public String getphotoUri() {
        return photoUri;
    }
    public String getText() {
        return Text;
    }
    public String getGeolocationPath() {
        return GeolocationPath;
    }
    public String getDate() {
        return Date;
    }
    public String getName() {
        return Name;
    }
    public Long getId() {
        return Id;
    }


//  сеттеры
    public void setPhotoURL (String photoUri) {
        this.photoUri = photoUri;
    }
    public void setText (String Text) {
        this.Text = Text;
    }
    public void setGeolocation(String GeolocationPath) {
        this.GeolocationPath = GeolocationPath;
    }
    public void setDate (String Date) {
        this.Date = Date;
    }
    public void settName (String Name) {
        this.Name = Name;
    }
    public void setId (Long Id) {
        this.Id = Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhotoItem photoItem = (PhotoItem) o;
        return photoUri == photoItem.photoUri;
    }
    @Override
    public int hashCode() {
        return Objects.hash(photoUri);
    }


}
