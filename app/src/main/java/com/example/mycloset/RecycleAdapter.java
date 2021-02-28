package com.example.mycloset;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;



import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<ListPictures.ViewHolder> {
    List<ListPictures> listPictures;
    Context ct;

    public RecycleAdapter(List<ListPictures> articleLists, Context ct) {
        this.listPictures = listPictures;
        this.ct = ct;
    }

    @NonNull
    @Override
    public ListPictures.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pictures, parent, false);
        return new ListPictures.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPictures.ViewHolder holder, int position) {
        ListPictures articleList = listPictures.get(position);
        Glide.with(ct)
                .load(articleList.getImageUrl())
                .into(holder.listImg);

        holder.listName.setText(articleList.getArticleName());

    }


    @Override
    public int getItemCount() {
        return listPictures.size();
    }

}