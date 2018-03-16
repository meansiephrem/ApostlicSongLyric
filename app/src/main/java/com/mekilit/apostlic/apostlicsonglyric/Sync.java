package com.mekilit.apostlic.apostlicsonglyric;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
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
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.sync_relative_layout);

        if (ApostolicSongs.theme == R.style.AppTheme_Black){



            textView.setTextColor(Color.WHITE);
            layout.setBackgroundColor(Color.BLACK);
        }

        toolbar.setTitle("አዳዲስ አልበሞች");
        toolbar.setSubtitle("እየፈለገ ነው...");

        toolbar.setBackgroundColor(app.color);
        toolbar.setTitleTextColor(ApostolicSongs.toolbarColor);
        toolbar.setSubtitleTextColor(ApostolicSongs.toolbarColor);

        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

        Bar.setVisibility(View.VISIBLE);
        dialog = new ProgressDialog(Sync.this);
        dialog.setMessage("እያወረደ ነው... ");
        dialog.setCancelable(false);
        String url;
        String date_created = app.ReadFromFile(this,"DATE_CREATED","2018-01-09T18:13:52.000Z");
        url = getResources().getString(R.string.url)+"/albums/?" +
                "[sort][date_created]=-1&per_page=20&[query][date_created][$gte]="+date_created;



        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {



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
                Log.e("ERROR TO CONNECT", error.getMessage());
                textView.setText("ከኢንተርኔት ጋር መገናኘት አልተቻለም :(\n ትንሽ ቆይተው ይሞክሩ");
                toolbar.setSubtitle("መገናኘት አልተቻለም");
                Bar.setVisibility(View.INVISIBLE);
            }
        }


        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " +
                        "259dDkFw2bWxmB3iVmxonGa+ZqG7ewjIHoLFwK/YY2pklhJf3NjBIqgq07scYIVzXYiVRObCFa7f");
                return headers;
            }
        };


        MySingleton.getInstance(this).addToRequestQueue(req);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if(sema){
                    sema=false;
                dialog.show();
                can_back = false;
                sync = -100;
                currAlbumId = AlbumList.get(position);



           String urlForSong = getResources().getString(R.string.url)+"/albums/"+
                      currAlbumId.get_ID()+"/lyrics?per_page=20";
           final String urlForPic = getResources().getString(R.string.url)+
                        currAlbumId.getAlbum_Art();
                    Log.d("urlForPic", urlForPic);

                JsonObjectRequest stringRequest1 = new JsonObjectRequest(urlForSong, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {


                                saveToDatabase(response,currAlbumId);


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

                ){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Authorization", "Bearer " +
                                "259dDkFw2bWxmB3iVmxonGa+ZqG7ewjIHoLFwK/YY2pklhJf3NjBIqgq07scYIVzXYiVRObCFa7f");
                        return headers;
                    }
                };


                    MySingleton.getInstance(context).addToRequestQueue(stringRequest1);
                subTitle();
                if(AlbumList.isEmpty())
                    textView.setText("ምንም አዲስ መዝሙር የለም :(\n ሌላ ጊዜ ይሞክሩ");


            }
        }});




    }

    private ArrayList<Album> JsonToAlbumList(JSONObject rawJsonStr)
    {
        ArrayList<Album> album = new ArrayList();
        JSONObject jsonObject = rawJsonStr;
        String Date_Created = app.ReadFromFile(this,"DATE_CREATED","2018-01-09T18:13:52.000Z");

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("docs");
            Log.d("Respose",jsonArray.toString());
            for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jobj = jsonArray.getJSONObject(i);
                if(app.ReadFromFile(this,jobj.getString("_id"),"NOTSYNCED").
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
            app.saveToFile(this,"DATE_CREATED", Date_Created);

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

    private  void SaveImage(Bitmap bitmap)
    {
        File direct = new File(Environment.getExternalStorageDirectory() +
                "/Apostolic Songs/Album Arts");

        String filename=currAlbumId.getAlbum_id()+".jpg";
        if (!direct.exists()) {
            File AlbumArt = new File("sdcard/Apostolic Songs/Album Arts/");
            mkFolder("sdcard/Apostolic Songs/Album Arts/");
        }
        File file = new File(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+
                getResources().getString(R.string.path)), filename);

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

    private synchronized void saveToDatabase(JSONObject res,Album album)
    {
        Log.d("Save ", "to DB");
        ArrayList<Song> songs = JsonToSongList(res);
        new MyDbHandler(context).InsertNewAlbum(album,songs);
        app.saveToFile(this,album.get_ID(),"SYNCED");
        dialog.dismiss();
        can_back = true;

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
