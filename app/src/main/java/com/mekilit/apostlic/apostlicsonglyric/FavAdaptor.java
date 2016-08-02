package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Menasi on 6/28/2016.
 */
public class FavAdaptor extends ArrayAdapter<String> {
    ArrayList<String> SongName;
    ArrayList<String> ArtistName;
    ArrayList<Integer> AlbumArt;

    public FavAdaptor(Context context, ArrayList<String> songName,
                      ArrayList<String> artistName, ArrayList<Integer> albumArt
    ) {
        super(context, R.layout.custom_album, songName);
        this.ArtistName = artistName;
        this.SongName = songName;
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


        String SongName = this.SongName.get(position);
        String ArtistName = this.ArtistName.get(position);
        int AlbumArt = this.AlbumArt.get(position);


        holders.BigText.setText(SongName);
        holders.SmallText.setText(ArtistName);


        if (AlbumArt == 0)
            holders.albumArt.setImageResource(R.drawable.defultpic);
        else
            holders.albumArt.setImageResource(AlbumArt);

        return customView;
    }
}
