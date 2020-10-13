package com.mekilit.apostlic.apostlicsonglyric.mainpage;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import com.google.android.material.tabs.TabLayout;
import androidx.core.view.MenuItemCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.Toast;

import com.mekilit.apostlic.apostlicsonglyric.R;
import com.mekilit.apostlic.apostlicsonglyric.lyrics.Song;
import com.mekilit.apostlic.apostlicsonglyric.utils.ViewPagerAdapter;
import com.mekilit.apostlic.apostlicsonglyric.album.DisplayALbums;
import com.mekilit.apostlic.apostlicsonglyric.album.OneAlbum;
import com.mekilit.apostlic.apostlicsonglyric.application.ApostolicSongs;
import com.mekilit.apostlic.apostlicsonglyric.lyrics.Lyric;
import com.mekilit.apostlic.apostlicsonglyric.settings.NavigationDrawer;
import com.mekilit.apostlic.apostlicsonglyric.utils.MyDbHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectAll extends AppCompatActivity implements AlbumListner {
    MyDbHandler helper = new MyDbHandler(this);
    Toolbar toolbar;
    ApostolicSongs app ;
    private long timeInMillsec;


    public SelectAll() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        app = (ApostolicSongs)getApplication();

        super.onCreate(savedInstanceState);
        setTheme(app.theme);
        setContentView(R.layout.activity_select_all);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        setUpPager(viewPager);

        tabLayout.setBackgroundColor(app.color);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(ApostolicSongs.toolbarColor,ApostolicSongs.toolbarColor);

        ViewStub sub = (ViewStub) findViewById(R.id.appBar2);
        if (ApostolicSongs.toolbarColor == Color.BLACK)
            sub.setLayoutResource(R.layout.app_bar);
        else
            sub.setLayoutResource(R.layout.app_bar_white);

        toolbar = (Toolbar) sub.inflate();
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(app.color);
        toolbar.setTitleTextColor(ApostolicSongs.toolbarColor);

        String path  = getResources().getString(R.string.path);
        String abous = Environment.getExternalStorageDirectory().getAbsolutePath();

        Log.d("Path","path "+path+ " absoul"+ abous);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SetUpNav();
        getSupportActionBar().setTitle("የጽዮን መዝሙሮች");
        timeInMillsec = 0;

        String date_created = app.ReadFromFile(this,"LAST_MODIFIED","2018-01-09T18:13:52.000Z");
        String url = getResources().getString(R.string.url)+"/lyrics/?" +
                "[query][last_modified][$gt]="+date_created;

    }

    private void setUpPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new hebretFragmnt(), "የህብረት መዝሙሮች");
        adapter.addFragment(new artistFragment(), "ዘማርያን");
        adapter.addFragment(new favFragment(), "የተመረጡ መዝሙሮች");

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

    }

    private void SetUpNav() {

        NavigationDrawer navigationDrawer = (NavigationDrawer) getSupportFragmentManager().
                findFragmentById(R.id.Navfragmnt);
        navigationDrawer.setUp(R.id.Navfragmnt, (DrawerLayout) findViewById(R.id.DrawerLayout),
                toolbar);

    }

    public void goToAlbum(char select, String name) {
        if (select == 'C') {

            Intent intent = new Intent(SelectAll.this, Lyric.class)
                    .putExtra(Intent.EXTRA_TEXT, name);
            startActivity(intent);

        } else if (select == 'D') {
            //   form all artists to display all albums of that artist
            Intent intent = new Intent(SelectAll.this, DisplayALbums.class)
                    .putExtra(Intent.EXTRA_TEXT, 0 + "" + name);
            startActivity(intent);

        } else {
            Intent intent = new Intent(SelectAll.this, OneAlbum.class).
                    putExtra(Intent.EXTRA_TEXT, name);
            startActivity(intent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        Context context = getApplicationContext();
        getMenuInflater().inflate(R.menu.menu_lyric,menu);
        MenuItem searchMenu = menu.findItem(R.id.action_search);

        if (ApostolicSongs.toolbarColor == Color.WHITE )
            searchMenu.setIcon(getResources().getDrawable(R.drawable.ic_search_white_24dp));

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        ComponentName componentName = new ComponentName(context,Searchable.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));

        return true;

    }

    @Override
    public void onBackPressed() {

        if((System.currentTimeMillis()-timeInMillsec)<= 2000)
        super.onBackPressed();
        else {
            timeInMillsec = System.currentTimeMillis();
            Toast.makeText(this, "ለመውጣት ድጋሚ ይጫኑ", Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<Song> JsonToSongList(JSONObject rawJsonStr) {
        ArrayList<Song> song = new ArrayList();
        JSONObject jsonObject = rawJsonStr;
        String Last_modified =app.ReadFromFile(this,"LAST_MODIFIED","2018-01-09T18:13:52.000Z"); ;
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("docs");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jobj = jsonArray.getJSONObject(i);
                song.add(new Song(jobj.getString("Lyric_Title"),jobj.getString("Album_id")
                        ,jobj.getString("Lyric_Text")));
                Last_modified = jobj.getString("last_modified");
            }

            app.saveToFile(this,"LAST_MODIFIED",Last_modified);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return song;
    }
}
