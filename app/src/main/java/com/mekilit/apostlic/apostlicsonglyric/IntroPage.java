package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class IntroPage extends AppCompatActivity {

    ApostolicSongs app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_page);
        MyDbHandler helper = new MyDbHandler(getApplicationContext());

        helper.getReadableDatabase();
        int DISPLAY_LENGTH = 1500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(IntroPage.this, SelectAll.class);
                startActivity(intent);
                finish();
            }
        }, DISPLAY_LENGTH);


    }


}
