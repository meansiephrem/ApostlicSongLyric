package com.mekilit.apostlic.apostlicsonglyric;


import android.app.Activity;
import android.app.ProgressDialog;
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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class artistFragment extends Fragment {


    AlbumListner albumListner;
    ListView listView;
    ProgressBar progressBar;


    public artistFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            albumListner = (AlbumListner) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString());
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final MyDbHandler helper = new MyDbHandler(getContext(), null, null, 1);
        View view = inflater.inflate(R.layout.fragment_album_fragmnt, container, false);
        listView = (ListView) view.findViewById(R.id.allLyric);
        progressBar=(ProgressBar) view.findViewById(R.id.progressBar);


        final ArrayList<String> listAlbum = helper.SelectAllArtist();

        new ArtistLoder().execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                albumListner.goToAlbum('D', listAlbum.get(position));
            }
        });
        return view;
    }

    public class ArtistLoder extends AsyncTask<Void, Integer, ListAdapter> {


        private final ProgressDialog dialog = new ProgressDialog(
                getContext());

        // can use UI thread here
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ListAdapter doInBackground(Void... params) {
            final MyDbHandler helper = new MyDbHandler(getContext(), null, null, 1);
            ArrayList<String> listAlbum = helper.SelectAllArtist();

            ArrayList<String> AlbumCount = new ArrayList();
            ;
            ArrayList<Integer> AlbumArt = new ArrayList();
            ;
            for (int i = 0; i < listAlbum.size(); i++) {
                String albumID = helper.getAlbumID(listAlbum.get(i));

                AlbumCount.add(helper.CountAlbumF(listAlbum.get(i)));
                AlbumArt.add(helper.getAlbumArt(albumID));

            }


            ListAdapter adapter = new ArtistAdapter(getContext(),listAlbum,AlbumCount,AlbumArt);
            Log.e("Artist Adaptor ", "Finished bulding the artist adaptor");

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
