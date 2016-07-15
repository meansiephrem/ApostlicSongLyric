package com.mekilit.apostlic.apostlicsonglyric;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SongLyric.db";
    private static final String TABLE_ALBUM = "albums";
    private static final String TABLE_LYRIC = "lyric";

    private static final String LYRIC_ID = "lyric_id";
    private static final String LYRIC_TITLE = "lyric_title";
    private static final String LYRIC_ALBUM_ID = "lyric_album_id";
    private static final String LYRIC_ACTUAL_LYRIC = "lyric_actual_lyric";
    private static final String LYRIC_IS_FAV = "lyric_is_fav";


    private static final String ALBUM_ID = "album_id";
    private static final String ALBUM_TITLE = "album_title";
    private static final String ALBUM_ARTIST = "album_artist";
    private static final String ALBUM_ART = "album_art";
    private static final String ALBUM_IS_SOLO = "_IsSolo";

    public MyDbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query_album = "CREATE TABLE " + TABLE_ALBUM + "(" +

                ALBUM_ID + " TEXT PRIMARY KEY ,"
                + ALBUM_TITLE + " TEXT NOT NULL, "
                + ALBUM_ARTIST + " TEXT NOT NULL ,"
                + ALBUM_ART + " TEXT NOT NULL ,"
                + ALBUM_IS_SOLO + " INTEGER  NOT NULL "
                + ");";
        db.execSQL(query_album);

        String query_lyric = "CREATE TABLE " + TABLE_LYRIC + "("

                + LYRIC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + LYRIC_TITLE + " TEXT NOT NULL ,"
                + LYRIC_ALBUM_ID + " TEXT NOT NULL, "
                + LYRIC_IS_FAV + " INTEGER NOT NULL ,"
                + LYRIC_ACTUAL_LYRIC + " TEXT NOT NULL "
                + ");";
        db.execSQL(query_lyric);


        ContentValues ALL_Albums = new ContentValues();
        for (int i =0 ; i<Album.insertAlbum.length; i++ )
        {
            ALL_Albums.put(ALBUM_ID,Album.insertAlbum[i].getAlbum_id());
            ALL_Albums.put(ALBUM_TITLE,Album.insertAlbum[i].getAlbum_Title());
            ALL_Albums.put(ALBUM_ARTIST,Album.insertAlbum[i].getAlbum_Artist());
            ALL_Albums.put(ALBUM_ART,Album.insertAlbum[i].getAlbum_Art());
            ALL_Albums.put(ALBUM_IS_SOLO,Album.insertAlbum[i].get_isSolo());
            db.insert(TABLE_ALBUM,null,ALL_Albums);
        }

        ContentValues ALL_Lyric = new ContentValues();
        for (int i =0 ; i<Song.InsertQuery.length; i++ )
        {
            ALL_Lyric.put(LYRIC_TITLE, Song.InsertQuery[i].get_title());
            ALL_Lyric.put(LYRIC_ALBUM_ID,Song.InsertQuery[i].get_albumId());
            ALL_Lyric.put(LYRIC_ACTUAL_LYRIC,Song.InsertQuery[i].get_actualLyric());
            ALL_Lyric.put(LYRIC_IS_FAV, Song.InsertQuery[i].get_isFav());
            db.insert(TABLE_LYRIC, null, ALL_Lyric);
        }

   

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String getArtistName(String id) {
        String Artist = null;
        SQLiteDatabase db = getWritableDatabase();
        String[] col = {MyDbHandler.ALBUM_ARTIST};
        Cursor cursor = db.query(MyDbHandler.TABLE_ALBUM, col, MyDbHandler.ALBUM_ID + "= '" + id + "'", null, null, null, null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(MyDbHandler.ALBUM_ARTIST);
            Artist = cursor.getString(index);

        }
        return Artist;
    }

    public String getAlbumName(String id) {
        String Album = null;
        SQLiteDatabase db = getWritableDatabase();
        String[] col = {MyDbHandler.ALBUM_TITLE};
        Cursor cursor = db.query(MyDbHandler.TABLE_ALBUM, col, MyDbHandler.ALBUM_ID + "= '" + id + "'", null, null, null, null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(MyDbHandler.ALBUM_TITLE);
            Album = cursor.getString(index);

        }
        return Album;
    }

    public ArrayList<String> SelectAllArtist() {
        ArrayList<String> ret = new ArrayList();
        SQLiteDatabase db = getWritableDatabase();
        int index;

        String[] col = {MyDbHandler.ALBUM_ARTIST};
        Cursor cur = db.query(true, MyDbHandler.TABLE_ALBUM, col,MyDbHandler.ALBUM_IS_SOLO+"="+1, null, null, null, null, null);
        while (cur.moveToNext()) {
            index = cur.getColumnIndex(MyDbHandler.ALBUM_ARTIST);
            ret.add(cur.getString(index));
        }

        return ret;
    }

    public String CountAlbumF(String Artist) {
        String ret ="";
        ArrayList<String> album = new ArrayList();

        SQLiteDatabase db = getWritableDatabase();
        String[] col = {MyDbHandler.ALBUM_ID};
        Cursor cursor = db.query(true,MyDbHandler.TABLE_ALBUM, col, MyDbHandler.ALBUM_ARTIST + "= '" +Artist + "'", null, null, null, null,null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(MyDbHandler.ALBUM_ID);
            album.add(cursor.getString(index));
        }
        if (album.size()==1)
            ret="1 አልበም";

        else
            ret= album.size()+" አልበሞች";

        return ret;
    }

    public String getAlbumID(String Artist){
        String ret ="";
        ArrayList<String> album = new ArrayList();

        SQLiteDatabase db = getWritableDatabase();
        String[] col = {MyDbHandler.ALBUM_ID};
        Cursor cursor = db.query(true,MyDbHandler.TABLE_ALBUM, col, MyDbHandler.ALBUM_ARTIST + "= '" +Artist + "'", null, null, null, null,null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(MyDbHandler.ALBUM_ID);
            album.add(cursor.getString(index));
        }

        ret = album.get(0);
        return ret;
    }

    public ArrayList<String> getAlbum(String Artist) {
        ArrayList<String> ret = new ArrayList();
        SQLiteDatabase db = getWritableDatabase();
        int index;

        String[] col = {MyDbHandler.ALBUM_ID};
        Cursor cur = db.query(true, MyDbHandler.TABLE_ALBUM, col,MyDbHandler.ALBUM_ARTIST+"= '"+Artist+"'", null, null, null, null, null);
        while (cur.moveToNext()) {
            index = cur.getColumnIndex(MyDbHandler.ALBUM_ID);
            ret.add(cur.getString(index));
        }

        return ret;
    }

    public ArrayList<Integer> getFav() {
        ArrayList<Integer> ret = new ArrayList();
        SQLiteDatabase db = getWritableDatabase();
        int index;

        String[] col = {MyDbHandler.LYRIC_ID};
        Cursor cur = db.query(true, MyDbHandler.TABLE_LYRIC, col,MyDbHandler.LYRIC_IS_FAV+"="+1, null, null, null, null, null);
        while (cur.moveToNext()) {
            index = cur.getColumnIndex(MyDbHandler.LYRIC_ID);
            ret.add(cur.getInt(index));
        }

        return ret;
    }

    public String getAlbumID(int LyricId){
        String ret ="";
        ArrayList<String> album = new ArrayList();

        SQLiteDatabase db = getWritableDatabase();
        String[] col = {MyDbHandler.LYRIC_ALBUM_ID};
        Cursor cursor = db.query(true,MyDbHandler.TABLE_LYRIC, col, MyDbHandler.LYRIC_ID + "= " +LyricId , null, null, null, null,null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(MyDbHandler.LYRIC_ALBUM_ID);
            album.add(cursor.getString(index));
        }

        ret = album.get(0);
        return ret;
    }

    public ArrayList<String> SelectAllhebret() {
        ArrayList<String> ret = new ArrayList();
        SQLiteDatabase db = getWritableDatabase();
        int index;

        String[] col = {MyDbHandler.ALBUM_ID};
        Cursor cur = db.query(true, MyDbHandler.TABLE_ALBUM, col,MyDbHandler.ALBUM_IS_SOLO+"="+0, null, null, null, null, null);
        while (cur.moveToNext()) {
            index = cur.getColumnIndex(MyDbHandler.ALBUM_ID);
            ret.add(cur.getString(index));
        }

        return ret;
    }

    public String CountSongs(String AlbumID) {
        String ret ="";
        ArrayList<String> album = new ArrayList();

        SQLiteDatabase db = getWritableDatabase();
        String[] col = {MyDbHandler.LYRIC_ID};
        Cursor cursor = db.query(true,MyDbHandler.TABLE_LYRIC, col, MyDbHandler.LYRIC_ALBUM_ID+ "= '" +AlbumID + "'", null, null, null, null,null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(MyDbHandler.LYRIC_ID);
            album.add(cursor.getString(index));
        }
        if (album.size()==1)
            ret="1 መዝሙር";

        else
            ret= album.size()+" መዝሙሮች";

        return ret;
    }

    public ArrayList<String> SelectAllSongs(String AlbumID) {

        ArrayList<String> album = new ArrayList();

        SQLiteDatabase db = getWritableDatabase();
        String[] col = {MyDbHandler.LYRIC_ID};
        Cursor cursor = db.query(true,MyDbHandler.TABLE_LYRIC, col, MyDbHandler.LYRIC_ALBUM_ID+ "= '" +AlbumID + "'", null, null, null, null,null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(MyDbHandler.LYRIC_ID);
            album.add(cursor.getString(index));
        }

        return album;
    }

    public String getSongName(String id)  {
        String Song = "";
        int LyricID = Integer.parseInt(id);
        SQLiteDatabase db = getWritableDatabase();
        String[] col = {MyDbHandler.LYRIC_TITLE};
        Cursor cursor = db.query(MyDbHandler.TABLE_LYRIC, col, MyDbHandler.LYRIC_ID + "= " + LyricID + "", null, null, null, null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(MyDbHandler.LYRIC_TITLE);
            Song = cursor.getString(index);

        }
        return Song;
    }

    public boolean isFav(String id) {
        boolean Song = false;
        int LyricID = Integer.parseInt(id);
        SQLiteDatabase db = getWritableDatabase();
        String[] col = {MyDbHandler.LYRIC_IS_FAV};
        Cursor cursor = db.query(MyDbHandler.TABLE_LYRIC, col, MyDbHandler.LYRIC_ID + "= " + LyricID + "", null, null, null, null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(MyDbHandler.LYRIC_IS_FAV);
            int fav = cursor.getInt(index);
            if (fav==0)
                Song = false;
            else
                Song = true;
        }
        return Song;
    }

    public void ChangeFavStat(boolean currentStat, int id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (currentStat)
            contentValues.put(MyDbHandler.LYRIC_IS_FAV, 0);
        else
            contentValues.put(MyDbHandler.LYRIC_IS_FAV, 1);


        db.update(MyDbHandler.TABLE_LYRIC, contentValues, MyDbHandler.LYRIC_ID + "= " + id, null);
    }

    public String getLyric(int id) {
        String lyric;
        SQLiteDatabase db = getWritableDatabase();
        String[] col = {MyDbHandler.LYRIC_ACTUAL_LYRIC};
        Cursor cursor = db.query(MyDbHandler.TABLE_LYRIC, col, MyDbHandler.LYRIC_ID + "= " + id + "", null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(MyDbHandler.LYRIC_ACTUAL_LYRIC);
            lyric = cursor.getString(index);
            buffer.append(" " + lyric);
        }
        return buffer.toString();
    }

}

