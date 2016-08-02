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
public class HebretAdapter extends ArrayAdapter<String> {
    ArrayList<String> ArtistName;
    ArrayList<String> AlbumName;
    ArrayList<Integer> AlbumArt;

    public HebretAdapter(Context context, ArrayList<String> ArtistName,
                         ArrayList<String> albumName, ArrayList<Integer> albumArt) {
        super(context, R.layout.custom_album, ArtistName);
        this.ArtistName = ArtistName;
        this.AlbumName = albumName;
        this.AlbumArt = albumArt;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

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


        String ArtistName = getItem(position);
        String AlbumName = this.AlbumName.get(position);
        int AlbumArt = this.AlbumArt.get(position);


        holders.BigText.setText(AlbumName);
        holders.SmallText.setText(ArtistName);


        if (AlbumArt == 0)
            holders.albumArt.setImageResource(R.drawable.defultpic);
        else
            holders.albumArt.setImageResource(AlbumArt);

        return customView;
    }


}