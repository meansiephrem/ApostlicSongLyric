package com.mekilit.apostlic.apostlicsonglyric;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class Searchable extends AppCompatActivity {

    TextView textView;
    ListView listView;
    Toolbar  toolbar;
    ProgressBar progressBar;
    String query;
    MyDbHandler helper = new MyDbHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        textView = (TextView) findViewById(R.id.search_no_result);
        listView = (ListView) findViewById(R.id.search_result_list);
        toolbar = (Toolbar) findViewById(R.id.appBar);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);

        Intent intent = getIntent();

        if(Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            query = intent.getStringExtra(SearchManager.QUERY);

            final ArrayList<Integer> SongsFound =helper.SearchQuery(query);

            new SearchLoder().execute();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.e("id",""+SongsFound.get(position));
                    Intent intt = new Intent(Searchable.this, Lyric.class)
                            .putExtra(Intent.EXTRA_TEXT, SongsFound.get(position)+"");
                    startActivity(intt);
                }
            });
        }
    }





    public class SearchLoder extends AsyncTask<Void, Integer, ListAdapter> {

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ListAdapter doInBackground(Void... params) {
            Context context = getBaseContext();
            final MyDbHandler helper = new MyDbHandler(context);
            final ArrayList<Integer> listAlbum = helper.SearchQuery(query);

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

            ListAdapter adapter = new FavAdaptor(context,SongName,ArtistName,AlbumArt);
            Log.e("Search ", "Finished building the Search adaptor");

            return adapter;
        }

        @Override
        protected void onPostExecute(ListAdapter listAdapter) {

            toolbar.setTitle(query);

            if(listAdapter.getCount()==1)
                toolbar.setSubtitle("1 ውጤት");
            else
                toolbar.setSubtitle( listAdapter.getCount()+ " ውጤቶች");
            listView.setAdapter(listAdapter);
            if(listAdapter.getCount()==0)
                textView.setText("ምንም አልተገኝም");
            else
                textView.setText("");

            progressBar.setVisibility(View.GONE);
            super.onPostExecute(listAdapter);
        }
    }
}