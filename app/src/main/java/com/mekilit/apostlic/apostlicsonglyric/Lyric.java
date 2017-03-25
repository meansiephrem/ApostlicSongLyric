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
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Lyric extends AppCompatActivity implements OnClickListener {

    private static final int SWIPE_MIN_DISTANCE=120;
    private static final int SWIPE_MAX_OFF_PATH=350;
    private static final int SWIPE_THRESHOLD_VELOCITY=150;
    protected ApostolicSongs app;
    Toolbar toolbar;
    String id, ActualLyric, Promotion;
    int FavCount;
    View.OnTouchListener gestureListener;
    FloatingActionButton fab;
    TextView textView;
    MyDbHandler helper = new MyDbHandler(Lyric.this);
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getApplication().setTheme(R.style.MenasiInfo);
        super.onCreate(savedInstanceState);
        gestureDetector = new GestureDetector(this,new MyGesDec());
        gestureListener = new View.OnTouchListener()
        {
            public boolean onTouch(View v ,MotionEvent event){
                return gestureDetector.onTouchEvent(event);
            }
        };
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
        int lryicID = Integer.parseInt(id);
        String albumId = helper.getAlbumID(lryicID);

        toolbar.setTitle(helper.getSongName(id));
        toolbar.setSubtitle(helper.getArtistName(albumId));

        setSupportActionBar(toolbar);


        boolean isFav = helper.isFav(id);
        FavCount = Integer.parseInt(helper.CountAlL('d'));

       fab = (FloatingActionButton) findViewById(R.id.fab);
        if (!isFav) {
            fab.setImageResource(android.R.drawable.btn_star_big_off);
            fab.setSelected(false);
        } else {
            fab.setImageResource(android.R.drawable.btn_star_big_on);
            fab.setSelected(true);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            final int lryicID1 = Integer.parseInt(id);
                if (!fab.isSelected()) {

                    fab.setImageResource(android.R.drawable.btn_star_big_on);
                    fab.setSelected(true);
                    helper.ChangeFavStat(false, lryicID1);
                    Toast tost = Toast.makeText(getApplicationContext(), "የተመረጡ ዝርዝር ውስጥ ተካቷል",
                            Toast.LENGTH_SHORT);
                    tost.show();
                } else {

                    fab.setImageResource(android.R.drawable.btn_star_big_off);
                    fab.setSelected(false);
                    helper.ChangeFavStat(true, lryicID1);
                    Toast tost = Toast.makeText(getApplicationContext(), "ከተመረጡ ዝርዝር ውስጥ ተወግዶል",
                            Toast.LENGTH_SHORT);
                    tost.show();
                }

                app.setUpdateAlbum("10");
            }
        });

        fab.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                return SecMsg(id);
            }
        });


        textView = (TextView) findViewById(R.id.Azmach);
        app = (ApostolicSongs)getApplication();
        textView.setTextSize(app.getLyricTextSize());
        ScrollView scrollView =(ScrollView) findViewById(R.id.scrollView);

        textView.setText(helper.getLyric(lryicID));


        scrollView.setOnClickListener(Lyric.this);
        scrollView.setOnTouchListener(gestureListener);



    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.lyric_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.action_share)
        {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, getShareStr());
            startActivity(Intent.createChooser(intent,"በ... አጋራ"));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void NxtSwapPrev(boolean nxt){
        int PrevID= Integer.parseInt(id);
        if (nxt)
         PrevID--;
        else
            PrevID++;

        getSupportActionBar().setTitle(helper.getSongName(PrevID + ""));
        String albumId = helper.getAlbumID(PrevID);
        getSupportActionBar().setSubtitle(helper.getArtistName(albumId));

        boolean isFav = helper.isFav(PrevID+"");
        if (!isFav) {
            fab.setImageResource(android.R.drawable.btn_star_big_off);
            fab.setSelected(false);
        } else {
            fab.setImageResource(android.R.drawable.btn_star_big_on);
            fab.setSelected(true);
        }
        textView.setText(helper.getLyric(PrevID));
        id =PrevID+"";

    }

    private boolean SecMsg(String id){//  Toast.makeText(Lyric.this, id, Toast.LENGTH_SHORT).show();
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
            return false;}

    private String getShareStr()
    {
        Promotion = "\n\n"+toolbar.getTitle()+"\n"+toolbar.getSubtitle()+"\n"+"Apostolic Songs";
        ActualLyric =(String)textView.getText();
        return ActualLyric+Promotion;
    }


    class MyGesDec extends GestureDetector.SimpleOnGestureListener
{
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            if (Math.abs(e1.getY()-e2.getY())>SWIPE_MAX_OFF_PATH)
                return false;
            if (e1.getX()-e2.getX()>SWIPE_MIN_DISTANCE&&Math.abs(velocityX)>SWIPE_THRESHOLD_VELOCITY)
            {
               NxtSwapPrev(false);
            }
            else if (e2.getX()-e1.getX()>SWIPE_MIN_DISTANCE&&Math.abs(velocityX)>
                    SWIPE_THRESHOLD_VELOCITY){
                NxtSwapPrev(true);
            }
        }catch (Exception e)
        {}
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }
  }
}