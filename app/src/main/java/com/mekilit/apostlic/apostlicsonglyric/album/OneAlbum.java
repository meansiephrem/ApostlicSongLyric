package com.mekilit.apostlic.apostlicsonglyric.album;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mekilit.apostlic.apostlicsonglyric.R;
import com.mekilit.apostlic.apostlicsonglyric.lyrics.SongAdaptor;
import com.mekilit.apostlic.apostlicsonglyric.application.ApostolicSongs;
import com.mekilit.apostlic.apostlicsonglyric.application.MySingleton;
import com.mekilit.apostlic.apostlicsonglyric.lyrics.Lyric;
import com.mekilit.apostlic.apostlicsonglyric.utils.MyDbHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class OneAlbum extends AppCompatActivity {
    protected ApostolicSongs app ;
    String albumID = "";
    ListView listView;
    View header;
    ImageView albumArt;
    TextView albumName;
    TextView artistName;
    TextView numberOfSongs;
    LinearLayout layout;
    Context context;
    int bgColor;
    int txtColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context=this;
        final MyDbHandler helper = new MyDbHandler(context);

        Intent intent       = getIntent();
        albumID             = intent.getStringExtra(Intent.EXTRA_TEXT);
        app                 = (ApostolicSongs) getApplication();

        super.onCreate(savedInstanceState);
        setTheme(ApostolicSongs.theme);
        setContentView(R.layout.one_album_list);

        header          = getLayoutInflater().inflate(R.layout.one_album_layout, null);
        albumArt        = (ImageView) header.findViewById(R.id.oneAlbumArt);
        albumName       = (TextView) header.findViewById(R.id.oneAlbumName);
        artistName      = (TextView) header.findViewById(R.id.oneArtistName);
        numberOfSongs   = (TextView) header.findViewById(R.id.oneNumberOfSongs);
        layout          = (LinearLayout) findViewById(R.id.oneAlbumLinerLayout);



        if (ApostolicSongs.theme == R.style.AppTheme_Black) {

            bgColor = Color.BLACK;
            txtColor = Color.WHITE;
            layout.setBackgroundColor(bgColor);
            header.setBackgroundColor(bgColor);
            albumName.setTextColor(txtColor);
            artistName.setTextColor(txtColor);
            numberOfSongs.setTextColor(txtColor);

        }


        albumName.setText(helper.getAlbumName(albumID));
        artistName.setText(helper.getArtistName(albumID));
        numberOfSongs.setText(helper.CountSongs(albumID));


        int res = helper.getAlbumArt(albumID);

        if (res != 0)
            albumArt.setImageResource(res);
        else {
            Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()+
                    getResources().getString(R.string.path)
                    + albumID + ".jpg");
            if (bitmap!=null)
            albumArt.setImageBitmap(bitmap);
        }


        final ArrayList<String> Songs = helper.SelectAllSongs(albumID);
        listView = (ListView) findViewById(R.id.Songs);

        listView.addHeaderView(header);

        new SongLoader().execute();

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


    private  void SaveImage(Bitmap bitmap,String name)
    {
        File direct = new File(Environment.getExternalStorageDirectory() +
                "/Apostolic Songs/Album Arts");

        String filename=name;
        if (!direct.exists()) {
            File AlbumArt = new File("sdcard/Apostolic Songs/Album Arts/");
            mkFolder(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Apostolic Songs/Album Arts/");
        }
        File file = new File(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+
                getResources().getString(
                R.string.path)), filename);

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,90,out);
            out.flush();
            out.close();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (app.getUpdateAlbum().equalsIgnoreCase("10"))
        {
            new SongLoader().execute();
            app.setUpdateAlbum("-1");
        }

    }

    @SuppressWarnings("unchecked")
    public class SongLoader extends AsyncTask<Void, Integer, ListAdapter> {


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


    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    public int mkFolder(String folderName){ // make a folder under Environment.DIRECTORY_DCIM
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)){
            Log.d("myAppName", "Error: external storage is unavailable");
            return 0;
        }
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            Log.d("myAppName", "Error: external storage is read only.");
            return 0;
        }
        Log.d("myAppName", "External storage is not read only or unavailable");

        if (ContextCompat.checkSelfPermission(this, // request permission when it is not granted.
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("myAppName", "permission:WRITE_EXTERNAL_STORAGE: NOT granted!");
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),folderName);
        int result = 0;
        if (folder.exists()) {
            Log.d("myAppName","folder exist:"+folder.toString());
            result = 2; // folder exist
        }else{
            try {
                if (folder.mkdir()) {
                    Log.d("myAppName", "folder created:" + folder.toString());
                    result = 1; // folder created
                } else {
                    Log.d("myAppName", "creat folder fails:" + folder.toString());
                    result = 0; // creat folder fails
                }
            }catch (Exception ecp){
                ecp.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
