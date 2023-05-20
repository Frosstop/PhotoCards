package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.databinding.GalleryBinding;

import java.util.ArrayList;
import java.util.List;

public class Gallery extends AppCompatActivity {

    private GalleryBinding binding;

    private SQL mDataBase;
    private List<PhotoItem> photoItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);

        final RecyclerView GalleryView = findViewById(R.id.RecyclerView);

        mDataBase = new SQL(getApplicationContext());
        photoItems = mDataBase.listPhoto();

        GalleryView.setLayoutManager(new GridLayoutManager(this,2,RecyclerView.VERTICAL,false));
        GalleryAdapter adapter = new GalleryAdapter(photoItems,this);
        GalleryView.setAdapter(adapter);


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDataBase != null){
            mDataBase.close();
        }
    }

    public void ToMainFromGallery (View v) {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void Button (View v) {
        Intent i = new Intent(this,RightWindow.class);
        startActivity(i);

    }
}
