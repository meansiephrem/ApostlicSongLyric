package com.mekilit.apostlic.apostlicsonglyric.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.mekilit.apostlic.apostlicsonglyric.utils.AlbumArtFromSD;

/**
 * Created by Menasi on 8/4/2016.
 */
public class DecodeTask extends AsyncTask<Integer, Void, Bitmap> {

    public ImageView v;
    public Context context;
    public String albumArt;
    public AlbumArtFromSD sd;

    public DecodeTask(ImageView iv,Context context,String albumArt) {
        this.v = iv;
        this.context=context;
        this.albumArt=albumArt;
    }

    protected Bitmap doInBackground(Integer... params) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPurgeable = true;
        opt.inPreferQualityOverSpeed = false;
        opt.inSampleSize = 0;

        Bitmap bitmap = null;
        if(isCancelled()) {
            return bitmap;
        }

        opt.inJustDecodeBounds = true;
        int maxTextureSize = 2048;
        do {
            opt.inSampleSize++;
            BitmapFactory.decodeResource(context.getResources(),params[0],opt);
        } while(opt.outHeight > maxTextureSize || opt.outWidth > maxTextureSize);
        opt.inJustDecodeBounds = false;
        opt.inSampleSize=3;

        bitmap =  BitmapFactory.decodeResource(context.getResources(), params[0], opt);
        if (bitmap!=null)
        return bitmap;
        else
            return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {


        if(v != null && result !=null) {
            v.setImageBitmap(result);
        }
        else
        {
            sd =new AlbumArtFromSD(v,context);
            sd.execute(albumArt+".jpg");
        }

    }

}