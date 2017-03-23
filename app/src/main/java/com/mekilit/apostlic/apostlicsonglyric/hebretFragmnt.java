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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class hebretFragmnt extends Fragment {

    protected ApostolicSongs app;
    AlbumListner albumListner;
    ListView listView;
    ProgressBar progressBar;
    ArrayAdapter adapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            albumListner = (AlbumListner) activity;
            app = (ApostolicSongs)activity.getApplication();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString());
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_album_fragmnt, container, false);
        listView = (ListView) view.findViewById(R.id.allLyric);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        new HebretLoder().execute();

        return view;
    }

    @Override
    public void onStart() {


        if (app.getUpdateAlbum().equalsIgnoreCase("0"))
        {
            adapter.clear();
            new HebretLoder().execute();
            app.setUpdateAlbum("-1");
        }
        final MyDbHandler helper = new MyDbHandler(getContext());
        final ArrayList<String> listAlbum = helper.SelectAllhebret();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                albumListner.goToAlbum('A', listAlbum.get(position));
            }
        });

        super.onStart();
    }

    @SuppressWarnings("unchecked")
    public class HebretLoder extends AsyncTask<Void, Integer, ArrayAdapter> {

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayAdapter doInBackground(Void... params) {
            final MyDbHandler helper = new MyDbHandler(getContext());
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

             adapter = new HebretAdapter(getContext(),AlbumName,ArtistName,AlbumArt
                    ,listAlbum);
            Log.e("Artist Adaptor ", "Finished bulding the Hebret adaptor");

            return adapter;
        }

        @Override
        protected void onPostExecute(ArrayAdapter listAdapter) {
            listView.setAdapter(listAdapter);
            progressBar.setVisibility(View.GONE);
            super.onPostExecute(listAdapter);
        }
    }
}
