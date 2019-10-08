package com.example.budgetthing.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.budgetthing.MainActivity;
import com.example.budgetthing.R;
import com.example.budgetthing.fragments.History;
import com.example.budgetthing.fragments.Home;
import com.example.budgetthing.fragments.Summary;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    protected static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);

        if(position == 2){
            MainActivity.summary_tag = createdFragment.getTag();
        }

        return super.instantiateItem(container, position);
    }

    public SectionsPagerAdapter(Context context, FragmentManager fm ) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Home();

            case 1:
                return new History();

            case 2:
                return new Summary();
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {

        return TAB_TITLES.length;
    }
}