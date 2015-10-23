package rccorp.musicx;

import android.app.ActionBar;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import rccorp.musicx.Adapters.TabsPagerAdapter;
import rccorp.musicx.Fragments.FolderFragment;


public class Homescreen extends AppCompatActivity implements
         android.support.v7.app.ActionBar.TabListener {

    private MusicService musicSrv;
    private Intent playIntent;
    private boolean musicBound=false;
    private ArrayList<Song> songList;




    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private android.support.v7.app.ActionBar actionBar;

    private String[] tabs = { "Album", "Tracks", "Folders", "Playlist" };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.MyAppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getSupportActionBar();
        viewPager.setOffscreenPageLimit(5);

        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());


        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

        });

    }


    @Override
    public void onBackPressed() {
        Fragment currentFragment = mAdapter.getCurrentFragment();
        if(currentFragment instanceof FolderFragment) {
            if(!((FolderFragment) currentFragment).onBackPressed()) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            //get service
            musicSrv = binder.getService();

            //pass list

            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };




    @Override
    public void onStart() {
        super.onStart();
        if(playIntent==null){
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }




    @Override
    public void onDestroy() {
        stopService(playIntent);
        musicSrv = null;
        super.onDestroy();
        unbindService(musicConnection);
    }





    public void songPicked(View view){
        musicSrv.setSong(Integer.parseInt(view.getTag().toString()));
        musicSrv.playSong();
    }






    @Override
    public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }


}


