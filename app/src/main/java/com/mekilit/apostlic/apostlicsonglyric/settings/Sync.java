package com.mekilit.apostlic.apostlicsonglyric.settings;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mekilit.apostlic.apostlicsonglyric.R;
import com.mekilit.apostlic.apostlicsonglyric.album.Album;
import com.mekilit.apostlic.apostlicsonglyric.application.ApostolicSongs;
import com.mekilit.apostlic.apostlicsonglyric.application.MySingleton;
import com.mekilit.apostlic.apostlicsonglyric.lyrics.Song;
import com.mekilit.apostlic.apostlicsonglyric.utils.MyDbHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Sync extends AppCompatActivity {
    protected ApostolicSongs app;
    MyDbHandler helper = new MyDbHandler(this);
    Toolbar toolbar;
    Context context;
    ProgressBar Bar;
    ListView listView;
    TextView textView;
    ArrayList<String> albumNO=new ArrayList();
    ArrayList<Album> AlbumList;
    ArrayAdapter<Album> adapter;
    Album  currAlbumId;
    boolean can_back = true,sema=true;
    int sync = -100;
    ProgressDialog dialog;


    @Override
    public void onBackPressed() {
        if(can_back)
        super.onBackPressed();
        else
            Toast.makeText(context, "አውርዶ እስኪጨርስ እባኮ ይታገሱ", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        app = (ApostolicSongs)getApplication();

        setTheme(app.theme);

        setContentView(R.layout.activity_sync);
        toolbar= findViewById(R.id.appBar);
        listView = findViewById(R.id.newAlbums);
        textView = findViewById(R.id.SyncTV);
        Bar = findViewById(R.id.progressBar2);
        RelativeLayout layout = findViewById(R.id.sync_relative_layout);

        if (ApostolicSongs.theme == R.style.AppTheme_Black){



            textView.setTextColor(Color.WHITE);
            layout.setBackgroundColor(Color.BLACK);
        }

        toolbar.setTitle("አዳዲስ አልበሞች");
        toolbar.setSubtitle("እየፈለገ ነው...");

        toolbar.setBackgroundColor(app.color);
        toolbar.setTitleTextColor(ApostolicSongs.toolbarColor);
        toolbar.setSubtitleTextColor(ApostolicSongs.toolbarColor);

        Bar.setVisibility(View.VISIBLE);
        dialog = new ProgressDialog(Sync.this);
        dialog.setMessage("እያወረደ ነው... ");
        dialog.setCancelable(false);
        String url;
        String date_created = app.ReadFromFile(this,"DATE_CREATED","2018-01-09T18:13:52.000Z");
        url = getResources().getString(R.string.url)+"/albums/?" +
                "[sort][date_created]=-1&per_page=20&[query][date_created][$gte]="+date_created;


    }

    private ArrayList<Album> JsonToAlbumList(JSONObject rawJsonStr)
    {
        ArrayList<Album> album = new ArrayList<>();
        JSONObject jsonObject = rawJsonStr;
        String Date_Created = ApostolicSongs.ReadFromFile(this,"DATE_CREATED","2018-01-09T18:13:52.000Z");

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("docs");
            Log.d("Respose",jsonArray.toString());
            for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jobj = jsonArray.getJSONObject(i);
                if(ApostolicSongs.ReadFromFile(this,jobj.getString("_id"),"NOTSYNCED").
                        equalsIgnoreCase("NOTSYNCED")){

                    String albumId =jobj.getString("Album_ID");
                    String albumTitle = jobj.getString("Album_Title");
                    String albumArtist = jobj.getString("Album_Artist");
                    String albumArt = jobj.getString("Album_Art");
                    String _id      = jobj.getString("_id");
                    int isSolo = Integer.parseInt(jobj.getString("isSolo"));
                    Album album1 = new Album(albumId,albumTitle,albumArtist,albumArt,isSolo);
                    album1.set_ID(_id);
                    album.add(album1);
                    Date_Created = jobj.getString("date_created");
                }
            }
            ApostolicSongs.saveToFile(this,"DATE_CREATED", Date_Created);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return album;
    }

    private ArrayList<Song> JsonToSongList(JSONObject rawJsonStr)
    {
        ArrayList<Song> song = new ArrayList();
        JSONObject jsonObject = rawJsonStr;
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("docs");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jobj = jsonArray.getJSONObject(i);
                song.add(new Song(jobj.getString("Lyric_Title"),jobj.getString("Album_id")
                        ,jobj.getString("Lyric_Text")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return song;
    }

    private void subTitle()
    {
        if(AlbumList.size()==1)
            toolbar.setSubtitle("1 አልበም");
        else if(AlbumList.size()==0)
        {
            toolbar.setSubtitle( AlbumList.size()+ " አልበሞች");
            textView.setText("ሁሉም አልበሞች ወርደዋል :)");
        }
        else
            toolbar.setSubtitle( AlbumList.size()+ " አልበሞች");
    }

    private synchronized void saveToDatabase(JSONObject res,Album album)
    {
        Log.d("Save ", "to DB");
        ArrayList<Song> songs = JsonToSongList(res);
        new MyDbHandler(context).InsertNewAlbum(album,songs);
        app.saveToFile(this,album.get_ID(),"SYNCED");
        dialog.dismiss();
        can_back = true;

    }



    private void getLocalBitmapUri(Bitmap bmp, String albumID) {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        try {
            File file =  new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    albumID+ ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
