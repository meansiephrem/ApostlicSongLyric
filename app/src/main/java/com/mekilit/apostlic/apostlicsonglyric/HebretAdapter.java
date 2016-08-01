package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Menasi on 7/9/2016.
 */
public class HebretAdapter extends ArrayAdapter<String>
{
    public HebretAdapter(Context context, ArrayList<String> objects) {
        super(context, R.layout.custom_album, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyDbHandler helper = new MyDbHandler(getContext(),null,null,1);
        View customView = convertView;
        ViewHolders holders = null;
        if (customView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            customView = inflater.inflate(R.layout.custom_album, parent, false);
            holders = new ViewHolders(customView);
            customView.setTag(holders);
        } else {
            holders = (ViewHolders) customView.getTag();
        }

        String albumID = getItem(position);
        String ArtistName = helper.getArtistName(albumID);
        String AlbumName = helper.getAlbumName(albumID);




   holders.BigText.setText(AlbumName);
        holders.SmallText.setText(ArtistName);
        int AlbumArt = helper.getAlbumArt(albumID);

        if (AlbumArt == 0)
            holders.albumArt.setImageResource(R.drawable.defultpic);
        else
            holders.albumArt.setImageResource(AlbumArt);

        return customView;
    }


}