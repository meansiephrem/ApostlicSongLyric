package com.mekilit.apostlic.apostlicsonglyric.application;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.collection.LruCache;


/**
 * Created by Menasi on 3/23/2017.
 */

public class MySingleton
{
    private static MySingleton mInstance;
    private static Context mCtx;



    private MySingleton(Context context){
        mCtx = context;

    }

    public static synchronized MySingleton getInstance(Context context){
        if (mInstance == null)
        {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }


}