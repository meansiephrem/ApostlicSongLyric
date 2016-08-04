package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Created by Menasi on 8/4/2016.
 */
public class DecodeTask extends AsyncTask<Integer, Void, Bitmap> {

    private static int MaxTextureSize = 2048; /* True for most devices. */

    public ImageView v;
    public Context context;

    public DecodeTask(ImageView iv,Context context) {
        v = iv;
       this.context=context;
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
        do {
            opt.inSampleSize++;
            BitmapFactory.decodeResource(context.getResources(),params[0],opt);
        } while(opt.outHeight > MaxTextureSize || opt.outWidth > MaxTextureSize);
        opt.inJustDecodeBounds = false;

        bitmap =  BitmapFactory.decodeResource(context.getResources(), params[0], opt);
        if (bitmap!=null)
        return bitmap;
        else
            return  BitmapFactory.decodeResource(context.getResources(), R.drawable.defultpic, opt);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if(v != null) {
            v.setImageBitmap(result);

        }
    }

}