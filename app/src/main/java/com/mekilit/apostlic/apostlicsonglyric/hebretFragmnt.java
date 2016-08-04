package com.mekilit.apostlic.apostlicsonglyric;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class hebretFragmnt extends Fragment {

    AlbumListner albumListner;
    ListView listView;
    ProgressBar progressBar;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            albumListner = (AlbumListner) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString());
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final MyDbHandler helper = new MyDbHandler(getContext(), null, null, 1);
        View view = inflater.inflate(R.layout.fragment_album_fragmnt, container, false);
        listView = (ListView) view.findViewById(R.id.allLyric);
        final ArrayList<String> listAlbum = helper.SelectAllhebret();
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        new HebretLoder().execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                albumListner.goToAlbum('A', listAlbum.get(position));
            }
        });
        return view;
    }

    public class HebretLoder extends AsyncTask<Void, Integer, ListAdapter> {

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ListAdapter doInBackground(Void... params) {
            final MyDbHandler helper = new MyDbHandler(getContext(), null, null, 1);
            final ArrayList<String> listAlbum = helper.SelectAllhebret();

            ArrayList<String> ArtistName = new ArrayList();
            ArrayList<String> AlbumName = new ArrayList();
            ArrayList<Integer> AlbumArt = new ArrayList();

            for (int i = 0; i < listAlbum.size(); i++) {
                String albumID = listAlbum.get(i);

                ArtistName.add(helper.getArtistName(albumID));
                AlbumName.add(helper.getAlbumName(albumID));
                AlbumArt.add(helper.getAlbumArt(albumID));

            }

            ListAdapter adapter = new HebretAdapter(getContext(),AlbumName,ArtistName,AlbumArt);
            Log.e("Artist Adaptor ", "Finished bulding the Hebret adaptor");

            return adapter;
        }

        @Override
        protected void onPostExecute(ListAdapter listAdapter) {
            listView.setAdapter(listAdapter);
            progressBar.setVisibility(View.GONE);
            super.onPostExecute(listAdapter);
        }
    }
}
