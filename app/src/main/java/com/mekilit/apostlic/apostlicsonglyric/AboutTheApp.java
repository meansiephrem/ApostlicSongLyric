package com.mekilit.apostlic.apostlicsonglyric;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class AboutTheApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.abt_page);
        ImageView bigPic = (ImageView) findViewById(R.id.aboutApp);
        ImageView logForUs = (ImageView) findViewById(R.id.logoForUs);
        DecodeTask task = new DecodeTask(bigPic, this,null);

        task.execute(R.drawable.about_app_two/* File path to image */);
        DecodeTask task1 = new DecodeTask(logForUs, this,null);

        task1.execute(R.drawable.logo_ver_two/* File path to image */);

    }
}
