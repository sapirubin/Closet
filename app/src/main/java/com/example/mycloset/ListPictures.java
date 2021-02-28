package com.example.mycloset;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListPictures  {

    public String listPictures;
    public String imageUrl;

    public ListPictures(){

    }

    public ListPictures(String articleName, String imageUrl) {
        this.listPictures = articleName;
        this.imageUrl = imageUrl;
    }

    public ListPictures(List<ListPictures> listPictures, Context applicationContext) {
    }

    public String getArticleName() {
        return listPictures;
    }

    public void setArticleName(String articleName) {
        this.listPictures = articleName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView listImg;
//        TextView listName;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            listImg = itemView.findViewById(R.id.article_image);
//            listName = itemView.findViewById(R.id.article_name);
//        }
//    }
}