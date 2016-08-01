package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by Menasi on 6/23/2016.
 */
public class ArtistAdapter extends ArrayAdapter<String> {
    public ArtistAdapter(Context context, String[] objects) {
        super(context, R.layout.custom_album, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View customView = convertView;
        ViewHolders holders = null;
        MyDbHandler helper = new MyDbHandler(getContext(), null, null, 1);
        if (customView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            customView = inflater.inflate(R.layout.custom_album, parent, false);
            holders = new ViewHolders(customView);
            customView.setTag(holders);
        } else {
            holders = (ViewHolders) customView.getTag();
        }

        String ArtistName = getItem(position);
        String albumID = helper.getAlbumID(ArtistName);
        String AlbumCount = helper.CountAlbumF(ArtistName);


       holders.BigText.setText(ArtistName);
        holders.SmallText.setText(AlbumCount);
        int AlbumArt = helper.getAlbumArt(albumID);

        if (AlbumArt == 0)
            holders.albumArt.setImageResource(R.drawable.defultpic);
        else
            holders.albumArt.setImageResource(AlbumArt);


        return customView;
    }



}
