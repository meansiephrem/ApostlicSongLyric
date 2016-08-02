package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Menasi on 6/23/2016.
 */
public class ArtistAdapter extends ArrayAdapter<String> {

    ArrayList<String> ArtistName;

    ArrayList<String> AlbumCount;
    ArrayList<Integer> AlbumArt;

    public ArtistAdapter(Context context, ArrayList<String> objects,
                         ArrayList<String> albumCount,ArrayList<Integer> albumArt)  {
        super(context, R.layout.custom_album, objects);
        this.ArtistName = objects;

        this.AlbumCount=albumCount;
        this.AlbumArt=albumArt;

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

        String ArtistName =  this.ArtistName.get(position);

        String AlbumCount = this.AlbumCount.get(position);


       holders.BigText.setText(ArtistName);
        holders.SmallText.setText(AlbumCount);
        int AlbumArt = this.AlbumArt.get(position);

        if (AlbumArt == 0)
            holders.albumArt.setImageResource(R.drawable.defultpic);
        else
            holders.albumArt.setImageResource(AlbumArt);


        return customView;
    }



}
