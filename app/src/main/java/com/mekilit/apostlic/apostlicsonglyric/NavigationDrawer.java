package com.mekilit.apostlic.apostlicsonglyric;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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
                getBoolean(readToPerfence(getActivity(), KEY_USER_LEARN_DRAWER, "false"));
        if (!mFromSavedIns) {
            mFromSavedIns = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
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
