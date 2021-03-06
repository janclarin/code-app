package com.wendiesel.myapplication.data;

import android.content.Context;

import com.wendiesel.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class EducationalAttainmentData {
    private static final String[] educationLevels = {
            "Less than Grade 9",
            "Some secondary school",
            "High school graduate",
            "Some postsecondary",
            "Postsecondary certificate or diploma",
            "Bachelor's degree",
            "Above bachelor's degree"};
    private static final String[] ages = {"15 to 24 years", "25 to 44 years", "45 and over"};
    private JSONObject data;

    /**
     * Creates new EducationalAttainmentData object
     *
     * @param ctx Context
     */
    public EducationalAttainmentData(Context ctx) {
        data = JsonDataReader.readObject(ctx, R.raw.educationalattainment);
    }

    /**
     * Get a list of education levels, like "less than grade 9" or "bachelor's degree"
     *
     * @return List of education levels, sorted from least to most
     */
    public Collection<String> getEducationLevels() {
        return Arrays.asList(educationLevels);
    }

    /**
     * Get a list of ages, like "15 to 24 years"
     * @return List of ages
     */
    public Collection<String> getAges() {
        return Arrays.asList(ages);
    }

    /**
     * Get employment percentage by education level and age
     *
     * @param educationLevel Education level, from getEducationLevels()
     * @param age            Person's age group ("15 to 24 years" etc, or null for total)
     * @param gender         Gender (can be both)
     * @return Employment percentage (0-100%)
     */
    public double getEmploymentPercentage(String educationLevel, String age, Gender gender) {
        if (age == null) age = "Total";
        try {
            int idx;
            if (gender == Gender.MALE) idx = 1;
            else if (gender == Gender.FEMALE) idx = 2;
            else idx = 0;
            JSONObject ep = data.getJSONObject(educationLevel);
            return ep.getJSONArray(age).getInt(idx);
        } catch (JSONException e) {
            return 0.0;
        }
    }
}
