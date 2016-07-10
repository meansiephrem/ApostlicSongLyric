package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Menasi on 7/4/2016.
 */
public class SongAdaptor extends ArrayAdapter<String > {

    public SongAdaptor(Context context, ArrayList<String> objects) {
        super(context, R.layout.song_adaptor, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyDbHandler helper = new MyDbHandler(getContext(),null,null,1);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.song_adaptor, parent, false);

        TextView SongNumber = (TextView) customView.findViewById(R.id.SongNumber);
        TextView SongTitle = (TextView) customView.findViewById(R.id.SongTitle);
        ImageView isFav = (ImageView) customView.findViewById(R.id.SongFav);

        SongNumber.setText(""+(position+1));
        SongTitle.setText(helper.getSongName(getItem(position)));
        if (helper.isFav(getItem(position)))
            isFav.setImageResource(android.R.drawable.btn_star_big_on);
           else
            isFav.setImageResource(android.R.drawable.btn_star_big_off);

    return customView;
    }
}
