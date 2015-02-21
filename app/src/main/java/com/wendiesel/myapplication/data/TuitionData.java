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

    public int getAverageTuition(String fieldOfInterest, String province) {
        return tuition.get(fieldOfInterest + province);
    }

    public Collection<String> getFieldOfInterests() {
        return fieldOfInterests;
    }
}
