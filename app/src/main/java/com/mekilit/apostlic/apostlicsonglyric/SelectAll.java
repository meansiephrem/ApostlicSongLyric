package com.mekilit.apostlic.apostlicsonglyric;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

        toolbar = (Toolbar) findViewById(R.id.appBar2);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(app.color);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SetUpNav();
        getSupportActionBar().setTitle("የጽዮን መዝሙሮች");
        timeInMillsec = 0;

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

            Intent intt = new Intent(SelectAll.this, Lyric.class)
                    .putExtra(Intent.EXTRA_TEXT, name);
            startActivity(intt);

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
}
