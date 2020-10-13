package com.mekilit.apostlic.apostlicsonglyric.mainpage;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mekilit.apostlic.apostlicsonglyric.R;
import com.mekilit.apostlic.apostlicsonglyric.application.ApostolicSongs;
import com.mekilit.apostlic.apostlicsonglyric.lyrics.Lyric;
import com.mekilit.apostlic.apostlicsonglyric.utils.MyDbHandler;

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
        setTheme(ApostolicSongs.theme);
        setContentView(R.layout.activity_searchable);
        textView = (TextView) findViewById(R.id.search_no_result);
        listView = (ListView) findViewById(R.id.search_result_list);

        toolbar = (Toolbar) findViewById(R.id.appBar);
        toolbar.setBackgroundColor(ApostolicSongs.color);
        toolbar.setTitleTextColor(ApostolicSongs.toolbarColor);
        toolbar.setSubtitleTextColor(ApostolicSongs.toolbarColor);

        if(ApostolicSongs.theme == R.style.AppTheme_Black){

            RelativeLayout layout = (RelativeLayout)findViewById(R.id.activity_searchable);
            layout.setBackgroundColor(Color.BLACK);

        }

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
            ArrayList<String> AlbumID = new ArrayList();
            ArrayList<Integer> AlbumArt = new ArrayList();

            for (int i=0;i<listAlbum.size();i++)
            {
                int LyricId=listAlbum.get(i);
                String albumId = helper.getAlbumID(LyricId);
                AlbumID.add(albumId);
                SongName.add(helper.getSongName(LyricId+""));
                ArtistName.add(helper.getArtistName(albumId));
                AlbumArt.add(helper.getAlbumArt(albumId));

            }

            ListAdapter adapter = new FavAdaptor(context,SongName,ArtistName,AlbumArt,AlbumID);
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
