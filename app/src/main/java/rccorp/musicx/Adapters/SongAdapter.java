package rccorp.musicx.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import rccorp.musicx.MusicService;
import rccorp.musicx.R;
import rccorp.musicx.Song;

public class SongAdapter extends BaseAdapter{

    private MusicService musicSrv;

    private ArrayList<Song> songs;
    private LayoutInflater songInf;
    private Context mContext;


    public SongAdapter(Context c, ArrayList<Song> theSongs){
        songs=theSongs;
        songInf=LayoutInflater.from(c);
        mContext=c;
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout songLay = (RelativeLayout)songInf.inflate
                (R.layout.song, parent, false);
        //get title and artist views
        TextView songView = (TextView)songLay.findViewById(R.id.song_title);
        TextView artistView = (TextView)songLay.findViewById(R.id.song_artist);
        ImageView trackalbmart=(ImageView)songLay.findViewById(R.id.songalbumart);
        //get song using position
        Song currSong = songs.get(position);
        //get title and artist strings
        songView.setText(currSong.getTitle());
        artistView.setText(currSong.getArtist());
        if(currSong.getAlbumart2()!=null) {
            Picasso.with(mContext).load(new File(currSong.getAlbumart2())).into(trackalbmart);

        } else {
            trackalbmart.setImageResource(R.drawable.art5);
        }
        //set position as tag
        songLay.setTag(position);

        return songLay;
    }



}
