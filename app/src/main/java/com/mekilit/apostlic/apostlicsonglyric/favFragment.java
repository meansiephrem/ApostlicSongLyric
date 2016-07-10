package com.mekilit.apostlic.apostlicsonglyric;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class favFragment extends Fragment {


    public favFragment() {
        // Required empty public constructor
    }

    AlbumListner albumListner;

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

    ListView listView;
    ArrayList<Integer> listFav;
    View view;
    TextView noFav;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final MyDbHandler helper = new MyDbHandler(getContext(),null,null,1);
        view= inflater.inflate(R.layout.fragment_album_fragmnt, container, false);
         listView =(ListView) view.findViewById(R.id.allLyric);
         listFav = helper.getFav();


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        noFav = (TextView) view.findViewById(R.id.noFav);
        final MyDbHandler helper = new MyDbHandler(getContext(),null,null,1);
       final ArrayList<Integer> FavSongs =helper.getFav();

        FavAdaptor adapter = new FavAdaptor(getContext(),FavSongs);
        listView.setAdapter(adapter);
        if(adapter.getCount()==0)
            noFav.setText("ምንም የተመረጡ መዝሙሮች የሉም");
        else
            noFav.setText("");


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                albumListner.goToAlbum('C', "" + FavSongs.get(position));
            }
        });
    }


}
