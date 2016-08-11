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
        ImageView imageView = holder.imageView;
        imageView.setImageDrawable(null);
        String AlbumName = this.AlbumName.get(position);
        String ArtistName = this.ArtistName.get(position);
        int AlbumArt = this.AlbumArt.get(position);




        holder.AlbumName.setText(AlbumName);
         holder.ArtistName.setText(ArtistName);
        DecodeTask task = new DecodeTask(imageView,getContext());

        task.execute(AlbumArt /* File path to image */);







    }

    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View customView = inflater.inflate(R.layout.album_card_layout, parent, false);

        return new ViewHoler(customView);
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
