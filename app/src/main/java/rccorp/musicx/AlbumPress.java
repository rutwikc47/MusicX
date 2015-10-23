package rccorp.musicx;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import rccorp.musicx.Adapters.AlbumPressedAdapter;
import rccorp.musicx.Fragments.AlbumFragment;

public class AlbumPress extends Activity {

    private ArrayList<Song> songList;
    private ListView songView;
    private String mAlbumName;
    private String mAlbumArt;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_press);
        Context c=mContext;

        mAlbumName = getIntent().getStringExtra(AlbumFragment.INTENT_KEY_ALBUM_NAME);
        mAlbumArt=getIntent().getStringExtra(AlbumFragment.INTENT_KEY_ALBUM_ART);

        ListView songsView = (ListView)findViewById(R.id.albmpressdlist);
        songList=new ArrayList<Song>();
        View header=(View)getLayoutInflater().inflate(R.layout.header,null);
        final TextView albumpname=(TextView)header.findViewById(R.id.albmpname);
        final ImageView albumpart=(ImageView)header.findViewById(R.id.albmpart);
        albumpname.setText(mAlbumName);
        if (mAlbumArt!=null){
            Picasso.with(mContext).load(mAlbumArt);
            Picasso.with(mContext).load(new File(mAlbumArt)).into(albumpart);
        }else {
            albumpart.setImageResource(R.drawable.art);
        }

        songsView.addHeaderView(header);
        getSongList();
        AlbumPressedAdapter songAdt = new AlbumPressedAdapter(this, songList);
        songsView.setAdapter(songAdt);



    }



    public void getSongList(){
        ContentResolver musicResolver = this.getContentResolver();
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
            int albumpartColumn=artCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM_ART);



            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String thisalbum=null;
                String thisalbumpart=artCursor.getString(albumpartColumn);






                songList.add(new Song(thisId, thisTitle, thisArtist,thisalbumpart));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.animator.ptoalbum, R.animator.ptoalbum2);

    }
}
