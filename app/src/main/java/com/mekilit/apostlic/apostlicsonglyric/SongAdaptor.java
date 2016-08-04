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
public class SongAdaptor extends ArrayAdapter<String> {
    ArrayList<Boolean> IsFav;

    public SongAdaptor(Context context, ArrayList<String> SongName, ArrayList<Boolean> isFav) {
        super(context, R.layout.song_adaptor, SongName);
        this.IsFav = isFav;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View customView = convertView;
        ViewHOOlder viewHOOlder=null;
        if (customView==null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
             customView = inflater.inflate(R.layout.song_adaptor, parent, false);
            viewHOOlder= new ViewHOOlder(customView);
            customView.setTag(viewHOOlder);
        }
        else {
            viewHOOlder = (ViewHOOlder)customView.getTag();
        }

        viewHOOlder.SongNumber.setText("" + (position + 1));
        viewHOOlder.SongTitle.setText(getItem(position));
        if (IsFav.get(position))
            viewHOOlder.isFav.setImageResource(android.R.drawable.btn_star_big_on);
        else
            viewHOOlder.isFav.setImageResource(android.R.drawable.btn_star_big_off);

        return customView;
    }

    class ViewHOOlder {
        TextView SongNumber;
        TextView SongTitle;
        ImageView isFav;

        ViewHOOlder(View customView) {
            SongNumber = (TextView) customView.findViewById(R.id.SongNumber);
            SongTitle = (TextView) customView.findViewById(R.id.SongTitle);
            isFav = (ImageView) customView.findViewById(R.id.SongFav);
        }
    }
}
