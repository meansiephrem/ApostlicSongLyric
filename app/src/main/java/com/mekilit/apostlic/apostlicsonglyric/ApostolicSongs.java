package com.mekilit.apostlic.apostlicsonglyric;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.Toolbar;

/**
 * Created by Menasi on 2/18/2017.
 */

public class ApostolicSongs extends Application {

    public final static String PREF_FILE_NAME = "textSize";
    Toolbar toolbar ;
    private float LyricTextSize;

    static void saveToFile(Context context,String Filename, float FileVlaue)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(Filename,FileVlaue);
        editor.apply();

    }

    static float ReadFromFile(Context context,String Filename, float DefValue)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,
                Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(Filename,DefValue);

    }

    public float getLyricTextSize() {

        return LyricTextSize;
    }

    public void setLyricTextSize(float lyricTextSize)
    {
        LyricTextSize = lyricTextSize;
        saveToFile(getBaseContext(),PREF_FILE_NAME,lyricTextSize);
    }

    @Override
    public void onCreate() {

        setLyricTextSize(ReadFromFile(getBaseContext(),PREF_FILE_NAME,20));



    }




}

