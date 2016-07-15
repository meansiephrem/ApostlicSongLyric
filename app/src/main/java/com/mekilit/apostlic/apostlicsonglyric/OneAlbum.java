package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class OneAlbum extends AppCompatActivity {
    String albumID="";
    ListView listView;
    View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final MyDbHandler helper = new MyDbHandler(this,null,null,1);
        Intent intent = getIntent();
        albumID=intent.getStringExtra(Intent.EXTRA_TEXT);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_album_list);
         header = getLayoutInflater().inflate(R.layout.one_album_layout, null);

        ImageView albumArt = (ImageView) header.findViewById(R.id.oneAlbumArt);
        TextView albumName = (TextView) header.findViewById(R.id.oneAlbumName);
        TextView artistName = (TextView) header.findViewById(R.id.oneArtistName);
        TextView numberOfSongs = (TextView) header.findViewById(R.id.oneNumberOfSongs);


        try {
            String name = albumID.toLowerCase();
            Class res = R.drawable.class;
            Field field = res.getField(name);
            int res1 =field.getInt(null);
            albumArt.setImageResource(res1);
        }catch (Exception e)
        {
            albumArt.setImageResource(R.drawable.defultpic);
        }
        albumName.setText(helper.getAlbumName(albumID));
        artistName.setText(helper.getArtistName(albumID));
        numberOfSongs.setText(helper.CountSongs(albumID));


        final ArrayList<String> Songs = helper.SelectAllSongs(albumID);
        listView = (ListView) findViewById(R.id.Songs);

        listView.addHeaderView(header);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if (position==0){
                    Intent intent = new Intent(OneAlbum.this,AlbumArt.class).putExtra(Intent.EXTRA_TEXT,
                            albumID);
                    startActivity(intent);
                }
                else{
                        Intent intent = new Intent(OneAlbum.this,Lyric.class).putExtra(Intent.EXTRA_TEXT,
                                            Songs.get(position-1));
                        startActivity(intent);
                }
            }

        });
    }

    @Override
    public void onStart() {
        super.onStart();
        final MyDbHandler helper = new MyDbHandler(this,null,null,1);
        final ArrayList<String> Songs = helper.SelectAllSongs(albumID);
        ListAdapter adapter = new SongAdaptor(this,Songs);
        listView.setAdapter(adapter);


    }
}
