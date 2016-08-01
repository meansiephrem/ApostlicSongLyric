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
public class FavAdaptor  extends ArrayAdapter<Integer>
{
    public FavAdaptor(Context context, ArrayList<Integer> objects) {
        super(context, R.layout.custom_album, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View customView = convertView;
        ViewHolders holders = null;
        MyDbHandler helper = new MyDbHandler(getContext(), null, null, 1);
        if (customView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            customView = inflater.inflate(R.layout.custom_album, parent, false);
            holders = new ViewHolders(customView);
            customView.setTag(holders);
        } else {
            holders = (ViewHolders) customView.getTag();
        }

        int LyricSongID = getItem(position);//find the lyric id
        String albumID = helper.getAlbumID(LyricSongID);//find the crossponding album id
        String SongName = helper.getSongName(LyricSongID + "" );//find the song name
        String ArtistName = helper.getArtistName(albumID); //find the artist Name




        holders.BigText.setText(ArtistName);
        holders.SmallText.setText(SongName);
        int AlbumArt = helper.getAlbumArt(albumID);

        if (AlbumArt == 0)
            holders.albumArt.setImageResource(R.drawable.defultpic);
        else
            holders.albumArt.setImageResource(AlbumArt);

        return customView;
    }
}
