package com.mekilit.apostlic.apostlicsonglyric.album;

import android.content.Context;
import android.graphics.Color;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mekilit.apostlic.apostlicsonglyric.application.ApostolicSongs;
import com.mekilit.apostlic.apostlicsonglyric.utils.DecodeTask;
import com.mekilit.apostlic.apostlicsonglyric.R;

import java.util.ArrayList;


/**
 * Created by Menasi on 6/28/2016.
 */
public class AlbumAdaptor extends
        RecyclerView.Adapter<AlbumAdaptor.ViewHoler>
{
    private ArrayList<String> AlbumName;
    private ArrayList<String> AlbumId;
    private ArrayList<String> ArtistName;
    private ArrayList<Integer> AlbumArt;
    private Context context;

    public AlbumAdaptor(Context context, ArrayList<String> albumName,ArrayList<String> artistName,
                        ArrayList<Integer> albumArt,ArrayList<String> albumId) {
        this.context = context;
        this.AlbumName = albumName;
        this.ArtistName= artistName;
        this.AlbumId=albumId;
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
        String AlbumId = this.AlbumId.get(position);
        String ArtistName = this.ArtistName.get(position);
        int AlbumArt = this.AlbumArt.get(position);




        holder.AlbumName.setText(AlbumName);
         holder.ArtistName.setText(ArtistName);
        DecodeTask task = new DecodeTask(imageView,getContext(),AlbumId);

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
        public CardView cardView;

        public ViewHoler(View ItemView)
        {
            super(ItemView);
            imageView = (ImageView) ItemView.findViewById(R.id.CardAlbumArt);
            AlbumName = (TextView) ItemView.findViewById(R.id.CardAlbumName);
            ArtistName = (TextView) ItemView.findViewById(R.id.CardAlbumArtist);
            cardView   = (CardView) ItemView.findViewById(R.id.cv);

            if (ApostolicSongs.theme == R.style.AppTheme_Black) {
                RelativeLayout layout = (RelativeLayout)
                        ItemView.findViewById(R.id.card_view_relative_layout);
                layout.setBackgroundColor(Color.BLACK);
                ArtistName.setTextColor(Color.WHITE);
                AlbumName.setTextColor(Color.WHITE);
            }
        }
    }
}
