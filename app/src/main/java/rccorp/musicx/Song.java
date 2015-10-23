package rccorp.musicx;


import android.database.Cursor;

public class Song {

    private long id;
    private String title;
    private String artist;
    private String albumart;


    public Song(long songID, String songTitle, String songArtist,String trackalbumart){
        id=songID;
        title=songTitle;
        artist=songArtist;
        albumart=trackalbumart;

    }

    public Song(Cursor mediaCursor, Cursor albumCursor) {

    }

    public long getId(){return id;}
    public String getTitle(){return title;}
    public String getArtist(){return artist;}
    public String getAlbumart2(){return albumart;}



}
