package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

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

        String ArtistName =  this.ArtistName.get(position);

        String AlbumCount = this.AlbumCount.get(position);


       holders.BigText.setText(ArtistName);
        holders.SmallText.setText(AlbumCount);
        int AlbumArt = this.AlbumArt.get(position);
        iv.setImageBitmap(null);
        DecodeTask task = new DecodeTask(iv,getContext());
        task.execute(AlbumArt /* File path to image */);
        iv.setTag(R.id.albumArt, task);




        return customView;
    }




}
