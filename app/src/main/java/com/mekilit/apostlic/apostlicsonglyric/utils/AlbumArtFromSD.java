package com.mekilit.apostlic.apostlicsonglyric.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.mekilit.apostlic.apostlicsonglyric.R;

import java.io.File;

/**
 * Created by Menasi on 3/16/2017.
 */

public class AlbumArtFromSD extends AsyncTask<String, Void, Bitmap> {
    public ImageView v;
    public Context context;


    public AlbumArtFromSD(ImageView iv,Context context) {
        v = iv;
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        Log.i("SD PRE", "Trying to access from SD card");
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPurgeable = true;
        opt.inPreferQualityOverSpeed = false;
        opt.inSampleSize = 0;
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
        String path;
        String [] arr = params[0].split("\\.(?=[^.]+$)");
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                arr[0]+ ".png");
        path = file.getPath();
        Bitmap bitmap = null;
        if(isCancelled()) {
            return bitmap;
        }

        opt.inJustDecodeBounds = true;
        int maxTextureSize = 2048;
        do {
            opt.inSampleSize++;
        } while(opt.outHeight > maxTextureSize || opt.outWidth > maxTextureSize);
        opt.inJustDecodeBounds = false;
        opt.inSampleSize=3;

        Log.i("Path:",path);
        bitmap =  BitmapFactory.decodeFile(path,opt);
        if (bitmap!=null)
            return bitmap;
        else
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.defultpic, opt);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if(v != null) {
            v.setImageBitmap(result);

        }
    }
}
