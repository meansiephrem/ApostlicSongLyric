package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * Created by Menasi on 6/28/2016.
 */
public class AlbumAdaptor extends
        RecyclerView.Adapter<AlbumAdaptor.ViewHoler>
{
    public static class ViewHoler extends RecyclerView.ViewHolder
    {
        public ImageView imageView;
        public TextView AlbumName;
        public TextView ArtistName;

        public ViewHoler(View ItemView)
        {
            super(ItemView);
            imageView = (ImageView) ItemView.findViewById(R.id.CardAlbumArt);
            AlbumName = (TextView) ItemView.findViewById(R.id.CardAlbumName);
            ArtistName = (TextView) ItemView.findViewById(R.id.CardAlbumArtist);
        }
    }

    private ArrayList<String> albums;
    private Context context;

    public AlbumAdaptor(Context context, ArrayList<String> albums) {
        this.context = context;
        this.albums = albums;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        final MyDbHandler helper = new MyDbHandler(getContext(),null,null,1);
        String AlbumID = albums.get(position);
        String AlbumName = helper.getAlbumName(AlbumID);
        String ArtistName = helper.getArtistName(AlbumID);



        ImageView albumArt = holder.imageView;
        TextView albumName = holder.AlbumName;
        TextView artistName = holder.ArtistName;
        albumName.setText(AlbumName);
        artistName.setText(ArtistName);

        try {
            String name = AlbumID.toLowerCase();
            Class res = R.drawable.class;
            Field field = res.getField(name);
            int res1 =field.getInt(null);
            albumArt.setImageResource(res1);
        }catch (Exception e)
        {
            albumArt.setImageResource(R.drawable.defultpic);
        }

    }

    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View customView = inflater.inflate(R.layout.album_card_layout, parent, false);
        ViewHoler viewHoler = new ViewHoler(customView);
        return viewHoler;
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }
}
