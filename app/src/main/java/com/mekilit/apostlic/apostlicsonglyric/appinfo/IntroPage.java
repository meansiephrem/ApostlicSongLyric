package com.mekilit.apostlic.apostlicsonglyric.appinfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import com.mekilit.apostlic.apostlicsonglyric.utils.MyDbHandler;
import com.mekilit.apostlic.apostlicsonglyric.R;
import com.mekilit.apostlic.apostlicsonglyric.mainpage.SelectAll;
import com.mekilit.apostlic.apostlicsonglyric.application.ApostolicSongs;


public class IntroPage extends AppCompatActivity {

    ApostolicSongs app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
