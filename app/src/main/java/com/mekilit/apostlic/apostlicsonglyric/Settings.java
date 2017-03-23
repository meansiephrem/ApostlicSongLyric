package com.mekilit.apostlic.apostlicsonglyric;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Settings extends AppCompatActivity {

    protected ApostolicSongs app;
    RadioGroup textSizeRg;
    RadioButton textSizeSmall;
    RadioButton textSizeMid;
    RadioButton textSizeBig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.appBar);
        toolbar.setTitle(R.string.settings);
        textSizeRg = (RadioGroup) findViewById(R.id.rg_text_size);
        textSizeBig = (RadioButton) findViewById(R.id.rb_text_big);
        textSizeMid = (RadioButton) findViewById(R.id.rb_text_mid);
        textSizeSmall = (RadioButton) findViewById(R.id.rb_text_small);

        app = (ApostolicSongs)getApplication();




        float size = app.getLyricTextSize();
        if(size==16)
        {
         textSizeSmall.setChecked(true);
        }
        else  if(size==20)
        {
            textSizeSmall.setChecked(true);
        }else if(size==24)
        {
            textSizeSmall.setChecked(true);
        }





        textSizeRg.setOnCheckedChangeListener(

                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) findViewById(checkedId);
                        if(radioButton.getId()==R.id.rb_text_big)
                        {
                         app.setLyricTextSize(24+"");
                        }
                        else if(radioButton.getId()==R.id.rb_text_mid)
                        {
                         app.setLyricTextSize(20+"");
                        }
                        else if(radioButton.getId()==R.id.rb_text_small)
                        {
                            app.setLyricTextSize(16+"");
                        }
                    }
                }

        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        float size = app.getLyricTextSize();

        if(size==16)
        {
            textSizeSmall.setChecked(true);
        }
        else  if(size==20)
        {
            textSizeMid.setChecked(true);
        }else if(size==24)
        {
            textSizeBig.setChecked(true);
        }
    }
}
