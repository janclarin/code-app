package com.wendiesel.myapplication.data;

import android.content.Context;

import com.wendiesel.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

public class TuitionData {
    private static final HashMap<String,String> salaryMapping = new HashMap<>();
    private static final String[][] subjectMapping = {
            // Math
            {"Architecture and related technologies","Engineering","Mathematics, computer and information sciences"},
            // Science
            {"Dentistry","Engineering","Medicine","Nursing","Pharmacy","Physical and life sciences and technologies","Veterinary medicine"},
            // English
            {"Education","Humanities","Visual and performing arts, and communications technologies","Law, legal professions and studies","Social and behavioural sciences"},
            // History
            {"Agriculture, natural resources and conservation","Business management and public administration","Humanities","Law, legal professions and studies","Social and behavioural sciences"},
            // Physical Education
            {"Medicine","Nursing","Other health, parks, recreation and fitness"}
    };
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

    private class FoiRanking implements Comparable {
        private int idx, rank;

        @Override
        public int compareTo(Object another) {
            FoiRanking fr2 = (FoiRanking) another;
            if (rank < fr2.rank) return 1;
            else if (rank > fr2.rank) return -1;
            return 0;
        }

        FoiRanking(int idx, int rank) {
            this.idx = idx;
            this.rank = rank;
        }
    }

    /**
     * Gets a list of field of interests, sorted by ranking
     * Rankings should be relative
     * @return List of field of interests
     */
    public Collection<String> getFieldOfInterests(int math, int science, int english, int history, int physed) {
        FoiRanking rankings[] = new FoiRanking[]{new FoiRanking(0,math), new FoiRanking(1,science), new FoiRanking(2,english), new FoiRanking(3,history), new FoiRanking(4,physed)};
        Arrays.sort(rankings);
        ArrayList<String> foiList = new ArrayList<>();
        HashSet<String> usedFoi = new HashSet<>();
        for (FoiRanking fr : rankings) {
            for (String foi : subjectMapping[fr.idx]) {
                if (usedFoi.contains(foi)) continue;
                usedFoi.add(foi);
                foiList.add(foi);
            }
        }
        return foiList;
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
