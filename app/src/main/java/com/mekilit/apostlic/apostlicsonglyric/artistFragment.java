package com.mekilit.apostlic.apostlicsonglyric;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
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
public class artistFragment extends Fragment {


    protected ApostolicSongs app;
    AlbumListner albumListner;
    ListView listView;
    ProgressBar progressBar;
    ArrayAdapter adapter;



    public artistFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            albumListner = (AlbumListner) activity;
            app = (ApostolicSongs) activity.getApplication();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString());
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_album_fragmnt, container, false);
        listView = (ListView) view.findViewById(R.id.allLyric);
        progressBar=(ProgressBar) view.findViewById(R.id.progressBar);




        new ArtistLoder().execute();


        return view;
    }

    @Override
    public void onStart() {


        if ((app.getUpdateAlbum().contains("1")||app.getUpdateAlbum().contains("3"))&&adapter!=null)
        {
            adapter.clear();
            new ArtistLoder().execute();
            if (app.getUpdateAlbum().contains("1"))
                app.setUpdateAlbum("-8");
            else
                app.setUpdateAlbum("0");
        }

        final MyDbHandler helper = new MyDbHandler(getContext());
        final ArrayList<String> listAlbum = helper.SelectAllArtist();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                albumListner.goToAlbum('D', listAlbum.get(position));
            }
        });

        super.onStart();
    }

    @SuppressWarnings("unchecked")
    public class ArtistLoder extends AsyncTask<Void, Integer, ArrayAdapter> {


        // can use UI thread here
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayAdapter doInBackground(Void... params) {
            final MyDbHandler helper = new MyDbHandler(getContext());
            ArrayList<String> listAlbum = helper.SelectAllArtist();
            ArrayList<String> AlbumId = new ArrayList();
            ArrayList<String> AlbumCount = new ArrayList();
            ArrayList<Integer> AlbumArt = new ArrayList();
            for (int i = 0; i < listAlbum.size(); i++) {
                String albumID = helper.getAlbumID(listAlbum.get(i));
                AlbumId.add(albumID);
                AlbumCount.add(helper.CountAlbumF(listAlbum.get(i)));
                AlbumArt.add(helper.getAlbumArt(albumID));

            }



             adapter = new ArtistAdapter(getContext(),listAlbum,AlbumCount,
                                        AlbumArt,AlbumId);
            Log.e("Artist Adaptor ", "Finished bulding the artist adaptor");

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
