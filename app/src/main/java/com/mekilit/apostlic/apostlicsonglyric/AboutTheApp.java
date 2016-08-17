package com.mekilit.apostlic.apostlicsonglyric;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class AboutTheApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.abt_page);
        ImageView bigPic = (ImageView) findViewById(R.id.abtApp);
        ImageView logForUs = (ImageView) findViewById(R.id.logoForUs);
        DecodeTask task = new DecodeTask(bigPic, this);

        task.execute(R.drawable.abt/* File path to image */);
        DecodeTask task1 = new DecodeTask(logForUs, this);

        task1.execute(R.drawable.logo_for_us/* File path to image */);

    }
}
