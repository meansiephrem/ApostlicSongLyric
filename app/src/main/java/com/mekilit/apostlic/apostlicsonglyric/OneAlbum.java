package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.io.File;
import java.io.FileOutputStream;
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
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context=this;
        final MyDbHandler helper = new MyDbHandler(context);
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
        else {
            Bitmap bitmap = BitmapFactory.decodeFile(getResources().getString(R.string.path)
                    + albumID + ".jpg");
            if (bitmap!=null)
            albumArt.setImageBitmap(bitmap);
            else
                getImageFromServer(albumID+".jpg");
        }



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

    private void getImageFromServer(final String picName) {

        final String urlForPic = getResources().getString(R.string.url)+picName;

        MySingleton.getInstance(context).getmImageLoader().get(urlForPic,
                new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response,
                                           boolean isImmediate) {

                       Bitmap bitmap1= response.getBitmap();
                        albumArt.setImageBitmap(bitmap1);
                        SaveImage(bitmap1,picName);
                        }
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        albumArt.setImageBitmap(BitmapFactory.decodeResource(getResources(),
                                R.drawable.defultpic));
                    }


                });

    }

    private  void SaveImage(Bitmap bitmap,String name)
    {
        File direct = new File(Environment.getExternalStorageDirectory() +
                "/Apostolic Songs/Album Arts");

        String filename=name;
        if (!direct.exists()) {
            File AlbumArt = new File("sdcard/Apostolic Songs/Album Arts/");
            AlbumArt.mkdirs();
        }
        File file = new File(new File(getResources().getString(R.string.path)), filename);

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,90,out);
            out.flush();
            out.close();

        }catch (Exception e)
        {
            // e.printStackTrace();
        }
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
