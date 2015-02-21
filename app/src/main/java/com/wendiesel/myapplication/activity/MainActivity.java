package com.wendiesel.myapplication.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.wendiesel.myapplication.R;
import com.wendiesel.myapplication.fragment.EmploymentInfoFragment;
import com.wendiesel.myapplication.fragment.ListInterestFieldFragment;


public class MainActivity extends ActionBarActivity
        implements ListInterestFieldFragment.OnListCareerPathListener,
        EmploymentInfoFragment.OnEmploymentInfoListener {

    private Toolbar mToolbar;
    private CustomPagerAdapter mAdapter;
    private PagerSlidingTabStrip mTabStrip;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // Find views.
        mTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tab_strip);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        // Set view pager adapter.
        mAdapter = new CustomPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mAdapter);

        // Set page margin.
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        mViewPager.setPageMargin(pageMargin);

        mTabStrip.setViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class CustomPagerAdapter extends FragmentPagerAdapter {
        private final String[] TITLES = {"Fields of Interest", "Employment Information"};

        public CustomPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return ListInterestFieldFragment.newInstance();
                case 1:
                    return EmploymentInfoFragment.newInstance();
                default:
                    return ListInterestFieldFragment.newInstance();
            }
        }
    }
}
