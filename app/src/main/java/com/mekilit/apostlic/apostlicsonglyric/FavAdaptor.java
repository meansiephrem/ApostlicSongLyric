package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Menasi on 6/28/2016.
 */
public class FavAdaptor extends ArrayAdapter<String> {
    ArrayList<String> SongName;
    ArrayList<String> ArtistName;
    ArrayList<String> AlbumID;
    ArrayList<Integer> AlbumArt;

    public FavAdaptor(Context context, ArrayList<String> songName,
                      ArrayList<String> artistName, ArrayList<Integer> albumArt,
                      ArrayList<String> albumID
    ) {
        super(context, R.layout.custom_album, songName);
        this.ArtistName = artistName;
        this.SongName = songName;
        this.AlbumArt = albumArt;
        this.AlbumID= albumID;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View customView = convertView;
        ViewHolders holders = null;
        ImageView iv = null;
        if (customView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            customView = inflater.inflate(R.layout.custom_album, parent, false);
            holders = new ViewHolders(customView);
            iv = (ImageView)customView.findViewById(R.id.albumArt);
            customView.setTag(holders);
        } else {
            holders = (ViewHolders) customView.getTag();
            iv = (ImageView) convertView.findViewById(R.id.albumArt);
            DecodeTask task = (DecodeTask)iv.getTag(R.id.albumArt);
            if(task != null) {
                task.cancel(true);
            }
        }

        String SongName = this.SongName.get(position);
        String ArtistName = this.ArtistName.get(position);
        String AlbumID = this.AlbumID.get(position);
        int AlbumArt = this.AlbumArt.get(position);


        holders.BigText.setText(SongName);
        holders.SmallText.setText(ArtistName);

        iv.setImageBitmap(null);
        DecodeTask task = new DecodeTask(iv,getContext(),AlbumID);
        task.execute(AlbumArt /* File path to image */);
        iv.setTag(R.id.albumArt, task);



        return customView;
    }
}
