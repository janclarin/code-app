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
    private static final HashMap<String,String> salaryMapping = new HashMap<>();
    static {
        salaryMapping.put("Agriculture, natural resources and conservation","Occupations unique to processing, manufacturing and utilities");
        salaryMapping.put("Architecture and related technologies","Occupations in art, culture, recreation and sport");
        salaryMapping.put("Business management and public administration","Management occupations");
        salaryMapping.put("Dentistry","Health occupations");
        salaryMapping.put("Education","Occupations in social science, education, government service and religion");
        salaryMapping.put("Engineering","Natural and applied sciences and related occupations");
        salaryMapping.put("Humanities","Occupations in social science, education, government service and religion");
        salaryMapping.put("Law, legal professions and studies","Business, finance and administrative occupations");
        salaryMapping.put("Mathematics, computer and information sciences","Natural and applied sciences and related occupations");
        salaryMapping.put("Medicine","Health occupations");
        salaryMapping.put("Nursing","Health occupations");
        salaryMapping.put("Other health, parks, recreation and fitness","Occupations in art, culture, recreation and sport");
        salaryMapping.put("Pharmacy","Health occupations");
        salaryMapping.put("Physical and life sciences and technologies","Natural and applied sciences and related occupations");
        salaryMapping.put("Social and behavioural sciences","Occupations in social science, education, government service and religion");
        salaryMapping.put("Veterinary medicine","Health occupations");
        salaryMapping.put("Visual and performing arts, and communications technologies","Occupations in art, culture, recreation and sport");
    }
    private TreeSet<String> fieldOfInterests = new TreeSet<>();
    private HashMap<String, Integer> tuition = new HashMap<>();
    private JSONObject salary;

    /**
     * Creates new TuitionData object
     * @param ctx Context
     */
    public TuitionData(Context ctx) {
        JSONArray data = JsonDataReader.readArray(ctx, R.raw.averagetuition);
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
        salary = JsonDataReader.readObject(ctx, R.raw.salaryindustry);
    }

    /**
     * Gets average tuition for a field of interest and province
     * @param fieldOfInterest Field of interest
     * @param province Province (or null for entire Canada)
     * @return Average tuition, in dollars
     */
    public int getAverageTuition(String fieldOfInterest, String province) {
        if (province == null) province = "Canada";
        Integer cost = tuition.get(fieldOfInterest + province);
        return (cost == null) ? 0 : cost;
    }

    /**
     * Gets a list of field of interests, sorted alphabetically
     * @return List of field of interests
     */
    public Collection<String> getFieldOfInterests() {
        return fieldOfInterests;
    }

    /**
     * Gets average salary (per hour) for a field of interest
     * @param fieldOfInterest Field of interest
     * @param province Province (or null for entire Canada)
     * @return Average salary, per hour
     */
    public double getAverageSalary(String fieldOfInterest, String province) {
        if (province == null) province = "Canada";
        try {
            return salary.getJSONObject(province).getDouble(salaryMapping.get(fieldOfInterest));
        } catch (Exception e) {
            return 0.0;
        }
    }
}
