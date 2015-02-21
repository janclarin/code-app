package com.wendiesel.myapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.wendiesel.myapplication.data.*;

import com.wendiesel.myapplication.R;
import com.wendiesel.myapplication.fragment.ListInterestFieldFragment;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CareerPathDetailActivity extends ActionBarActivity {


    TuitionData mTuitionData;
    String mFieldOfInterest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        mFieldOfInterest= i.getStringExtra(ListInterestFieldFragment.KEY_INTEREST_FIELD);



        mTuitionData = new TuitionData(this);
        setContentView(R.layout.activity_career_path_detail);


        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText(mFieldOfInterest);

        BarChart mBarChart = (BarChart) findViewById(R.id.barchart);

        for(String s:(String[])GeneralData.getProvinces().toArray()){
            Log.i("CareerPath", s);
        }
        for(String s:GeneralData.abbrev_provinces){
            Log.i("CareerPath", s);
        }

        for (int n=0;n<GeneralData.abbrev_provinces.length; n++) {
            int val = mTuitionData.getAverageTuition(mFieldOfInterest,(String)GeneralData.getProvinces().toArray()[n]);
            mBarChart.addBar(new BarModel(GeneralData.abbrev_provinces[n], val, 0xFF123456));
        }

        mBarChart.startAnimation();

        PieChart mPieChart = (PieChart) findViewById(R.id.piechart);

        mPieChart.addPieSlice(new PieModel("Poop", 15, Color.parseColor("#FE6DA8")));
        mPieChart.addPieSlice(new PieModel("Sleep", 25, Color.parseColor("#56B7F1")));
        mPieChart.addPieSlice(new PieModel("Work", 35, Color.parseColor("#CDA67F")));
        mPieChart.addPieSlice(new PieModel("Eating", 9, Color.parseColor("#FED70E")));

        mPieChart.startAnimation();
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
