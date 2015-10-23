package rccorp.musicx.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import rccorp.musicx.Album;
import rccorp.musicx.R;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder>{

    private ArrayList<Album> albums;
    private LayoutInflater albInf;
    private Context mContext;

    public AlbumAdapter(Context c, ArrayList<Album> thealbums){
        albums=thealbums;
        albInf=LayoutInflater.from(c);
        mContext=c;
    }


    @Override
    public AlbumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.album, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Album currAlbum=albums.get(position);
        holder.albView.setText(albums.get(position).getAlbum());
        if(currAlbum.getAlbumart()!=null) {
            Picasso.with(mContext).load(new File(currAlbum.getAlbumart())).fit().into(holder.albartView);

        } else {
            holder.albartView.setImageResource(R.drawable.art);
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView albView;
        public ImageView albartView;


        public ViewHolder(View itemView) {
            super(itemView);
            albView =(TextView)itemView.findViewById(R.id.albumname);
            albartView =(ImageView)itemView.findViewById(R.id.albmart);
        }

    }

    @Override
    public int getItemCount() {
        return albums.size();
    }


   


}