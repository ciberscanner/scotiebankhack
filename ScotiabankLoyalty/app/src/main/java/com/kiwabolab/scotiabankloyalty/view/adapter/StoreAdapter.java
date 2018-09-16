package com.kiwabolab.scotiabankloyalty.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kiwabolab.scotiabankloyalty.R;
import com.kiwabolab.scotiabankloyalty.model.Servidor;
import com.kiwabolab.scotiabankloyalty.model.Store;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.MyViewHolder> {
    //----------------------------------------------------------------------------------------------
    //Variables
    private List<Store> moviesList;
    private Context ctx;
    //----------------------------------------------------------------------------------------------
    //
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, desc;
        public ImageView img;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt_item_title);
            img = (ImageView) view.findViewById(R.id.img_item_image);
            desc = (TextView) view.findViewById(R.id.txt_item_description);
        }
    }
    //----------------------------------------------------------------------------------------------
    //Constructor
    public StoreAdapter(List<Store> moviesList, Context ctx) {
        this.moviesList = moviesList;
        this.ctx = ctx;
    }
    //----------------------------------------------------------------------------------------------
    //
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_establecimiento, parent, false);

        return new MyViewHolder(itemView);
    }
    //----------------------------------------------------------------------------------------------
    //
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Store movie = moviesList.get(position);
        holder.title.setText(movie.getName());

        Picasso.with(ctx)
                .load(Servidor.getImage_url(movie.getImg()))
                .placeholder(R.mipmap.shop)
                .error(R.mipmap.shop)
                .into(holder.img);

        Log.v("IMAGEN",Servidor.getImage_url(movie.getImg()));

        String aux = "";
        if(movie.getDescription().length()>49){
            aux = movie.getDescription().substring(0,50)+"...";
        }else{
            aux = movie.getDescription();
        }

        holder.desc.setText(aux);
    }
    //----------------------------------------------------------------------------------------------
    //
    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}