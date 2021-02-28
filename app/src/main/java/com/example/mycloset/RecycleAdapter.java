package com.example.mycloset;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;



import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    public List<ListPictures> listPictures;
    public Context ct;

    //
    public  OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void  setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;

    }





    public RecycleAdapter(List<ListPictures> listPictures, Context ct) {
        this.listPictures = listPictures;
        this.ct = ct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pictures, parent, false);
        return new ViewHolder(view,mListener);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListPictures listPicture = listPictures.get(position);
        Glide.with(ct)
                .load(listPicture.getImageUrl())
                .into(holder.listImg);

    }


    @Override
    public int getItemCount() {
        return listPictures.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView listImg;

        public ViewHolder(@NonNull View itemView,OnItemClickListener listener) {
            super(itemView);
            listImg = itemView.findViewById(R.id.article_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (listener!= null){
                                                    int position = getAdapterPosition();
                                                    if(position!=RecyclerView.NO_POSITION){
                                                        listener.onItemClick(position);
                                                    }
                                                }
                                            }
                                        }
            );
        }

}


}