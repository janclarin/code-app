package com.wendiesel.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.wendiesel.myapplication.R;
import com.wendiesel.myapplication.data.EducationalAttainmentData;

public class YourInformationActivity extends ActionBarActivity {

    public static final String KEY_PREF_AGE_GROUP = "age_group";
    public static final String KEY_PREF_CURRENT_EDU_LEVEL = "current_edu_level";
    public static final String KEY_PREF_SCIENCE = "science";
    public static final String KEY_PREF_ENGLISH = "english";
    public static final String KEY_PREF_MATH = "math";
    public static final String KEY_PREF_SOCIAL_STUDIES = "social_studies";
    public static final String KEY_PREF_PHYSICAL_EDU = "physical_edu";

    private SharedPreferences mSharedPreferences;
    private Spinner mSpinnerAgeGroup;
    private Spinner mSpinnerCurrentEduLevel;
    private SeekBar mSeekScience;
    private SeekBar mSeekEnglish;
    private SeekBar mSeekMath;
    private SeekBar mSeekSocialStudies;
    private SeekBar mSeekPhysicalEdu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Preferences.
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // Data.
        EducationalAttainmentData data = new EducationalAttainmentData(getApplicationContext());
        String[] ageGroups = (String[]) data.getAges().toArray();
        String[] eduLevels = (String[]) data.getEducationLevels().toArray();

        // Find views.
        mSpinnerAgeGroup = (Spinner) findViewById(R.id.spn_age_group);
        mSpinnerCurrentEduLevel = (Spinner) findViewById(R.id.spn_current_edu_level);
        mSeekScience = (SeekBar) findViewById(R.id.seek_science);
        mSeekEnglish = (SeekBar) findViewById(R.id.seek_english);
        mSeekMath = (SeekBar) findViewById(R.id.seek_math);
        mSeekSocialStudies = (SeekBar) findViewById(R.id.seek_social_studies);
        mSeekPhysicalEdu = (SeekBar) findViewById(R.id.seek_physical_education);

        // Spinner adapters.
        ArrayAdapter<String> adapterAgeGroup = new ArrayAdapter<>(YourInformationActivity.this,
                android.R.layout.simple_list_item_1, ageGroups);
        ArrayAdapter<String> adapterEduLevels = new ArrayAdapter<>(YourInformationActivity.this,
                android.R.layout.simple_list_item_1, eduLevels);

        // Set spinner adapters.
        mSpinnerAgeGroup.setAdapter(adapterAgeGroup);
        mSpinnerCurrentEduLevel.setAdapter(adapterEduLevels);

        // Get position of saved age group.
        String savedAgeGroup = mSharedPreferences.getString(KEY_PREF_AGE_GROUP, ageGroups[0]);
        int agePosition = 0;
        for (int i = 0; i < ageGroups.length; i++) {
            if (ageGroups[i].equals(savedAgeGroup)) {
                break;
            }
            agePosition++;
        }
        mSpinnerAgeGroup.setSelection(agePosition);

        // Get position of saved education level.
        String savedEduLevel = mSharedPreferences.getString(KEY_PREF_CURRENT_EDU_LEVEL, eduLevels[0]);
        int eduLevel = 0;
        for (int i = 0; i < eduLevels.length; i++) {
            if (eduLevels[i].equals(savedEduLevel)) {
                break;
            }
            eduLevel++;
        }
        mSpinnerCurrentEduLevel.setSelection(eduLevel);

        // Change how much each increments by.
        mSeekScience.incrementProgressBy(1);
        mSeekEnglish.incrementProgressBy(1);
        mSeekMath.incrementProgressBy(1);
        mSeekSocialStudies.incrementProgressBy(1);
        mSeekPhysicalEdu.incrementProgressBy(1);

        // Set progress to middle or previous setting.
        mSeekScience.setProgress(mSharedPreferences.getInt(KEY_PREF_SCIENCE, 2));
        mSeekEnglish.setProgress(mSharedPreferences.getInt(KEY_PREF_ENGLISH, 2));
        mSeekMath.setProgress(mSharedPreferences.getInt(KEY_PREF_MATH, 2));
        mSeekSocialStudies.setProgress(mSharedPreferences.getInt(KEY_PREF_SOCIAL_STUDIES, 2));
        mSeekPhysicalEdu.setProgress(mSharedPreferences.getInt(KEY_PREF_PHYSICAL_EDU, 2));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_information, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_done) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString(KEY_PREF_AGE_GROUP, (String) mSpinnerAgeGroup.getSelectedItem());
            editor.putString(KEY_PREF_CURRENT_EDU_LEVEL, (String) mSpinnerCurrentEduLevel.getSelectedItem());
            editor.putInt(KEY_PREF_SCIENCE, mSeekScience.getProgress());
            editor.putInt(KEY_PREF_ENGLISH, mSeekEnglish.getProgress());
            editor.putInt(KEY_PREF_MATH, mSeekMath.getProgress());
            editor.putInt(KEY_PREF_SOCIAL_STUDIES, mSeekSocialStudies.getProgress());
            editor.putInt(KEY_PREF_PHYSICAL_EDU, mSeekPhysicalEdu.getProgress());
            editor.apply();
            Intent intent = new Intent(YourInformationActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
