package com.example.myproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myproject.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private List<PhotoItem> photoItems = new ArrayList<>();
    private PhotoAdapter mAdapter;
    private SQL mDataBase;


    @SuppressLint("SdCardPath")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final ViewPager2 photoView = findViewById(R.id.Page);
        mDataBase = new SQL(getApplicationContext());
        photoItems = mDataBase.listPhoto();
        Collections.shuffle(photoItems);
        photoView.setAdapter(new PhotoAdapter(photoItems,this));

    }



    public void Button (View v) {
        Intent i = new Intent(this,RightWindow.class);
        startActivity(i);

    }

    public void ToGalleryFromMain (View v) {
        Intent i = new Intent(this,Gallery.class);
        startActivity(i);
    }
}
