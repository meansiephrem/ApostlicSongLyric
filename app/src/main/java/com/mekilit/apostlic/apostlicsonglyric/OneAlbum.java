package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class OneAlbum extends AppCompatActivity {
    protected  ApostolicSongs app ;
    String albumID = "";
    ListView listView;
    View header;
    ImageView albumArt;
    TextView albumName;
    TextView artistName;
    TextView numberOfSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final MyDbHandler helper = new MyDbHandler(this);
        Intent intent = getIntent();
        albumID = intent.getStringExtra(Intent.EXTRA_TEXT);
        app = (ApostolicSongs) getApplication();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_album_list);
        header = getLayoutInflater().inflate(R.layout.one_album_layout, null);

        albumArt = (ImageView) header.findViewById(R.id.oneAlbumArt);
        albumName = (TextView) header.findViewById(R.id.oneAlbumName);
        artistName = (TextView) header.findViewById(R.id.oneArtistName);
        numberOfSongs = (TextView) header.findViewById(R.id.oneNumberOfSongs);



        albumName.setText(helper.getAlbumName(albumID));
        artistName.setText(helper.getArtistName(albumID));
        numberOfSongs.setText(helper.CountSongs(albumID));

            int res =helper.getAlbumArt(albumID);
        if (res != 0)
            albumArt.setImageResource(res);
        else
            albumArt.setImageBitmap(BitmapFactory.decodeFile(getResources().getString(R.string.path)
                    +albumID+".jpg"));




        final ArrayList<String> Songs = helper.SelectAllSongs(albumID);
        listView = (ListView) findViewById(R.id.Songs);

        listView.addHeaderView(header);


        new SongLoder().execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if (position == 0) {
                    Intent intent = new Intent(OneAlbum.this, AlbumArt.class).putExtra(Intent.EXTRA_TEXT,
                            albumID);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(OneAlbum.this, Lyric.class).putExtra(Intent.EXTRA_TEXT,
                            Songs.get(position - 1));
                    startActivity(intent);
                }
            }

        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (app.getUpdateAlbum().equalsIgnoreCase("10"))
        {
            new SongLoder().execute();
            app.setUpdateAlbum("-1");
        }

    }

    @SuppressWarnings("unchecked")
    public class SongLoder extends AsyncTask<Void, Integer, ListAdapter> {


        @Override
        protected ListAdapter doInBackground(Void... params) {
            final MyDbHandler helper = new MyDbHandler(getApplicationContext());
            final ArrayList<String> Songs = helper.SelectAllSongs(albumID);

            ArrayList<String> SongName = new ArrayList();
            ArrayList<Boolean> IsFav = new ArrayList();


            for (int i = 0; i < Songs.size(); i++) {


                SongName.add(helper.getSongName(Songs.get(i)));
                IsFav.add(helper.isFav(Songs.get(i)));


            }
            ListAdapter adapter = new SongAdaptor(getApplicationContext(),SongName,IsFav,Songs);
            Log.e("Song Adaptor ", "Finished building the Song adaptor");

            return adapter;
        }

        @Override
        protected void onPostExecute(ListAdapter listAdapter) {
            listView.setAdapter(listAdapter);

            super.onPostExecute(listAdapter);
        }
    }
}
