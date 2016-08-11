package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.widget.TextView;

public class Sec_Activity extends AppCompatActivity {

    String[] Name = {"Menasi Ephrem", "Tehut Ephrem","Eshcol Teferra"},
             Email = {"menasiephrem@gmail.com", "tehutephrem@gmail.com","eshcol.teferra@gmail.com"},
             Phone = {"+251912476374", "+251948242726","+251920720821"},
             Church = {"Koteb Netsant Atbeya", "Koteb Netsant Atbeya","Semen Atebeya"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            Slide slide = new Slide();
            slide.setDuration(5000);
            getWindow().setEnterTransition(slide);

        }
        Intent intent = this.getIntent();
        int id = Integer.parseInt(intent.getStringExtra(Intent.EXTRA_TEXT));
        setContentView(R.layout.activity_sec_);
        TextView mName = (TextView) findViewById(R.id.SecName);
        TextView mEmail = (TextView) findViewById(R.id.Email);
        TextView mPhone = (TextView) findViewById(R.id.Phone);
        TextView mChurch = (TextView) findViewById(R.id.Atbeya);
        mName.setText(Name[id]);
        mEmail.setText(Email[id]);
        mPhone.setText(Phone[id]);
        mChurch.setText(Church[id]);
    }
}
