package com.mekilit.apostlic.apostlicsonglyric;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class artistFragment extends Fragment {



    AlbumListner albumListner;


    public artistFragment() {
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
        final MyDbHandler helper = new MyDbHandler(getContext(),null,null,1);
        View view= inflater.inflate(R.layout.fragment_album_fragmnt, container, false);
        ListView listView =(ListView) view.findViewById(R.id.allLyric);
        final ArrayList<String> listAlbum = helper.SelectAllArtist();

        ArtistLoder loder = new ArtistLoder();
        loder.execute();
        try {
            listView.setAdapter(loder.get()) ;
        }catch (Exception e)
        {};

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                albumListner.goToAlbum('D',listAlbum.get(position));
            }
        });
        return view;
    }

    public class ArtistLoder extends AsyncTask<Void,Integer,ListAdapter> {
        @Override
        protected ListAdapter doInBackground(Void... params) {
            final MyDbHandler helper = new MyDbHandler(getContext(), null, null, 1);
            ArrayList<String> listAlbum = helper.SelectAllArtist();
            final String[] albumsArtist = new String[listAlbum.size()];
            for (int i = 0; i < listAlbum.size(); i++) {
                albumsArtist[i] = listAlbum.get(i);
            }
            ListAdapter adapter = new ArtistAdapter(getContext(), albumsArtist);

            return adapter;

        }




    }

}
