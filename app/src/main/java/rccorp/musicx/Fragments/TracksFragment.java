package rccorp.musicx.Fragments;


import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import rccorp.musicx.Adapters.SongAdapter;
import rccorp.musicx.MusicService;
import rccorp.musicx.R;
import rccorp.musicx.Song;


public class TracksFragment extends Fragment{
    private MusicService musicSrv;
    private Intent playIntent;
    private boolean musicBound=false;

    private ArrayList<Song> songList;
    private ListView songView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tracks, container, false);

        ListView songView = (ListView)rootView.findViewById(R.id.tracklistview);
        songList=new ArrayList<Song>();
        getSongList();
        SongAdapter songAdt = new SongAdapter(getActivity(), songList);
        songView.setAdapter(songAdt);
        return rootView;

    }

    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            //get service
            musicSrv = binder.getService();
            //pass list

            musicBound = true;
            musicSrv.setList(songList);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };


    @Override
    public void onStart() {
        super.onStart();
        if(playIntent==null){
            playIntent = new Intent(getActivity(), MusicService.class);
            getActivity().bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            getActivity().startService(playIntent);
        }
    }




    @Override
    public void onDestroy() {
        getActivity().stopService(playIntent);
        musicSrv = null;
        super.onDestroy();
        getActivity().unbindService(musicConnection);
    }






    public void getSongList(){
        ContentResolver musicResolver = getActivity().getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri albumUri=MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor artCursor=musicResolver.query(albumUri,null,null,null,null);
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        if(musicCursor!=null && musicCursor.moveToFirst() && artCursor!=null && artCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int albumartColumn=artCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM_ART);


            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String thistrackalbumart=artCursor.getString(albumartColumn);

                songList.add(new Song(thisId, thisTitle, thisArtist,thistrackalbumart));
                Collections.sort(songList, new Comparator<Song>() {
                    public int compare(Song a, Song b) {
                        return a.getTitle().compareTo(b.getTitle());

                    }
                });
            }
            while (musicCursor.moveToNext());
            while (artCursor.moveToNext());

            musicCursor.close();
            artCursor.close();
        }

    }





}
