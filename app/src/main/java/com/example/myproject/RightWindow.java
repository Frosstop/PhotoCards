package com.example.myproject;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RightWindow extends AppCompatActivity {

    private static final String TAG = "ASDB";

    Uri DataImage;
    String Geoposition,currentDate;
    double lat, lon;
    ImageView NewImage;
    EditText NewText, NewName;
    TextView NewGeolocation, NewDate;
    Geocoder geocoder;
    List<Address> addresses;
    private SQL mDataBase;
    FusedLocationProviderClient fusedLocationProviderClient;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.right_layout);

        NewName = findViewById(R.id.NewName);
        NewImage = findViewById(R.id.NewImage);
        NewDate = findViewById(R.id.NewDate);
        NewText = findViewById(R.id.NewText);
        NewGeolocation = findViewById(R.id.NewGeolocation);
        geocoder = new Geocoder(this);
        mDataBase = new SQL(getApplicationContext());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        currentDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        NewDate.setText(currentDate);
    }

    //  Метод для открытия галереи и выбора фото
    public void openGallery(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);
    }

    //  Метод для получения данных из фото
    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            DataImage = data.getData();
            Glide.with(this).load(DataImage).into(NewImage);
            Geoposition = "Геопозиция не определена";

            NewGeolocation.setText(getGeoposition(DataImage));

        }


    }

    public String getGeoposition(Uri data) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(getContentResolver().openInputStream(data));
            if (metadata.getFirstDirectoryOfType(GpsDirectory.class) != null) {
                GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);
                GeoLocation location = gpsDirectory.getGeoLocation();

                if (location != null) {
                    lat = location.getLatitude();
                    lon = location.getLongitude();
                    addresses = geocoder.getFromLocation(lat, lon, 1);
                    Geoposition = addresses.get(0).getAddressLine(0);
                }
            }
        } catch (ImageProcessingException | IOException e) {
            throw new RuntimeException(e);
        }
        return Geoposition;
    }

    public void SaveToSQLite(String Name, String Photo, String Date, String Text, String Geoposition) {
        if (Photo != null) {
            if (Name.equals("")) Name = "Без названия";
            if (Text.equals("")) Text = "Без описания";
            if (Geoposition.equals("")) Geoposition = "Геопозиция не определена";
            if (Date.equals("")) Date = "01.01.1970";
            mDataBase.InsertPhoto(Name, Photo, Date, Text, Geoposition);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDataBase != null) {
            mDataBase.close();
        }
    }

    public void BackFromCreating(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void Save(View v) {
        if (NewImage.getDrawable() == null) {
            Toast.makeText(this,"Добавьте фото",Toast.LENGTH_LONG).show();
        }
        else {
            Intent i = new Intent(this, MainActivity.class);
            SaveToSQLite(NewName.getText().toString(), DataImage.toString(), NewDate.getText().toString(),
                    NewText.getText().toString(), NewGeolocation.getText().toString());
            startActivity(i);
        }

    }

    public void Geoposition_My() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if(NewGeolocation.getText().toString().equals("Геопозиция не определена")) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);


                        NewGeolocation.setText(String.valueOf(addresses.get(0).getAddressLine(0)));
//
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

    }



}

