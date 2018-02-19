package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

/**
 * Created by Menasi on 3/16/2017.
 */

public class SyncAdapter extends ArrayAdapter<Album> {

    ArrayList<Album> album;
    MySingleton singleton;
    public SyncAdapter(Context context, ArrayList<Album> album)  {
        super(context, R.layout.sync_adapter, album);
        this.album = album;
        singleton =MySingleton.getInstance(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View customView = convertView;
        ViewHolders holders = null;
        NetworkImageView iv = null;
        if (customView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            customView = inflater.inflate(R.layout.sync_adapter, parent, false);
            holders = new ViewHolders(customView);
            iv = (NetworkImageView) customView.findViewById(R.id.syncAdapteALbumArt);
            customView.setTag(holders);
        } else {
            holders = (ViewHolders) customView.getTag();
            iv = (NetworkImageView) convertView.findViewById(R.id.syncAdapteALbumArt);


        }
        holders.BigText.setText(album.get(position).getAlbum_Title());
        holders.SmallText.setText(album.get(position).getAlbum_Artist());
        String imgUrl =getContext().getResources().getString(R.string.url)+
                album.get(position).getAlbum_Art();
        iv.setImageUrl(imgUrl,singleton.getmImageLoader());

        return customView;
    }

}
