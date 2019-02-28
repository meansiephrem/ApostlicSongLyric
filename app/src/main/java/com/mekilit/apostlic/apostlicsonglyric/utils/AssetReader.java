package com.mekilit.apostlic.apostlicsonglyric.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mekilit.apostlic.apostlicsonglyric.album.Album;
import com.mekilit.apostlic.apostlicsonglyric.album.AlbumArray;
import com.mekilit.apostlic.apostlicsonglyric.lyrics.LyricsArray;
import com.mekilit.apostlic.apostlicsonglyric.lyrics.Song;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AssetReader {
    public static Album[] readAlbumFromJson(Context context) {
        String json = "[]";
        Gson gson = new Gson();
        try {
            InputStream is = context.getAssets().open("Album.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return gson.fromJson(json, Album[].class);
    }

    public static Song[] readLyricFromJson(Context context) {
        String json = "[]";
        Gson gson = new Gson();
        try {
            InputStream is = context.getAssets().open("Lyrics.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return gson.fromJson(json, Song[].class);
    }
}
