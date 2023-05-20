package com.example.myproject;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class  GalleryAdapter extends RecyclerView.Adapter <GalleryAdapter.GalleryViewHolder> {

    private List<PhotoItem> listPhoto;
    Context context;

    public GalleryAdapter (List<PhotoItem> listPhoto,Context context){
        this.listPhoto = listPhoto;
        this.context = context;
    }





    @NonNull
    @Override
    public GalleryAdapter.GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GalleryAdapter.GalleryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pattern_for_gallery,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryAdapter.GalleryViewHolder holder, int position) {
        holder.setGalleryView(listPhoto.get(position),context);
    }

    @Override
    public int getItemCount() {
        return listPhoto.size();
    }





    static class GalleryViewHolder extends RecyclerView.ViewHolder {

        ImageView galleryPhoto;
        TextView Name;
        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.NameInGallery);
            galleryPhoto = itemView.findViewById(R.id.Gallery_Photo);
        }

        void setGalleryView (PhotoItem photoItem, Context context) {
            Glide.with(context).load(Uri.parse(photoItem.photoUri)).into(galleryPhoto);
            Name.setText(photoItem.Name);
        }
    }


}
