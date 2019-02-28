package com.mekilit.apostlic.apostlicsonglyric.lyrics;

/**
 * Created by Menasi on 4/19/2016.
 */
public class Song {

    private int ID;
    private String Lyric_Title;
    private String Album_id;
    private String Lyric_Text;
    private int _isFav;

    public Song() {
        this._isFav = 0;
    }

    public Song(String _titleAmahric, String _album, String _actualLyric) {

        this.Lyric_Title = _titleAmahric;
        this.Album_id = _album;
        this.Lyric_Text = _actualLyric;
        this._isFav = 0;
    }

    public int get_isFav() {
        return _isFav;
    }

    public String getLyric_Title() {
        return Lyric_Title;
    }

    public String getAlbum_id() {
        return Album_id;
    }

    public String getLyric_Text() {
        return Lyric_Text;

    }

    public int getID() {
        return ID;
    }

}
