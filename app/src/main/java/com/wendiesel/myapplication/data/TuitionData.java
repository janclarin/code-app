package com.wendiesel.myapplication.data;

import android.content.Context;

import com.wendiesel.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.TreeSet;

public class TuitionData {
    private JSONArray data;
    private TreeSet<String> fieldOfInterests = new TreeSet<>();
    private HashMap<String, Integer> tuition = new HashMap<>();

    /**
     * Creates new TuitionData object
     * @param ctx Context
     */
    public TuitionData(Context ctx) {
        data = JsonDataReader.readArray(ctx, R.raw.averagetuition);
        for (int i = 0; i < data.length(); i++) {
            try {
                JSONObject e = data.getJSONObject(i);
                if (!e.getString("Ref_Date").equals("2014/2015")) continue;
                fieldOfInterests.add(e.getString("GROUP"));
                tuition.put(e.getString("GROUP") + e.getString("GEO"), e.getInt("Value"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Gets average tuition for a field of interest and province
     * @param fieldOfInterest Field of interest
     * @param province Province (or null for entire Canada)
     * @return Average tuition, in dollars
     */
    public int getAverageTuition(String fieldOfInterest, String province) {
        if (province == null) province = "Canada";
        return tuition.get(fieldOfInterest + province);
    }

    /**
     * Gets a list of field of interests, sorted alphabetically
     * @return List of field of interests
     */
    public Collection<String> getFieldOfInterests() {
        return fieldOfInterests;
    }
}
