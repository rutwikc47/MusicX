package rccorp.musicx;

//
public class Album  {

    private String album;
    private String albumart;

    public Album(String songAlbum, String songAlbumart){

        album=songAlbum;
        albumart=songAlbumart;
    }

    public String getAlbum(){return album;}
    public String getAlbumart(){return albumart;}
}