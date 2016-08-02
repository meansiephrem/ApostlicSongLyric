package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Menasi on 6/28/2016.
 */
public class AlbumAdaptor extends
        RecyclerView.Adapter<AlbumAdaptor.ViewHoler>
{
    private ArrayList<String> AlbumName;
    private ArrayList<String> ArtistName;
    private ArrayList<Integer> AlbumArt;
    private Context context;
    public AlbumAdaptor(Context context, ArrayList<String> albumName,ArrayList<String> artistName,
                        ArrayList<Integer> albumArt) {
        this.context = context;
        this.AlbumName = albumName;
        this.ArtistName= artistName;
        this.AlbumArt=albumArt;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {


        String AlbumName = this.AlbumName.get(position);
        String ArtistName = this.ArtistName.get(position);
        int AlbumArt = this.AlbumArt.get(position);



        ImageView albumArt = holder.imageView;
        TextView albumName = holder.AlbumName;
        TextView artistName = holder.ArtistName;
        albumName.setText(AlbumName);
        artistName.setText(ArtistName);



        if (AlbumArt == 0)
            albumArt.setImageResource(R.drawable.defultpic);
        else
            albumArt.setImageResource(AlbumArt);


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
        return AlbumName.size();
    }

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
}
