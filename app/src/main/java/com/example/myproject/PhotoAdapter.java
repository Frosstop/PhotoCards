package com.example.myproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {


    private List<PhotoItem> listPhoto;
    Context context;




//  Применяет все данные из List в MainActivity в List PhotoAdapter
    public PhotoAdapter (List<PhotoItem> listPhoto,Context context) {
        this.listPhoto = listPhoto;
        this.context = context;

    }


//  Подключаем pattern к ViewPager2
    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhotoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pattern,parent,false));
    }
//  Берёт текущую позицию в List
    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.PhotoViewHolder holder, int position) {
        holder.setPhotoView(listPhoto.get(position),context);
    }

//  Определяем колличество переменных в List и устанавливаем кол-во страниц ViewPager2
    @Override
    public int getItemCount() {
        return listPhoto.size();
    }



    //  Подключение pattern и вывод всех данных
    static class PhotoViewHolder extends RecyclerView.ViewHolder {

        ImageView PhotoCard;
        TextView Text,GPS,Name,Date;

//      Подключение виджетов из pattern
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.Main_Name);
            PhotoCard = itemView.findViewById(R.id.imageView);
            Date = itemView.findViewById(R.id.Main_Date);
            Text = itemView.findViewById(R.id.main_text);
            GPS = itemView.findViewById(R.id.Geolocation);
        }
//      Устанавливаем все данные в pattern
@SuppressLint({"SdCardPath", "CheckResult"})
        void setPhotoView(PhotoItem photoItem,Context context) {
            Name.setText(photoItem.Name);
            Glide.with(context).load(Uri.parse(photoItem.photoUri)).into(PhotoCard);
            Date.setText(photoItem.Date);
//            PhotoCard.setImageURI(Uri.parse(photoItem.photoUri));
            Text.setText(photoItem.Text);
            GPS.setText(photoItem.GeolocationPath);
        }

    }
}
