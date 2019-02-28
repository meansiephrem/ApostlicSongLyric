package com.mekilit.apostlic.apostlicsonglyric.utils;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mekilit.apostlic.apostlicsonglyric.R;
import com.mekilit.apostlic.apostlicsonglyric.application.ApostolicSongs;

/**
 * Created by Menasi on 8/1/2016.
 */
public class ViewHolders {
    public ImageView albumArt;
    public TextView BigText;
    public TextView SmallText;

    public ViewHolders(View view) {
        albumArt = (ImageView) view.findViewById(R.id.albumArt);
        BigText = (TextView) view.findViewById(R.id.BigText);
        SmallText = (TextView) view.findViewById(R.id.SmallText);


        if(ApostolicSongs.theme == R.style.AppTheme_Black){

            BigText.setTextColor(Color.WHITE);
            SmallText.setTextColor(Color.WHITE);

        }

    }
}