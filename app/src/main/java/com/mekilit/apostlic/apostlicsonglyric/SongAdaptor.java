package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Menasi on 7/4/2016.
 */
public class SongAdaptor extends ArrayAdapter<String> {
    ArrayList<Boolean> IsFav;
    ArrayList<String> Songs;
    int bgColor , txtColor;

    public SongAdaptor(Context context, ArrayList<String> SongName, ArrayList<Boolean> isFav,
                       ArrayList<String> songs) {

        super(context, R.layout.song_adaptor, SongName);
        this.IsFav = isFav;
        this.Songs=songs;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View customView = convertView;
        ViewHOOlder viewHOOlder = null;

        if (customView == null) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            customView              = inflater.inflate(R.layout.song_adaptor, parent, false);
            viewHOOlder             = new ViewHOOlder(customView);

            customView.setTag(viewHOOlder);

            if (ApostolicSongs.theme == R.style.AppTheme_Black){

                bgColor     = Color.BLACK;
                txtColor    = Color.WHITE;
                viewHOOlder.layout.setBackgroundColor(bgColor);
                viewHOOlder.SongNumber.setTextColor(txtColor);
                viewHOOlder.SongTitle.setTextColor(txtColor);

            }

        }
        else {

            viewHOOlder = (ViewHOOlder) customView.getTag();

        }

        final ViewHOOlder viewHOOlder1 =viewHOOlder;
        viewHOOlder.SongNumber.setText("" + (position + 1));
        viewHOOlder.SongTitle.setText(getItem(position));

        if (IsFav.get(position)) {

            viewHOOlder.isFav.setImageResource(android.R.drawable.btn_star_big_on);
            viewHOOlder.isFav.setSelected(true);

        }
        else {

            viewHOOlder.isFav.setImageResource(android.R.drawable.btn_star_big_off);
            viewHOOlder.isFav.setSelected(false);

        }


        viewHOOlder.isFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyDbHandler helper = new MyDbHandler(getContext());
                if (! viewHOOlder1.isFav.isSelected()) {

                     viewHOOlder1.isFav.setImageResource(android.R.drawable.btn_star_big_on);
                     viewHOOlder1.isFav.setSelected(true);

                    Toast tost = Toast.makeText(getContext(),
                            "መዝሙር "+(position+1)+" የተመረጡ ዝርዝር ውስጥ ተካቷል",
                            Toast.LENGTH_SHORT);

                    tost.show();
                    helper.ChangeFavStat(false,Integer.parseInt(Songs.get(position)));

                } else {

                    viewHOOlder1.isFav.setImageResource(android.R.drawable.btn_star_big_off);
                    viewHOOlder1.isFav.setSelected(false);

                    Toast tost = Toast.makeText(getContext(),
                            "መዝሙር "+(position+1)+ "ከተመረጡ ዝርዝር ውስጥ ተወግዶል",
                            Toast.LENGTH_SHORT);

                    tost.show();
                    helper.ChangeFavStat(true, Integer.parseInt(Songs.get(position)));

                }
            }
        });


        return customView;
    }

    class ViewHOOlder {
        TextView SongNumber;
        TextView SongTitle;
        ImageView isFav;
        LinearLayout layout;

        ViewHOOlder(View customView) {
            SongNumber  = (TextView) customView.findViewById(R.id.SongNumber);
            SongTitle   = (TextView) customView.findViewById(R.id.SongTitle);
            isFav       = (ImageView) customView.findViewById(R.id.SongFav);
            layout      = (LinearLayout) customView.findViewById(R.id.song_adaptor_liner_layout);
        }
    }
}
