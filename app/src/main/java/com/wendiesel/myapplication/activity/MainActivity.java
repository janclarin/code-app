package com.wendiesel.myapplication.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.wendiesel.myapplication.R;
import com.wendiesel.myapplication.data.TuitionData;
import com.wendiesel.myapplication.fragment.EmploymentInfoFragment;
import com.wendiesel.myapplication.fragment.ListInterestFieldFragment;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class MainActivity extends ActionBarActivity
        implements MaterialTabListener,
        ListInterestFieldFragment.OnListCareerPathListener,
        EmploymentInfoFragment.OnEmploymentInfoListener {

    private Toolbar mToolbar;
    private CustomPagerAdapter mAdapter;
    private MaterialTabHost mTabHost;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        boolean isFirstTime = sharedPreferences.getBoolean("firstTime", true);

        if (isFirstTime) {
            sharedPreferences
                    .edit()
                    .putBoolean("firstTime", false)
                    .apply();
            Intent intent = new Intent(MainActivity.this, YourInformationActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        // Find views.
        mTabHost = (MaterialTabHost) findViewById(R.id.tab_host);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        // Set view pager adapter.
        mAdapter = new CustomPagerAdapter(getFragmentManager());

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mTabHost.setSelectedNavigationItem(position);
            }
        });

        // Insert all tabs.
        for (int i = 0; i < mAdapter.getCount(); i++) {
            mTabHost.addTab(mTabHost.newTab()
                    .setText(mAdapter.getPageTitle(i))
                    .setTabListener(this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_account:
                Intent intent = new Intent(MainActivity.this, YourInformationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.action_sort_recommendations:
                mAdapter.mListInterestFragment.sortBy(null);
                item.setChecked(true);
                break;
            case R.id.action_sort_alphabetical_ascending:
                mAdapter.mListInterestFragment.sortBy(TuitionData.SortOrder.ALPHABETICAL);
                item.setChecked(true);
                break;
            case R.id.action_sort_tuition_ascending:
                mAdapter.mListInterestFragment.sortBy(TuitionData.SortOrder.TUITION_ASC);
                item.setChecked(true);
                break;
            case R.id.action_sort_tuition_descending:
                mAdapter.mListInterestFragment.sortBy(TuitionData.SortOrder.TUITION_DSC);
                item.setChecked(true);
                break;
            case R.id.action_sort_salary_ascending:
                mAdapter.mListInterestFragment.sortBy(TuitionData.SortOrder.SALARY_ASC);
                item.setChecked(true);
                break;
            case R.id.action_sort_salary_descending:
                mAdapter.mListInterestFragment.sortBy(TuitionData.SortOrder.SALARY_DSC);
                item.setChecked(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        mViewPager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    public class CustomPagerAdapter extends FragmentPagerAdapter {
        private final String[] TITLES = {"Career Paths", "Employment Info"};

        ListInterestFieldFragment mListInterestFragment;
        EmploymentInfoFragment mEmploymentInfoFragment;

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
                    if (mListInterestFragment == null) {
                        mListInterestFragment = new ListInterestFieldFragment();
                    }
                    return mListInterestFragment;
                case 1:
                    if (mEmploymentInfoFragment == null) {
                        mEmploymentInfoFragment = new EmploymentInfoFragment();
                    }
                    return mEmploymentInfoFragment;
                default:
                    return new Fragment();
            }
        }
    }
}
