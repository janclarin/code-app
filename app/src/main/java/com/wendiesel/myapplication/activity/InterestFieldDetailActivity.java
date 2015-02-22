package com.wendiesel.myapplication.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.wendiesel.myapplication.data.*;
import com.wendiesel.myapplication.R;
import com.wendiesel.myapplication.fragment.ListInterestFieldFragment;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;

public class InterestFieldDetailActivity extends ActionBarActivity {


    TuitionData mTuitionData;
    String mFieldOfInterest;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            Intent i = getIntent();
            mFieldOfInterest = i.getStringExtra(ListInterestFieldFragment.KEY_INTEREST_FIELD);


            mTuitionData = new TuitionData(this);
            setContentView(R.layout.activity_interest_field_detail);


            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(mToolbar);



            TextView textView = (TextView) findViewById(R.id.text);
            textView.setText(mFieldOfInterest);

            BarChart mBarChart = (BarChart) findViewById(R.id.barchart);
            String[] provinces=(String[])GeneralData.getProvinces().toArray();



            TypedArray colorPalette = getApplicationContext().getResources().obtainTypedArray(R.array.province_color);
            mBarChart.addBar(new BarModel("CAN",mTuitionData.getAverageTuition(mFieldOfInterest, null),0xffd50000));
            for (int n = 0; n < GeneralData.abbrev_provinces.length; n++) {
                int val = mTuitionData.getAverageTuition(mFieldOfInterest, provinces[n]);
                //   int val = mTuitionData.getAverageTuition(mFieldOfInterest,null);

                mBarChart.addBar(new BarModel(GeneralData.abbrev_provinces[n], val, colorPalette.getColor(n, 0)));
            }

            mBarChart.startAnimation();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_career_path_detail, menu);
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
}
