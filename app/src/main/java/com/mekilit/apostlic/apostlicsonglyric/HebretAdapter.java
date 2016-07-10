package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
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
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_album, parent, false);

        String albumID = getItem(position);
        String ArtistName = helper.getArtistName(albumID);
        String AlbumName = helper.getAlbumName(albumID);



        ImageView albumArt = (ImageView) customView.findViewById(R.id.albumArt);
        TextView albumName = (TextView) customView.findViewById(R.id.BigText);
        TextView artistName = (TextView) customView.findViewById(R.id.SmallText);

        albumName.setText(AlbumName);
        artistName.setText(ArtistName);
        try {
            String name = albumID.toLowerCase();
            Class res = R.drawable.class;
            Field field = res.getField(name);
            int res1 =field.getInt(null);
            albumArt.setImageResource(res1);
        }catch (Exception e)
        {
            albumArt.setImageResource(R.drawable.defultpic);
        }

        return customView;
    }}