package com.mekilit.apostlic.apostlicsonglyric.album;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.mekilit.apostlic.apostlicsonglyric.application.ApostolicSongs;
import com.mekilit.apostlic.apostlicsonglyric.utils.MyDbHandler;
import com.mekilit.apostlic.apostlicsonglyric.application.MySingleton;
import com.mekilit.apostlic.apostlicsonglyric.R;

import java.io.File;
import java.io.FileOutputStream;

public class AlbumArt extends AppCompatActivity {
    String name;
    ImageView albumArt;
    String AlbumID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyDbHandler helper = new MyDbHandler(this);
        setContentView(R.layout.activity_album_art);
        setTheme(ApostolicSongs.theme);
        albumArt = (ImageView) findViewById(R.id.BigAlbumArt);
        Intent intent = getIntent();
        AlbumID = intent.getStringExtra(Intent.EXTRA_TEXT);

        int res = helper.getAlbumArt(AlbumID);

        if (res != 0)
            albumArt.setImageResource(res);
        else {

            Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()+
                    getResources().getString(R.string.path)
                    + AlbumID + ".jpg");
            if (bitmap!=null)
                albumArt.setImageBitmap(bitmap);
            else
                getImageFromServer(helper.getAlbumArtForServer(AlbumID));
        }

    }
    private void getImageFromServer(final String picName) {

        final String urlForPic = getResources().getString(R.string.url)+picName;

        MySingleton.getInstance(this).getmImageLoader().get(urlForPic,
                new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response,
                                           boolean isImmediate) {

                        Bitmap bitmap1= response.getBitmap();
                        albumArt.setImageBitmap(bitmap1);
                        SaveImage(bitmap1,AlbumID);
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
            mkFolder(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Apostolic Songs/Album Arts/");
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
            e.printStackTrace();
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
