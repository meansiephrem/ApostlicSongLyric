package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.lang.reflect.Field;

public class AlbumArt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_art);
        ImageView albumArt = (ImageView) findViewById(R.id.BigAlbumArt);
        Intent intent = getIntent();
        String AlbumID = intent.getStringExtra(Intent.EXTRA_TEXT);
        try {
            String name = AlbumID.toLowerCase();
            Class res = R.drawable.class;
            Field field = res.getField(name);
            int res1 =field.getInt(null);
            albumArt.setImageResource(res1);
        }catch (Exception e)
        {
            albumArt.setImageResource(R.drawable.defultpic);
        }
    }
}
