package com.mekilit.apostlic.apostlicsonglyric.appinfo;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mekilit.apostlic.apostlicsonglyric.application.ApostolicSongs;
import com.mekilit.apostlic.apostlicsonglyric.utils.DecodeTask;
import com.mekilit.apostlic.apostlicsonglyric.R;

public class AboutTheApp extends AppCompatActivity {

    ApostolicSongs app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        app = (ApostolicSongs) getApplication();

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.abt_page);

        ImageView   bigPic =        (ImageView)   findViewById(R.id.aboutApp);
        ImageView   logForUs =      (ImageView)   findViewById(R.id.logoForUs);
        ImageButton facebook =      (ImageButton) findViewById(R.id.facebookLink);
        ImageButton web =           (ImageButton) findViewById(R.id.webLink);
        ImageButton telegram =      (ImageButton) findViewById(R.id.telegram);
        TextView    priviacyPolicy = (TextView)   findViewById(R.id.privatePolicy);

        DecodeTask task = new DecodeTask(bigPic, this,null);
        task.execute(R.drawable.about_app_two/* File path to image */);
        DecodeTask task1 = new DecodeTask(logForUs, this,null);
        task1.execute(R.drawable.logo_ver_two/* File path to image */);



        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFBPage();
            }
        });

        web.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToUrl("http://apostolicsong.com");
            }
        });

        priviacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl("https://termsfeed.com/privacy-policy/645845dd6dd3546281505b69c05393dd");
            }
        });

        telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl("https://telegram.me/ApostolicSongsBot");
            }
        });


    }

    private void goToUrl(String addres){
        Uri uri = Uri.parse(addres);
        Intent launchAdders = new Intent();
        launchAdders.addCategory(Intent.CATEGORY_BROWSABLE);
        launchAdders.setAction(Intent.ACTION_VIEW);
        launchAdders.setData(uri);
        startActivity(launchAdders);
    }

    private void openFBPage()
    {Intent intent;
        ApplicationInfo applicationInfo;
        try {
                 applicationInfo= getApplicationContext().getPackageManager()
                .getApplicationInfo("com.facebook.katana",0);
                if(applicationInfo.enabled)
                {
                    intent= new Intent(Intent.ACTION_VIEW,
                  Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/apostolicsongsapp"));
                    startActivity(intent);
                }
        }catch (Exception e)
        {
                goToUrl("http://facebook.com/apostolicsongsapp");

        }
    }
}
