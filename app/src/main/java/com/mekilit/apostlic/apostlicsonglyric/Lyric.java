package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Lyric extends AppCompatActivity {

    Toolbar toolbar;
    String id;
    int FavCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            TransitionInflater inflater = TransitionInflater.from(this);
            Transition transition = inflater.inflateTransition(R.transition.lyric_out);
            getWindow().setExitTransition(transition);

        }
        setContentView(R.layout.activity_lyric);
        final MyDbHandler helper = new MyDbHandler(this);
        Intent inten = getIntent();
        id = inten.getStringExtra(Intent.EXTRA_TEXT);
        toolbar = (Toolbar) findViewById(R.id.lyapp);
        final int lryicID = Integer.parseInt(id);
        String albumId = helper.getAlbumID(lryicID);

        toolbar.setTitle(helper.getSongName(id));
        toolbar.setSubtitle(helper.getArtistName(albumId));

        setSupportActionBar(toolbar);


        boolean isFav = helper.isFav(id);
        FavCount = Integer.parseInt(helper.CountAlL('d'));

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (!isFav) {
            fab.setImageDrawable(getResources().getDrawable(R.drawable.btn_star_big_off));
            fab.setSelected(false);
        } else {
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
                    Toast tost = Toast.makeText(getApplicationContext(), "የተመረጡ ዝርዝር ውስጥ ተካቷል",
                            Toast.LENGTH_SHORT);
                    tost.show();
                } else {

                    fab.setImageDrawable(getResources().getDrawable(R.drawable.btn_star_big_off));
                    fab.setSelected(false);
                    helper.ChangeFavStat(true, lryicID);
                    Toast tost = Toast.makeText(getApplicationContext(), "ከተመረጡ ዝርዝር ውስጥ ተወግዶል",
                            Toast.LENGTH_SHORT);
                    tost.show();
                }

            }
        });

        fab.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
              //  Toast.makeText(Lyric.this, id, Toast.LENGTH_SHORT).show();
                int idd = Integer.parseInt(id);
                if ((idd == 50 || idd == 568 || idd == 734 || idd == 844) && FavCount > 4) {
                    Intent intent = new Intent(Lyric.this, Sec_Activity.class)
                            .putExtra(Intent.EXTRA_TEXT, "" + 0);
                    ActivityOptionsCompat compat = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(Lyric.this, null);
                    startActivity(intent, compat.toBundle());


                    return true;
                } else if (idd == 467 && FavCount > 4) {
                    Intent intent = new Intent(Lyric.this, Sec_Activity.class)
                            .putExtra(Intent.EXTRA_TEXT, "" + 1);
                    ActivityOptionsCompat compat = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(Lyric.this, null);
                    startActivity(intent, compat.toBundle());


                    return true;
                } else if (idd == 433 && FavCount > 4) {
                    Intent intent = new Intent(Lyric.this, Sec_Activity.class)
                            .putExtra(Intent.EXTRA_TEXT, "" + 3);
                    ActivityOptionsCompat compat = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(Lyric.this, null);
                    startActivity(intent, compat.toBundle());


                    return true;
                } else if (idd == 560 && FavCount > 4) {
                    Intent intent = new Intent(Lyric.this, Sec_Activity.class)
                            .putExtra(Intent.EXTRA_TEXT, "" + 2);
                    ActivityOptionsCompat compat = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(Lyric.this, null);
                    startActivity(intent, compat.toBundle());


                    return true;
                } else
                    return false;


            }
        });


        TextView textView = (TextView) findViewById(R.id.Azmach);


        textView.setText(helper.getLyric(lryicID));


    }


}