package com.mekilit.apostlic.apostlicsonglyric;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawer extends Fragment {

    public final static String PERF_FILE_NAME = "testperf";
    public final static String KEY_USER_LEARN_DRAWER = "uselernkey";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View viewContainer;
    private boolean mUserLearnNavBar;
    private boolean mFromSavedIns = false;

    public NavigationDrawer() {
        // Required empty public constructor
    }

    private static void writeToPerfence(Context context, String prefrenceName, String prefrenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences
                (PERF_FILE_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(prefrenceName, prefrenceValue);
        editor.apply();
    }

    private static String readToPerfence(Context context, String prefrenceName, String DefualtValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences
                (PERF_FILE_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(prefrenceName, DefualtValue);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnNavBar = Boolean.
                valueOf(readToPerfence(getActivity(), KEY_USER_LEARN_DRAWER, "false"));
        if (!mFromSavedIns) {
            mFromSavedIns = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MyDbHandler helper = new MyDbHandler(getActivity(), null, null, 1);
        View view = inflater.inflate(R.layout.nav_bar_header, container, false);
        ListView navListView = (ListView) view.findViewById(R.id.navList);
        TextView AllAlbum = (TextView) view.findViewById(R.id.totalAlbum);
        TextView AllArtist = (TextView) view.findViewById(R.id.totalSingers);
        TextView AllSong = (TextView) view.findViewById(R.id.totalSongs);

        AllAlbum.setText(helper.CountAlL('a'));
        AllArtist.setText(helper.CountAlL('c'));
        AllSong.setText(helper.CountAlL('b'));


        Integer[] array = {1, 2, 3, 4};
        navListView.setAdapter(new NavBarList(getContext(), array));

        navListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(getContext(), DisplayALbums.class);
                    intent.putExtra(Intent.EXTRA_TEXT, "1");
                    startActivity(intent);
                } else if (position == 3) {

                    System.exit(0);
                } else {
                    Toast.makeText(getContext(), position + "", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public void setUp(int viewById, DrawerLayout drawerLayout, Toolbar toolbar) {
        mDrawerLayout = drawerLayout;
        viewContainer = getActivity().findViewById(viewById);
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnNavBar) {
                    mUserLearnNavBar = true;
                    writeToPerfence(getActivity(), KEY_USER_LEARN_DRAWER, mUserLearnNavBar + "");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }


        };
        if (!mUserLearnNavBar && !mFromSavedIns)
            mDrawerToggle.onDrawerOpened(viewContainer);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

}
