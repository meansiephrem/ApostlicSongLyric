package com.mekilit.apostlic.apostlicsonglyric.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageView;

import com.mekilit.apostlic.apostlicsonglyric.R;
import com.mekilit.apostlic.apostlicsonglyric.utils.ViewHolders;
import com.mekilit.apostlic.apostlicsonglyric.album.Album;
import com.mekilit.apostlic.apostlicsonglyric.application.MySingleton;

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
        ImageView iv = null;
        if (customView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            customView = inflater.inflate(R.layout.sync_adapter, parent, false);
            holders = new ViewHolders(customView);
            iv = (ImageView) customView.findViewById(R.id.syncAdapteALbumArt);
            customView.setTag(holders);
        } else {
            holders = (ViewHolders) customView.getTag();
            iv = (ImageView) convertView.findViewById(R.id.syncAdapteALbumArt);


        }
        holders.BigText.setText(album.get(position).getAlbum_Title());
        holders.SmallText.setText(album.get(position).getAlbum_Artist());
        String imgUrl =getContext().getResources().getString(R.string.url)+
                album.get(position).getAlbum_Art();


        return customView;
    }

}
