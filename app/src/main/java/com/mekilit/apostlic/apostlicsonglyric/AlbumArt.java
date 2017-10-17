package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.lang.reflect.Field;

public class AlbumArt extends AppCompatActivity {
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_art);
        setTheme(ApostolicSongs.theme);
        ImageView albumArt = (ImageView) findViewById(R.id.BigAlbumArt);
        Intent intent = getIntent();
        String AlbumID = intent.getStringExtra(Intent.EXTRA_TEXT);
        try {
           name = AlbumID.toLowerCase();
            Class res = R.drawable.class;
            Field field = res.getField(name);
            int res1 =field.getInt(null);
            albumArt.setImageResource(res1);
        }catch (Exception e)
        {
            albumArt.setImageBitmap(BitmapFactory.decodeFile(getResources().getString(R.string.path)
                    +name+".jpg"));

        }
    }
}
