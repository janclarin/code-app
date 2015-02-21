package com.wendiesel.myapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.wendiesel.myapplication.data.*;

import com.wendiesel.myapplication.R;
import com.wendiesel.myapplication.fragment.ListCareerPathFragment;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;

public class CareerPathDetailActivity extends ActionBarActivity {


    TuitionData mTuitionData;
    String mFieldOfInterest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        mFieldOfInterest= i.getStringExtra(ListCareerPathFragment.KEY_INTEREST_FIELD);



        mTuitionData = new TuitionData(this);
        setContentView(R.layout.activity_career_path_detail);


        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText(mFieldOfInterest);

        BarChart mBarChart = (BarChart) findViewById(R.id.barchart);

        for (String s : mTuitionData.getFieldOfInterests()) {
            mBarChart.addBar(new BarModel(s, 2.3f, 0xFF123456));
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
