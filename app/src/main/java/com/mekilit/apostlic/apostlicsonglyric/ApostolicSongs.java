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
    private final static String PREF_THEME = "pref_theme";
    public static int color= 0xFFCCCCCC;
    public  static int theme = R.style.AppTheme;
    public final int red =        0xffF44336;
    public final int pink =       0xffE91E63;
    public final int Purple =     0xff9C27B0;
    public final int DeepPurple = 0xff673AB7;
    public final int Indigo =     0xff3F51B5;
    public final int Blue =       0xff2196F3;
    public final int LightBlue =  0xff03A9F4;
    public final int Cyan =       0xff00BCD4;
    public final int Teal =       0xff009688;
    public final int Green =      0xff4CAF50;
    public final int LightGreen = 0xff8BC34A;
    public final int Lime =       0xffCDDC39;
    public final int Yellow =     0xffFFEB3B;
    public final int Amber =      0xffFFC107;
    public final int Orange =     0xffFF9800;
    public final int DeepOrange = 0xffFF5722;
    public final int Brown =      0xff795548;
    public final int Grey =       0xff9E9E9E;
    public final int BlueGray =   0xff607D8B;
    public final int Black =      0xff000000;
    public final int White =      0xffffffff;
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

    static void saveTheme(Context context, Integer FileValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_THEME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PREF_THEME,FileValue);
        editor.apply();
    }

    static int readTheme(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_THEME,
                Context.MODE_PRIVATE);
        return sharedPreferences.getInt(PREF_THEME,0xffCCCCCC);

    }

    @Override
    public void onCreate() {

        setLyricTextSize(ReadFromFile(getBaseContext(),PREF_TEXT_FILE_NAME,20+""));
        setSyncedRawStr("");
        setUpdateAlbum(ReadFromFile(getBaseContext(),PREF_UPDATE_ALBUM_LIST,"-1"));
        this.color =readTheme(getApplicationContext());
        this.setColorTheme();
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

    public void setColorTheme(){
        switch (this.color){

            case red:
                this.theme = R.style.AppTheme_red;
                break;
            case pink:
                this.theme=R.style.AppTheme_pink;
                break;
            case DeepPurple:
                this.theme=R.style.AppTheme_violet;
                break;
            case Blue:
                this.theme=R.style.AppTheme_blue;
                break;
            case LightBlue:
                this.theme=R.style.AppTheme_skybule;
                break;
            case Green:
                this.theme=R.style.AppTheme_green;
                break;
            case Grey:
                this.theme=R.style.AppTheme_grey;
                break;
            case Brown:
                this.theme=R.style.AppTheme_brown;
                break;

            case Yellow:
                this.theme=R.style.AppTheme_Yellow;
                break;

 /*           case Purple:
                this.theme=R.style.AppTheme_P;
                break;

            case Indigo:
                this.theme=R.style.AppTheme_Indigo;
                break;

            case Cyan:
                this.theme=R.style.AppTheme_Cyan;
                break;

           case Teal:
                this.theme=R.style.AppTheme_Teal;
                break;

            case Lime:
                this.theme=R.style.AppTheme_Lime;
                break;

   */

            case Amber:
                this.theme=R.style.AppTheme_Amber;
                break;

            case LightGreen:
                this.theme=R.style.AppTheme_LightGreen;
                break;

            case Orange:
                this.theme=R.style.AppTheme_Orange;
                break;

            case DeepOrange:
                this.theme=R.style.AppTheme_DeepOrange;
                break;

            case BlueGray:
                this.theme=R.style.AppTheme_BlueGray;
                break;

            case Black:
                this.theme=R.style.AppTheme;
                this.color = 0xffCCCCCC;
                break;

            case White:
                this.theme=R.style.AppTheme_White;
                break;

            default:
                this.theme=R.style.AppTheme;
                break;
        }

    }



}

