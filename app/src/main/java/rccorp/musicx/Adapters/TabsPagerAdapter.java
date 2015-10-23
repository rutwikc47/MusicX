package rccorp.musicx.Adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import rccorp.musicx.Fragments.AlbumFragment;
import rccorp.musicx.Fragments.FolderFragment;
import rccorp.musicx.Fragments.PlaylistFragment;
import rccorp.musicx.Fragments.TracksFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    private Fragment mCurrentFragment;

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new AlbumFragment();
            case 1:
                return new TracksFragment();
            case 2:
                return new FolderFragment();
            case 3:
                return new PlaylistFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 4;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mCurrentFragment = (Fragment) object;
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }
}
