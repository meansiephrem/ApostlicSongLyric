package com.mekilit.apostlic.apostlicsonglyric;


import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 */
public class hebretFragmnt extends Fragment {

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final MyDbHandler helper = new MyDbHandler(getContext(),null,null,1);
        View view= inflater.inflate(R.layout.fragment_album_fragmnt,container,false);
        ListView listView =(ListView) view.findViewById(R.id.allLyric);
        final ArrayList<String> listAlbum = helper.SelectAllhebret();
        ListAdapter adapter = new HebretAdapter(getContext(),listAlbum);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                albumListner.goToAlbum('A',listAlbum.get(position));
            }
        });
        return view;
    }
}
