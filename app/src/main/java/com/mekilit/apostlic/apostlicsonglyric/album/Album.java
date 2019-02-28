package com.mekilit.apostlic.apostlicsonglyric.album;

/**
 * Created by Menasi on 6/18/2016.
 */
public class Album {

    private String Album_ID;
    private String Album_Title;
    private String Album_Artist;
    private String Album_Art;
    private String _ID;
    private int isSolo;

    public Album(String album_id, String album_Title, String album_Artist, int _isSolo) {
        Album_ID = album_id;
        Album_Title = album_Title;
        Album_Artist = album_Artist;
        Album_Art= album_id;
        this.isSolo = _isSolo;
    }

    public Album(String album_id, String album_Title, String album_Artist,String album_Art, int _isSolo) {
        Album_ID = album_id;
        Album_Title = album_Title;
        Album_Artist = album_Artist;
        Album_Art= album_Art;
        this.isSolo = _isSolo;
    }

    public String getAlbum_Art() {
        return Album_Art;
    }

    public String getAlbum_ID() {
        return Album_ID;
    }

    public String getAlbum_Title() {
        return Album_Title;
    }

    public String getAlbum_Artist() {
        return Album_Artist;
    }

    public int getIsSolo() {
        return isSolo;
    }

    public String get_ID() { return _ID; }

    public void set_ID(String _ID) { this._ID = _ID; }


}
