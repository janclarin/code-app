package com.wendiesel.myapplication.data;

import java.util.Arrays;
import java.util.Collection;

public class GeneralData {
    private static final String[] provinces = new String[]{"Alberta", "British Columbia", "Manitoba", "New Brunswick", "Newfoundland and Labrador", "Nova Scotia", "Ontario", "Prince Edward Island", "Quebec", "Saskatchewan"};

    /**
     * Get a list of Canadian provinces
     * @return List of provinces, sorted alphabetically
     */
    public static Collection<String> getProvinces() {
        return Arrays.asList(provinces);
    }
}
