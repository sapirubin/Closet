package com.example.mycloset;


import android.content.Context;

import java.util.List;

public class ListPictures  {
    private String imageUrl;

    public ListPictures() {
        //empty constructor
    }

    public ListPictures(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ListPictures(List<ListPictures> listPictures, Context applicationContext) {
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



}