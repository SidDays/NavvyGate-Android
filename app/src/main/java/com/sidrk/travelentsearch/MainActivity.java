package com.sidrk.travelentsearch;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.getTabAt(0).setCustomView(R.layout.tab_icon);
        tabLayout.getTabAt(1).setCustomView(R.layout.tab_icon);
        View tab1_view = tabLayout.getTabAt(0).getCustomView();
        View tab2_view = tabLayout.getTabAt(1).getCustomView();
        TextView tab1_title = (TextView) tab1_view.findViewById(R.id.textViewTab);
        ImageView img1 = (ImageView) tab1_view.findViewById(R.id.imageViewTab);
        TextView tab2_title = (TextView) tab2_view.findViewById(R.id.textViewTab);
        ImageView img2 = (ImageView) tab2_view.findViewById(R.id.imageViewTab);
        tab1_title.setText("Search");
        img1.setImageResource(R.drawable.search);
        tab2_title.setText("Favorites");
        img2.setImageResource(R.drawable.heart_fill_white);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            // getItem is called to instantiate the fragment for the given page.
            switch (position) {
                case 0:
                    return new SearchFragment(); // FIXME: Replace with new instance

                case 1:
                    return FavoritesFragment.newInstance();

                default:
                    // TODO: Remove this case
                    Log.d(TAG, "Default case");
                    return new SearchFragment();

            }
        }

        @Override
        public int getCount() {
            // Show number of total pages.
            return 2;
        }
    }

}
