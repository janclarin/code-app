package com.wendiesel.myapplication.data;

import android.content.Context;

import com.wendiesel.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class EducationalAttainmentData {
    private JSONObject data;
    private ArrayList<String> educationLevels;

    /**
     * Creates new EducationalAttainmentData object
     *
     * @param ctx Context
     */
    public EducationalAttainmentData(Context ctx) {
        data = JsonDataReader.readObject(ctx, R.raw.educationalattainment);
        educationLevels = new ArrayList<>();
        Iterator<String> keys = data.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            if (key.equals("Total")) continue;
            educationLevels.add(key);
        }
    }

    /**
     * Get a list of education levels, like "less than grade 9" or "bachelor's degree"
     *
     * @return List of education levels, sorted from least to most
     */
    public Collection<String> getEducationLevels() {
        return educationLevels;
    }

    /**
     * Get employment percentage by education level and age
     *
     * @param educationLevel Education level, from getEducationLevels()
     * @param age            Person's age, or 0 to get average for all ages
     * @param gender         Gender (can be both)
     * @return Employment percentage (0-100%)
     */
    public double getEmploymentPercentage(String educationLevel, int age, Gender gender) {
        try {
            int idx;
            if (gender == Gender.MALE) idx = 1;
            else if (gender == Gender.FEMALE) idx = 2;
            else idx = 0;
            JSONObject ep = data.getJSONObject(educationLevel);
            if (age == 0) return ep.getJSONArray("Overall").getInt(idx);
            else if (age <= 24) return ep.getJSONArray("15 to 24 years").getInt(idx);
            else if (age <= 44) return ep.getJSONArray("25 to 44 years").getInt(idx);
            else return ep.getJSONArray("45 and over").getInt(idx);
        } catch (JSONException e) {
            return 0.0;
        }
    }
}
