package com.mekilit.apostlic.apostlicsonglyric.album;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.mekilit.apostlic.apostlicsonglyric.utils.MyDbHandler;
import com.mekilit.apostlic.apostlicsonglyric.R;
import com.mekilit.apostlic.apostlicsonglyric.utils.RecyclerItemClickListener;
import com.mekilit.apostlic.apostlicsonglyric.application.ApostolicSongs;

import java.util.ArrayList;

import static android.graphics.Color.BLACK;

public class DisplayALbums extends AppCompatActivity {
    MyDbHandler helper = new MyDbHandler(this);
    Toolbar toolbar;
    boolean all=false;//to know whether this activity is called to show all albums or not
    String ArtistName;
    RecyclerView recyclerView;
    Context context;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();

        Intent intent = getIntent();
        String rawData = intent.getStringExtra(Intent.EXTRA_TEXT);//raw data name+Select type

        if (rawData.charAt(0)=='1')
            all=true;//called to display all albums
        ArtistName= minusFirst(rawData);

        setTheme(ApostolicSongs.theme);
        setContentView(R.layout.activity_display_albums);


        RelativeLayout relativeView = (RelativeLayout) findViewById(R.id.display_album_relative_layout);




        toolbar= (Toolbar) findViewById(R.id.appBar);
        toolbar.setTitleTextColor(ApostolicSongs.toolbarColor);
        toolbar.setSubtitleTextColor(ApostolicSongs.toolbarColor);

        recyclerView = (RecyclerView) findViewById(R.id.RC);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        if(ApostolicSongs.theme == R.style.AppTheme_Black){
              relativeView.setBackgroundColor(BLACK);
              recyclerView.setBackgroundColor(BLACK);
            }

        toolbar.setBackgroundColor(ApostolicSongs.color);

        if(!all)
        {
        toolbar.setTitle(ArtistName);
        toolbar.setSubtitle(helper.CountAlbumF(ArtistName));
        }
        else {
            toolbar.setTitle("ሁሉም አልበሞች");
            toolbar.setSubtitle(helper.CountAlL('a') + " አልበሞች");

        }

        new AlbumLoader().execute();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(DisplayALbums.this, OneAlbum.class);
                        if (!all)
                            intent.putExtra(Intent.EXTRA_TEXT, helper.getAlbum(ArtistName).get(position));
                        else
                            intent.putExtra(Intent.EXTRA_TEXT, helper.SelectAll().get(position));
                        startActivity(intent);
                    }
                })
        );
        
    }



    public String minusFirst (String data)
    {   String ret="";
        char [] arr = new char[data.length()-1];
        for (int j=0;j<arr.length;j++)
        {
            arr[j]=data.charAt(j+1);
        }
        for (char anArr : arr) {
            ret += anArr;
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    public class AlbumLoader extends AsyncTask<Void, Integer, AlbumAdaptor> {

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected AlbumAdaptor doInBackground(Void... params) {
            ArrayList<String> listAlbum;
            if(!all)
              listAlbum = helper.getAlbum(ArtistName);
            else
                listAlbum = helper.SelectAll();

            ArrayList<String> ArtistName=new ArrayList();
            ArrayList<String> AlbumName=new ArrayList();
            ArrayList<Integer> AlbumArt=new ArrayList();

            for (int i=0;i<listAlbum.size();i++)
            {
                String albumID=listAlbum.get(i);

                ArtistName.add(helper.getArtistName(albumID));
                AlbumName.add(helper.getAlbumName(albumID));
                AlbumArt.add(helper.getAlbumArt(albumID));

            }

            AlbumAdaptor adapter = new AlbumAdaptor(context, AlbumName,ArtistName,
                    AlbumArt,listAlbum);

            return adapter;
        }

        @Override
        protected void onPostExecute(AlbumAdaptor listAdapter) {
            recyclerView.setAdapter(listAdapter);
            progressBar.setVisibility(View.GONE);
            super.onPostExecute(listAdapter);
        }
    }
}
