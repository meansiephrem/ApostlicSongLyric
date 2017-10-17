package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.turkialkhateeb.materialcolorpicker.ColorChooserDialog;
import com.turkialkhateeb.materialcolorpicker.ColorListener;

public class Settings extends AppCompatActivity {

    protected ApostolicSongs app;
    RadioGroup textSizeRg;
    RadioButton textSizeSmall;
    RadioButton textSizeMid;
    RadioButton textSizeBig;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ApostolicSongs.theme);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appBar);
        toolbar.setTitle(R.string.settings);
        toolbar.setBackgroundColor(app.color);

        textSizeRg = (RadioGroup) findViewById(R.id.rg_text_size);
        textSizeBig = (RadioButton) findViewById(R.id.rb_text_big);
        textSizeMid = (RadioButton) findViewById(R.id.rb_text_mid);
        textSizeSmall = (RadioButton) findViewById(R.id.rb_text_small);

        button = (Button) findViewById(R.id.button_color);
        colorize();


        app = (ApostolicSongs) getApplication();


        float size = app.getLyricTextSize();
        if (size == 16) {
            textSizeSmall.setChecked(true);
        } else if (size == 20) {
            textSizeSmall.setChecked(true);
        } else if (size == 24) {
            textSizeSmall.setChecked(true);
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorChooserDialog dialog = new ColorChooserDialog(Settings.this);
                dialog.setTitle("Select");
                dialog.setColorListener(new ColorListener() {

                    @Override
                    public void OnColorClick(View v, int color) {
                        colorize();
                        app.color = color;
                        app.setColorTheme();
                        app.saveTheme(Settings.this,color);
                        Intent intent = new Intent(Settings.this, SelectAll.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
        });

        textSizeRg.setOnCheckedChangeListener(

                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) findViewById(checkedId);
                        if (radioButton.getId() == R.id.rb_text_big) {
                            app.setLyricTextSize(24 + "");
                        } else if (radioButton.getId() == R.id.rb_text_mid) {
                            app.setLyricTextSize(20 + "");
                        } else if (radioButton.getId() == R.id.rb_text_small) {
                            app.setLyricTextSize(16 + "");
                        }
                    }
                }

        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        float size = app.getLyricTextSize();

        if (size == 16) {
            textSizeSmall.setChecked(true);
        } else if (size == 20) {
            textSizeMid.setChecked(true);
        } else if (size == 24) {
            textSizeBig.setChecked(true);
        }


    }

    private void colorize() {
        ShapeDrawable d = new ShapeDrawable(new OvalShape());
        d.setBounds(58, 58, 58, 58);
        d.getPaint().setStyle(Paint.Style.FILL);
        d.getPaint().setColor(app.color);
        button.setBackground(d);
    }

}
