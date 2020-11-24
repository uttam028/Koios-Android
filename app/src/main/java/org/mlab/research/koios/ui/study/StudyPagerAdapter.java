package org.mlab.research.koios.ui.study;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class StudyPagerAdapter extends FragmentPagerAdapter {
    private final static String TAG = StudyPagerAdapter.class.getSimpleName()+"_debug";

    public StudyPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public StudyPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==1){
            Fragment fragment = new MyStudiesFragment();
//            Bundle args = new Bundle();
//            // Our object is just an integer :-P
//            args.putInt(MyStudiesFragment.ARG_OBJECT, i + 1);
//            fragment.setArguments(args);
            Log.d(TAG, "position 1 for my studies");
            return fragment;
        }else{
            Fragment fragment = new OpenStudiesFragment();
            Log.d(TAG, "position "+ position +" for open studies");
            return fragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==1){
            Log.d(TAG, "my studies position has been changed");
            return "My Studies";
        }else{
            Log.d(TAG, "open studies position has been changed");
            return "Open Studies";
        }
    }

}
