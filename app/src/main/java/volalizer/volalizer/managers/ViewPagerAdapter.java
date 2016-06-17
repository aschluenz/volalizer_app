package volalizer.volalizer.managers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import volalizer.volalizer.fragments.RecordFragment;
import volalizer.volalizer.fragments.RecordedListFragment;

/**
 * Created by andyschlunz on 11.06.16.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[];
    int NumOfTabs;

    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumOfTabs) {
        super(fm);

        this.Titles = mTitles;
        this.NumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            RecordFragment recordFragment = new RecordFragment();
            return recordFragment;
        } else {
            RecordedListFragment recordedListFragment = new RecordedListFragment();
            return recordedListFragment;
        }
    }

    @Override
    public int getCount() {
        return NumOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }
}
