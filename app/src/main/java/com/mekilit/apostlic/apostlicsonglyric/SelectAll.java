package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

public class SelectAll extends ActionBarActivity implements AlbumListner {
    MyDbHandler helper = new MyDbHandler(this, null, null, 1);


    public SelectAll() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_all);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        setUpPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.appBar2);

        toolbar.setTitle("የፂሆን መዝሙሮች");
    }

    private void setUpPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new hebretFragmnt(), "የህብረት መዝሙሮች");
        adapter.addFragment(new artistFragment(), "ዘማርያን");
        adapter.addFragment(new favFragment(), "የተመረጡ መዝሙሮች");

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
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
            ;
        }
    }

}
