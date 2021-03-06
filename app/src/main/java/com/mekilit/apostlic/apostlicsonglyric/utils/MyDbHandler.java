package com.mekilit.apostlic.apostlicsonglyric.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mekilit.apostlic.apostlicsonglyric.lyrics.Song;
import com.mekilit.apostlic.apostlicsonglyric.album.Album;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class MyDbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 28;
    private static final String DATABASE_NAME = "SongLyric.db";
    private static final String TABLE_ALBUM = "albums";
    private static final String TABLE_LYRIC = "lyric";


    private static final String LYRIC_ID = "_id";
    private static final String LYRIC_TITLE = "lyric_title";
    private static final String LYRIC_ALBUM_ID = "lyric_album_id";
    private static final String LYRIC_ACTUAL_LYRIC = "lyric_actual_lyric";
    private static final String LYRIC_IS_FAV = "lyric_is_fav";


    private static final String ALBUM_ID = "_id";
    private static final String ALBUM_TITLE = "album_title";
    private static final String ALBUM_ARTIST = "album_artist";
    private static final String ALBUM_ART = "album_art";
    private static final String ALBUM_IS_SOLO = "_IsSolo";
    static Context context;

    public MyDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query_album = "CREATE TABLE " + TABLE_ALBUM + "(" +

                ALBUM_ID + " TEXT PRIMARY KEY ,"
                + ALBUM_TITLE + " TEXT NOT NULL, "
                + ALBUM_ARTIST + " TEXT NOT NULL ,"
                + ALBUM_ART + " INTEGER NOT NULL ,"
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
        for (Album album : AssetReader.readAlbumFromJson(context)) {

            ALL_Albums.put(ALBUM_ID, album.getAlbum_ID());
            ALL_Albums.put(ALBUM_TITLE, album.getAlbum_Title());
            ALL_Albums.put(ALBUM_ARTIST, album.getAlbum_Artist());
            ALL_Albums.put(ALBUM_ART, getRes(album.getAlbum_ID()));
            ALL_Albums.put(ALBUM_IS_SOLO, album.getIsSolo());
            db.insert(TABLE_ALBUM, null, ALL_Albums);
       }

        ContentValues ALL_Lyric = new ContentValues();
        for (Song lyric : AssetReader.readLyricFromJson(context)){
            ALL_Lyric.put(LYRIC_TITLE, lyric.getLyric_Title());
            ALL_Lyric.put(LYRIC_ALBUM_ID, lyric.getAlbum_id());
            ALL_Lyric.put(LYRIC_ACTUAL_LYRIC, lyric.getLyric_Text());
            ALL_Lyric.put(LYRIC_IS_FAV, lyric.get_isFav());
            db.insert(TABLE_LYRIC, null, ALL_Lyric);
        }


    }

    public int getRes(String name) {
        int res = 0;

        String name2 = name.toLowerCase();
        res = context.getResources().getIdentifier("@drawable/" + name2, null,
                context.getPackageName());
        if (res == 0)
            return context.getResources().getIdentifier("@drawable/defultpic", null,
                    context.getPackageName());

        return res;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion != newVersion) {
            String query = "drop table " + TABLE_ALBUM;
            String query1 = "drop table " + TABLE_LYRIC;
            db.execSQL(query);
            db.execSQL(query1);
            onCreate(db);
        }


    }

    public String getArtistName(String id) {
        String Artist = null;
        SQLiteDatabase db = getWritableDatabase();
        String[] col = {MyDbHandler.ALBUM_ARTIST};
        Cursor cursor = db.query(MyDbHandler.TABLE_ALBUM, col, MyDbHandler.ALBUM_ID + "= '" + id +
                "'", null, null, null, null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(MyDbHandler.ALBUM_ARTIST);
            Artist = cursor.getString(index);

        }
        cursor.close();
        return Artist;
    }

    public String getAlbumName(String id) {
        String Album = null;
        SQLiteDatabase db = getWritableDatabase();
        String[] col = {MyDbHandler.ALBUM_TITLE};
        Cursor cursor = db.query(MyDbHandler.TABLE_ALBUM, col, MyDbHandler.ALBUM_ID + "= '" + id +
                "'", null, null, null, null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(MyDbHandler.ALBUM_TITLE);
            Album = cursor.getString(index);

        }
        cursor.close();
        return Album;
    }

    public String getAlbumArtForServer(String id) {
        String Album = null;
        SQLiteDatabase db = getWritableDatabase();
        String[] col = {MyDbHandler.ALBUM_ART};
        Cursor cursor = db.query(MyDbHandler.TABLE_ALBUM, col, MyDbHandler.ALBUM_ID + "= '" + id +
                "'", null, null, null, null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(MyDbHandler.ALBUM_ART);
            Album = cursor.getString(index);

        }
        cursor.close();
        return Album;
    }

    public ArrayList<String> SelectAllArtist() {
        ArrayList<String> ret = new ArrayList();
        SQLiteDatabase db = getWritableDatabase();
        int index;

        String[] col = {MyDbHandler.ALBUM_ARTIST};
        Cursor cur = db.query(true, MyDbHandler.TABLE_ALBUM, col, MyDbHandler.ALBUM_IS_SOLO + "=" +
                1, null, null, null, MyDbHandler.ALBUM_ID, null);
        while (cur.moveToNext()) {
            index = cur.getColumnIndex(MyDbHandler.ALBUM_ARTIST);
            ret.add(cur.getString(index));
        }
        cur.close();
        return ret;
    }

    public String CountAlbumF(String Artist) {
        String ret = "";
        ArrayList<String> album = new ArrayList();

        SQLiteDatabase db = getWritableDatabase();
        String[] col = {MyDbHandler.ALBUM_ID};
        Cursor cursor = db.query(true, MyDbHandler.TABLE_ALBUM, col, MyDbHandler.ALBUM_ARTIST +
                "= '" + Artist + "'", null, null, null, null, null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(MyDbHandler.ALBUM_ID);
            album.add(cursor.getString(index));
        }
        if (album.size() == 1)
            ret = "1 አልበም";

        else
            ret = album.size() + " አልበሞች";

        cursor.close();
        return ret;
    }

    public String getAlbumID(String Artist) {
        String ret = "";
        ArrayList<String> album = new ArrayList();

        SQLiteDatabase db = getWritableDatabase();
        String[] col = {MyDbHandler.ALBUM_ID};
        Cursor cursor = db.query(true, MyDbHandler.TABLE_ALBUM, col, MyDbHandler.ALBUM_ARTIST +
                "= '" + Artist + "'", null, null, null, null, null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(MyDbHandler.ALBUM_ID);
            album.add(cursor.getString(index));
        }

        ret = album.get(0);
        cursor.close();
        return ret;
    }

    public ArrayList<String> getAlbum(String Artist) {
        ArrayList<String> ret = new ArrayList();
        SQLiteDatabase db = getWritableDatabase();
        int index;

        String[] col = {MyDbHandler.ALBUM_ID};
        Cursor cur = db.query(true, MyDbHandler.TABLE_ALBUM, col, MyDbHandler.ALBUM_ARTIST + "= '" +
                Artist + "'", null, null, null, null, null);
        while (cur.moveToNext()) {
            index = cur.getColumnIndex(MyDbHandler.ALBUM_ID);
            ret.add(cur.getString(index));
        }
        cur.close();
        return ret;
    }

    public ArrayList<Integer> getFav() {
        ArrayList<Integer> ret = new ArrayList();
        SQLiteDatabase db = getWritableDatabase();
        int index;

        String[] col = {MyDbHandler.LYRIC_ID};
        Cursor cur = db.query(true, MyDbHandler.TABLE_LYRIC, col, MyDbHandler.LYRIC_IS_FAV + "=" + 1
                , null, null, null, MyDbHandler.LYRIC_ALBUM_ID, null);
        while (cur.moveToNext()) {
            index = cur.getColumnIndex(MyDbHandler.LYRIC_ID);
            ret.add(cur.getInt(index));
        }

        cur.close();
        return ret;
    }

    public String getAlbumID(int LyricId) {
        String ret = "";
        ArrayList<String> album = new ArrayList();

        SQLiteDatabase db = getWritableDatabase();
        String[] col = {MyDbHandler.LYRIC_ALBUM_ID};
        Cursor cursor = db.query(true, MyDbHandler.TABLE_LYRIC, col, MyDbHandler.LYRIC_ID + "= " +
                LyricId, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(MyDbHandler.LYRIC_ALBUM_ID);
            album.add(cursor.getString(index));
        }

        ret = album.get(0);

        cursor.close();
        return ret;
    }

    public ArrayList<String> SelectAllhebret() {
        ArrayList<String> ret = new ArrayList();
        SQLiteDatabase db = getWritableDatabase();
        int index;

        String[] col = {MyDbHandler.ALBUM_ID};
        Cursor cur = db.query(true, MyDbHandler.TABLE_ALBUM, col, MyDbHandler.ALBUM_IS_SOLO + "=" +
                0, null, null, null, MyDbHandler.ALBUM_ID, null);
        while (cur.moveToNext()) {
            index = cur.getColumnIndex(MyDbHandler.ALBUM_ID);
            ret.add(cur.getString(index));
        }

        cur.close();
        return ret;
    }

    public String CountSongs(String AlbumID) {
        String ret = "";
        ArrayList<String> album = new ArrayList();

        SQLiteDatabase db = getWritableDatabase();
        String[] col = {MyDbHandler.LYRIC_ID};
        Cursor cursor = db.query(true, MyDbHandler.TABLE_LYRIC, col, MyDbHandler.LYRIC_ALBUM_ID +
                "= '" + AlbumID + "'", null, null, null, null, null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(MyDbHandler.LYRIC_ID);
            album.add(cursor.getString(index));
        }
        if (album.size() == 1)
            ret = "1 መዝሙር";

        else
            ret = album.size() + " መዝሙሮች";

        cursor.close();
        return ret;
    }

    public ArrayList<String> SelectAllSongs(String AlbumID) {

        ArrayList<String> album = new ArrayList();

        SQLiteDatabase db = getWritableDatabase();
        String[] col = {MyDbHandler.LYRIC_ID};
        Cursor cursor = db.query(true, MyDbHandler.TABLE_LYRIC, col, MyDbHandler.LYRIC_ALBUM_ID +
                "= '" + AlbumID + "'", null, null, null, null, null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(MyDbHandler.LYRIC_ID);
            album.add(cursor.getString(index));
        }
        cursor.close();
        return album;
    }

    public String getSongName(String id) {
        String Song = "";
        int LyricID = Integer.parseInt(id);
        SQLiteDatabase db = getWritableDatabase();
        String[] col = {MyDbHandler.LYRIC_TITLE};
        Cursor cursor = db.query(MyDbHandler.TABLE_LYRIC, col, MyDbHandler.LYRIC_ID + "= " + LyricID
                + "", null, null, null, null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(MyDbHandler.LYRIC_TITLE);
            Song = cursor.getString(index);

        }
        cursor.close();
        return Song;
    }

    public boolean isFav(String id) {
        boolean Song = false;
        int LyricID = Integer.parseInt(id);
        SQLiteDatabase db = getWritableDatabase();
        String[] col = {MyDbHandler.LYRIC_IS_FAV};
        Cursor cursor = db.query(MyDbHandler.TABLE_LYRIC, col, MyDbHandler.LYRIC_ID + "= " + LyricID
                + "", null, null, null, null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(MyDbHandler.LYRIC_IS_FAV);
            int fav = cursor.getInt(index);
            Song = fav != 0;
        }

        cursor.close();
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
        Cursor cursor = db.query(MyDbHandler.TABLE_LYRIC, col, MyDbHandler.LYRIC_ID + "= " + id + ""
                , null, null, null, null);
        StringBuilder buffer = new StringBuilder();
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(MyDbHandler.LYRIC_ACTUAL_LYRIC);
            lyric = cursor.getString(index);
            buffer.append(" ").append(lyric);
        }

        cursor.close();
        return buffer.toString();
    }

    public String CountAlL(char x) {//a for album b for songs and c for artists
        String ret = "";
        ArrayList<String> album = new ArrayList();

        SQLiteDatabase db = getWritableDatabase();
        String[] co0 = {MyDbHandler.ALBUM_ID};
        String[] co1 = {MyDbHandler.ALBUM_ARTIST};
        String[] co2 = {MyDbHandler.LYRIC_ID};
        Cursor cursor;
        int index;
        if (x == 'a') {//count all albm
            cursor = db.query(true, MyDbHandler.TABLE_ALBUM, co0, null, null, null, null, null, null);
            index = cursor.getColumnIndex(MyDbHandler.ALBUM_ID);
        } else if (x == 'b') {//count all Songs
            cursor = db.query(true, MyDbHandler.TABLE_LYRIC, co2, null, null, null, null, null, null);
            index = cursor.getColumnIndex(MyDbHandler.LYRIC_ID);
        } else if (x == 'c') {//count all Artists
            cursor = db.query(true, MyDbHandler.TABLE_ALBUM, co1, MyDbHandler.ALBUM_IS_SOLO + "=" + 1,
                    null, null, null, null, null);
            index = cursor.getColumnIndex(MyDbHandler.ALBUM_ARTIST);
        }
        else { //count all FavSongs
            cursor = db.query(true, MyDbHandler.TABLE_LYRIC, co2, MyDbHandler.LYRIC_IS_FAV + "=" + 1,
                    null, null, null, null, null);
            index = cursor.getColumnIndex(MyDbHandler.LYRIC_ID);
        }

        while (cursor.moveToNext()) {
            album.add(cursor.getString(index));
        }

        ret = album.size() + "";

        cursor.close();
        return ret;
    }

    public ArrayList<String> SelectAll() {

        ArrayList<String> album = new ArrayList();

        SQLiteDatabase db = getWritableDatabase();
        String[] col = {MyDbHandler.ALBUM_ID};
        Cursor cursor = db.query(true, MyDbHandler.TABLE_ALBUM, col, null, null, null, null,
                MyDbHandler.ALBUM_ID,
                null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(MyDbHandler.ALBUM_ID);
            album.add(cursor.getString(index));
        }

        cursor.close();
        return album;
    }

    public int getAlbumArt(String id) {
        int Album = -1;
        SQLiteDatabase db = getWritableDatabase();
        String[] col = {MyDbHandler.ALBUM_ART};
        Cursor cursor = db.query(MyDbHandler.TABLE_ALBUM, col, MyDbHandler.ALBUM_ID + "= '" + id +
                "'", null, null, null, null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(MyDbHandler.ALBUM_ART);
            Album = cursor.getInt(index);

        }
        cursor.close();
        return Album;
    }

    public ArrayList<Integer> SearchQuery(String query) {

        ArrayList<Integer> ret = new ArrayList();
        SQLiteDatabase db = getWritableDatabase();
        int index;

        String[] col = {MyDbHandler.LYRIC_ID};
        Cursor cur = db.query(true, MyDbHandler.TABLE_LYRIC, col, MyDbHandler.LYRIC_TITLE + " LIKE '%"
                +query +"%'", null, null, null, null, null);
        while (cur.moveToNext()) {
            index = cur.getColumnIndex(MyDbHandler.LYRIC_ID);
            ret.add(cur.getInt(index));
        }

        cur.close();
        return ret;
    }

    public void InsertNewAlbum(Album album, ArrayList<Song> song) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues ALL_Albums = new ContentValues();

            ALL_Albums.put(ALBUM_ID, album.getAlbum_ID());
            ALL_Albums.put(ALBUM_TITLE, album.getAlbum_Title());
            ALL_Albums.put(ALBUM_ARTIST, album.getAlbum_Artist());
            ALL_Albums.put(ALBUM_ART, album.getAlbum_Art());
            ALL_Albums.put(ALBUM_IS_SOLO,album.getIsSolo());
            db.insert(TABLE_ALBUM, null, ALL_Albums);


        ContentValues ALL_Lyric = new ContentValues();
            for (int i = 0; i<song.size();i++) {
                ALL_Lyric.put(LYRIC_TITLE, song.get(i).getLyric_Title());
                ALL_Lyric.put(LYRIC_ALBUM_ID, song.get(i).getAlbum_id());
                ALL_Lyric.put(LYRIC_ACTUAL_LYRIC, song.get(i).getLyric_Text());
                ALL_Lyric.put(LYRIC_IS_FAV, song.get(i).get_isFav());
                db.insert(TABLE_LYRIC, null, ALL_Lyric);
            }

    }

    public void UpdateLyric(Song newLyric) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(LYRIC_ACTUAL_LYRIC,newLyric.getLyric_Text());

        db.update(MyDbHandler.TABLE_LYRIC, contentValues,
                MyDbHandler.LYRIC_ALBUM_ID + "= '" +newLyric.getAlbum_id()
                +"' AND "+ MyDbHandler.LYRIC_TITLE + "= '" + newLyric.getLyric_Title() +"'", null);
    }
}

