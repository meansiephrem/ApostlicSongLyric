package com.mekilit.apostlic.apostlicsonglyric;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

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

    @Nullable
    private static String fixEncoding(String response)
    {
        try {
            byte[] u = response.toString().getBytes("ISO-8859-1");
            response = new String(u,"UTF-8");
        }catch (UnsupportedEncodingException ex)
        {
            ex.printStackTrace();
            return null;
        }
        return response;
    }

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
        toolbar= (Toolbar) findViewById(R.id.appBar);
        listView = (ListView) findViewById(R.id.newAlbums);
        textView = (TextView)findViewById(R.id.SyncTV);
        Bar = (ProgressBar) findViewById(R.id.progressBar2);

        toolbar.setTitle("አዳዲስ አልበሞች");
        toolbar.setSubtitle("እየፈለገ ነው...");
        toolbar.setBackgroundColor(app.color);
        toolbar.setTitleTextColor(ApostolicSongs.toolbarColor);
        toolbar.setSubtitleTextColor(ApostolicSongs.toolbarColor);



        Bar.setVisibility(View.VISIBLE);
        dialog = new ProgressDialog(Sync.this);
        dialog.setMessage("እያወረደ ነው... ");
        dialog.setCancelable(false);

        String url = getResources().getString(R.string.url)+"Albums.json";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response = fixEncoding(response);


                        AlbumList = JsonToAlbumList(response);
                         adapter = new SyncAdapter(getBaseContext()
                                ,AlbumList);


                        listView.setAdapter(adapter);
                        subTitle();
                        if(AlbumList.isEmpty())
                            textView.setText("ምንም አዲስ መዝሙር የለም :(\n ሌላ ጊዜ ይሞክሩ");

                        Bar.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("ከኢንተርኔት ጋር መገናኘት አልተቻለም :(\n ትንሽ ቆይተው ይሞክሩ");
                toolbar.setSubtitle("መገናኘት አልተቻለም");
                Bar.setVisibility(View.INVISIBLE);
            }
        }
        );

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if(sema){
                    sema=false;
                dialog.show();
                can_back = false;
                sync = -100;
                currAlbumId = AlbumList.get(position);



           String urlForSong = getResources().getString(R.string.url)+"lyric/"+
                      currAlbumId.getAlbum_id()+".json";
           final String urlForPic = getResources().getString(R.string.url)+
                        currAlbumId.getAlbum_id()+".jpg";

                StringRequest stringRequest1 = new StringRequest(Request.Method.GET, urlForSong,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                             response = fixEncoding(response);
                                saveToDatabase(response,currAlbumId,position);


                                MySingleton.getInstance(context).getmImageLoader().get(urlForPic,
                                        new ImageLoader.ImageListener() {
                                    @Override
                                    public void onResponse(ImageLoader.ImageContainer response,
                                                           boolean isImmediate) {

                                        Bitmap bitmap= response.getBitmap();
                                        SaveImage(bitmap);
                                        if(sync!=position){
                                        AlbumList.remove(position);
                                        adapter.notifyDataSetChanged();
                                        subTitle();
                                        sync=position;
                                            sema=true;
                                            dialog.dismiss();
                                            can_back =true;
                                            Toast.makeText(Sync.this,  currAlbumId.getAlbum_Title()+
                                                    " የአልበሞች ዝርዝር ውስጥ ተካቷል", Toast.LENGTH_SHORT).show();
                                        }


                                    }

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                     error.printStackTrace();
                                    }


                                });
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}

                }

                );

                MySingleton.getInstance(context).addToRequestQueue(stringRequest1);
                subTitle();
                if(AlbumList.isEmpty())
                    textView.setText("ምንም አዲስ መዝሙር የለም :(\n ሌላ ጊዜ ይሞክሩ");


            }
        }});




    }

    private ArrayList<Album> JsonToAlbumList(String rawJsonStr)
    {
        ArrayList<Album> album = new ArrayList();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(rawJsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray("Album");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jobj = jsonArray.getJSONObject(i);
                int tempAlbumNo = Integer.parseInt(jobj.getString("Album_No"));
                boolean temp=!(app.alredaySyncedAlbum(tempAlbumNo));
                if (temp) {
                    albumNO.add(tempAlbumNo+"");
                    String albumId =jobj.getString("Album_id");
                    String albumTitle = jobj.getString("Album_Title");
                    String albumArtist = jobj.getString("Album_Artist");
                    String albumArt = jobj.getString("Album_Art");
                    int isSolo = Integer.parseInt(jobj.getString("_isSolo"));
                    album.add(new Album(albumId,albumTitle,albumArtist,albumArt,isSolo));
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return album;
    }

    private ArrayList<Song> JsonToSongList(String rawJsonStr)
    {
        ArrayList<Song> song = new ArrayList();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(rawJsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray(currAlbumId.getAlbum_id());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jobj = jsonArray.getJSONObject(i);
                song.add(new Song(jobj.getString("Song_title"),jobj.getString("Album_id")
                        ,jobj.getString("Song_lyric")));
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

    private  void SaveImage(Bitmap bitmap)
    {
        File direct = new File(Environment.getExternalStorageDirectory() +
                "/Apostolic Songs/Album Arts");

        String filename=currAlbumId.getAlbum_id()+".jpg";
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

    private synchronized void saveToDatabase(String res,Album album,int pos)
    {
        ArrayList<Song> songs = JsonToSongList(res);
        new MyDbHandler(context).InsertNewAlbum(album,songs);
        songs.clear();
        app.writeSyncedAlbum(albumNO.get(pos));
        albumNO.remove(pos);
        if(app.getUpdateAlbum().contains("1")||app.getUpdateAlbum().contains("2"))
        app.setUpdateAlbum("3");
        else
            app.setUpdateAlbum(album.get_isSolo()+"");
        dialog.dismiss();
        can_back = true;

    }
}
