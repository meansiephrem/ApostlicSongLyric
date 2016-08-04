package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class IntroPage extends AppCompatActivity {

    private final int DISPLAY_LENGTH = 1500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_page);
        MyDbHandler helper = new MyDbHandler(getApplicationContext(), null, null, 1);
        helper.getReadableDatabase();
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
