package com.example.androidmobile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    public static final int spanCountOne = 1;
    public static final int spanCountThree = 3;

    private static final int viewTypeGrid = 1;
    private static final int viewTypeList = 2;

    private Context mContext;
    private List<Movies> mData;
    private GridLayoutManager mLayoutManager;
    private OnItemListener mOnItemListener;

    public Adapter(Context mContext, List<Movies> mData, GridLayoutManager layoutManager, OnItemListener onItemListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.mOnItemListener = onItemListener;
        mLayoutManager = layoutManager;
    }

    public int getItemViewType(int position){
        int spanCount = mLayoutManager.getSpanCount();
        if(spanCount == spanCountOne){
            return viewTypeList;
        }else{
            return viewTypeGrid;
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;

        LayoutInflater inflater = LayoutInflater.from(mContext);

        if(viewType == viewTypeList){
            v = inflater.inflate(R.layout.movie_item, parent,false);
        }else{
            v = inflater.inflate(R.layout.movie_grid,parent,false);
        }
        return new MyViewHolder(v, viewType, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.releaseDate.setText(mData.get(position).getDate());
        holder.name.setText(mData.get(position).getName());

        //using glide to display image
        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w500"+mData.get(position).getImg())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView releaseDate;
        TextView name;
        ImageView img;
        OnItemListener onItemListener;

        public MyViewHolder(@NonNull View itemView, int viewType, OnItemListener onItemListener) {
            super(itemView);
            this.onItemListener = onItemListener;

            if(viewType == viewTypeGrid){
                releaseDate = itemView.findViewById(R.id.releaseDateGrid);
                name = itemView.findViewById(R.id.titleGrid);
                img = itemView.findViewById(R.id.imageGrid);
            }else{
                releaseDate = itemView.findViewById(R.id.movieReleaseDate);
                name = itemView.findViewById(R.id.movieTitle);
                img = itemView.findViewById(R.id.movieImage);
            }
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemListener{
        void onItemClick(int position);
    }
}
