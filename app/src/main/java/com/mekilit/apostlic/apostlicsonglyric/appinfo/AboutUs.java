package com.mekilit.apostlic.apostlicsonglyric.appinfo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.mekilit.apostlic.apostlicsonglyric.application.ApostolicSongs;
import com.mekilit.apostlic.apostlicsonglyric.R;

public class AboutUs extends AppCompatActivity {

    ApostolicSongs app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        app = (ApostolicSongs) getApplication();
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_about_us);
    }
}
