package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Lyric extends ActionBarActivity {
    private static String SECRET_MESSAGE="Menasi Ephrem 0912476374\n Eshcol Tefera 0920720821";
    Toolbar toolbar ;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyric);
        final MyDbHandler helper = new MyDbHandler(this,null,null,1);
        Intent inten = getIntent();
       id = inten.getStringExtra(Intent.EXTRA_TEXT);
        toolbar= (Toolbar) findViewById(R.id.lyapp);
        final int lryicID=Integer.parseInt(id);
        String albumId= helper.getAlbumID(lryicID);

            toolbar.setTitle(helper.getSongName(id));
            toolbar.setSubtitle(helper.getArtistName(albumId));

        setSupportActionBar(toolbar);

        boolean isFav =helper.isFav(id);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (!isFav)
        {
            fab.setImageDrawable(getResources().getDrawable(R.drawable.btn_star_big_off));
            fab.setSelected(false);
        }
        else
        {
            fab.setImageDrawable(getResources().getDrawable(R.drawable.btn_star_big_on_selected));
            fab.setSelected(true);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!fab.isSelected()) {

                    fab.setImageDrawable(getResources().getDrawable(R.drawable.btn_star_big_on_selected));
                    fab.setSelected(true);
                    helper.ChangeFavStat(false, lryicID);
                    Toast tost = Toast.makeText(getApplicationContext(), "የተመረጡ ዝርዝር ውስጥ ተካቶል", Toast.LENGTH_SHORT);
                    tost.show();
                } else {

                    fab.setImageDrawable(getResources().getDrawable(R.drawable.btn_star_big_off));
                    fab.setSelected(false);
                    helper.ChangeFavStat(true, lryicID);
                    Toast tost = Toast.makeText(getApplicationContext(), "ከተመረጡ ዝርዝር ውስጥ ተወግዶል", Toast.LENGTH_SHORT);
                    tost.show();
                }

            }
        });

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if(Integer.parseInt(id)!=53)
                    return false;
                Toast toast= Toast.makeText(Lyric.this, SECRET_MESSAGE, Toast.LENGTH_LONG);
                toast.show();
                toast.setGravity(Gravity.CENTER,0,0);

                return true;
            }
        });


        TextView textView = (TextView) findViewById(R.id.Azmach);


        textView.setText(helper.getLyric(lryicID));


    }



}