package org.mlab.research.koios.ui.study;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class StudyPagerAdapter extends FragmentPagerAdapter {


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
            return fragment;
        }else{
            Fragment fragment = new OpenStudiesFragment();
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
            return "My Studies";
        }else{
            return "Open Studies";
        }
    }
}
