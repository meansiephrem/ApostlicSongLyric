package com.mekilit.apostlic.apostlicsonglyric;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Menasi on 2/18/2017.
 */

public class ApostolicSongs extends Application {

    private final static String PREF_TEXT_FILE_NAME = "textSize";
    private final static String PREF_SYNCED_ALBUMS = "syncedAlbums";
    private final static String PREF_UPDATE_ALBUM_LIST = "updateAlbums";
    public boolean [] syncedAlbum  = new boolean[100];

    private float LyricTextSize;
    private String syncedRawStr="";



    private String updateAlbum;

    static void saveToFile(Context context, String Filename, String FileVlaue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Filename,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Filename,FileVlaue);
        editor.apply();

    }

    static String ReadFromFile(Context context, String Filename, String DefValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Filename,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(Filename,DefValue);

    }

    @Override
    public void onCreate() {

        setLyricTextSize(ReadFromFile(getBaseContext(),PREF_TEXT_FILE_NAME,20+""));
        setSyncedRawStr("");
        setUpdateAlbum(ReadFromFile(getBaseContext(),PREF_UPDATE_ALBUM_LIST,"-1"));
        super.onCreate();
    }

    public void writeSyncedAlbum(String albumNO) {

        int intAlbumNO=Integer.parseInt(albumNO);
        boolean temp =syncedAlbum[intAlbumNO];
        if(!temp)
        {
            syncedRawStr+=":"+albumNO;
            saveToFile(getBaseContext(),PREF_SYNCED_ALBUMS,syncedRawStr);
            syncedAlbum[intAlbumNO]=true;
        }
    }

    public void setSyncedRawStr(String syncedRawStr) {
        for (int j =0 ; j<syncedAlbum.length;j++)
        {
            syncedAlbum[j]=false;
        }
        this.syncedRawStr= ReadFromFile(getBaseContext(),PREF_SYNCED_ALBUMS,syncedRawStr);
        String[] tempStrArray =this.syncedRawStr.split(":");
        for (int i =1 ; i<tempStrArray.length;i++)
        {
            syncedAlbum[Integer.parseInt(tempStrArray[i])]=true;
        }
    }

    public boolean alredaySyncedAlbum(int albumNO)
    {
        return syncedAlbum[albumNO];
    }

    public float getLyricTextSize() {

        return LyricTextSize;
    }

    public void setLyricTextSize(String lyricTextSize) {
        LyricTextSize = Float.parseFloat(lyricTextSize);
        saveToFile(getBaseContext(),PREF_TEXT_FILE_NAME,lyricTextSize);
    }

    public String getUpdateAlbum() {
        return updateAlbum;
    }

    public void setUpdateAlbum(String updateAlbum) {
        this.updateAlbum = updateAlbum;
        saveToFile(getBaseContext(),PREF_UPDATE_ALBUM_LIST,updateAlbum);
    }

}

