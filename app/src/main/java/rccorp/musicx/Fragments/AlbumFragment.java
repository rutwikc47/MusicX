package rccorp.musicx.Fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import rccorp.musicx.Adapters.AlbumAdapter;
import rccorp.musicx.Album;
import rccorp.musicx.AlbumPress;
import rccorp.musicx.R;
import rccorp.musicx.RecyclerItemClickListener;

public class AlbumFragment extends Fragment {
    public static final String INTENT_KEY_ALBUM_NAME = "intent_key_album_name";
    public static final String INTENT_KEY_ALBUM_ART="intent_key_album_art";
    private ArrayList<Album> albumList;
    private ListView albumView;
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private Context context;

    protected RecyclerView.LayoutManager mLayoutManager;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context c =context;

        View rootView = inflater.inflate(R.layout.fragment_album, container, false);

        RecyclerView albumView=(RecyclerView)rootView.findViewById(R.id.recyclerView);



        albumView.setLayoutManager(new GridLayoutManager(rootView.getContext(), 2));

        albumList=new ArrayList<Album>();
        getAlbumList();

        AlbumAdapter albAdt=new AlbumAdapter(getActivity(), albumList);
        albumView.setAdapter(albAdt);
        albumView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent i = new Intent(getActivity(), AlbumPress.class);
                        Album album = albumList.get(position);
                        i.putExtra(INTENT_KEY_ALBUM_NAME, album.getAlbum());
                        i.putExtra(INTENT_KEY_ALBUM_ART, album.getAlbumart());
                        startActivity(i);
                        getActivity().overridePendingTransition(R.animator.albumtop,R.animator.albumtop2);
                    }
                })
        );


        return rootView;


    }



    public void getAlbumList(){
        ContentResolver musicResolver = getActivity().getContentResolver();
        Uri musicUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;

        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        if(musicCursor!=null && musicCursor.moveToFirst()){
            int albumColumn=musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM);
            int albumartColumn=musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM_ART);
            do{
                String thisAlbum=musicCursor.getString(albumColumn);
                String thisAlbumart=musicCursor.getString(albumartColumn);
                albumList.add(new Album(thisAlbum, thisAlbumart ));
                Collections.sort(albumList, new Comparator<Album>() {
                    public int compare(Album a, Album b) {
                        return a.getAlbum().compareTo(b.getAlbum());

                    }
                });
            }
            while (musicCursor.moveToNext());
            musicCursor.close();
        }

    }






}
