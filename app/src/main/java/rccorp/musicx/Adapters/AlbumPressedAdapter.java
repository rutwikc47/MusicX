package rccorp.musicx.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import rccorp.musicx.R;
import rccorp.musicx.Song;

public class AlbumPressedAdapter extends BaseAdapter{

    private ArrayList<Song> songs;
    private LayoutInflater songInf;
    private Context mContext;


    public AlbumPressedAdapter(Context c, ArrayList<Song> theSongs){
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
                (R.layout.albumpressedelement, parent, false);
        //get title and artist views
        TextView songsView = (TextView)songLay.findViewById(R.id.trackname);
        TextView artistsView = (TextView)songLay.findViewById(R.id.albartisname);

        //get song using position
        Song currSong = songs.get(position);
        //get title and artist strings
        songsView.setText(currSong.getTitle());
        artistsView.setText(currSong.getArtist());
        

        //set position as tag
        songLay.setTag(position);

        return songLay;
    }
}

