package com.mekilit.apostlic.apostlicsonglyric;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class favFragment extends Fragment {


    Context context;
    AlbumListner albumListner;
    ListView listView;
    ProgressBar progressBar;
    View view;
    TextView noFav;
    public favFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            albumListner =(AlbumListner) activity;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString());
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_album_fragmnt, container, false);
         listView =(ListView) view.findViewById(R.id.allLyric);
        progressBar=(ProgressBar) view.findViewById(R.id.progressBar);
        this.context=getContext();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        noFav = (TextView) view.findViewById(R.id.noFav);
        final MyDbHandler helper = new MyDbHandler(getContext(),null,null,1);
          final ArrayList<Integer> FavSongs =helper.getFav();


    new FavLoder().execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                albumListner.goToAlbum('C', "" + FavSongs.get(position));
            }
        });
    }

    public class FavLoder extends AsyncTask<Void, Integer, ListAdapter> {

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ListAdapter doInBackground(Void... params) {
            final MyDbHandler helper = new MyDbHandler(getContext(), null, null, 1);
            final ArrayList<Integer> listAlbum = helper.getFav();

            ArrayList<String> SongName = new ArrayList();
            ArrayList<String> ArtistName = new ArrayList();
            ArrayList<Integer> AlbumArt = new ArrayList();

            for (int i=0;i<listAlbum.size();i++)
            {
                int LyricId=listAlbum.get(i);
                String albumId = helper.getAlbumID(LyricId);

              SongName.add(helper.getSongName(LyricId+""));
                ArtistName.add(helper.getArtistName(albumId));
                AlbumArt.add(helper.getAlbumArt(albumId));

            }

            ListAdapter adapter = new FavAdaptor(context, SongName,ArtistName,
                    AlbumArt);
            Log.e("Fav Adaptor ", "Finished bulding the Fav adaptor");

            return adapter;
        }

        @Override
        protected void onPostExecute(ListAdapter listAdapter) {

            listView.setAdapter(listAdapter);
            if(listAdapter.getCount()==0)
                noFav.setText("ምንም የተመረጡ መዝሙሮች የሉም");
            else
                noFav.setText("");

            progressBar.setVisibility(View.GONE);
            super.onPostExecute(listAdapter);
        }
    }

}
