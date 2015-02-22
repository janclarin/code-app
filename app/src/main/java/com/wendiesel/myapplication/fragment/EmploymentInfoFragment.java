package com.wendiesel.myapplication.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.wendiesel.myapplication.R;
import com.wendiesel.myapplication.activity.YourInformationActivity;
import com.wendiesel.myapplication.data.EducationalAttainmentData;
import com.wendiesel.myapplication.data.Gender;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.List;

public class EmploymentInfoFragment extends Fragment {

    private OnEmploymentInfoListener mListener;

    private List<String> mEducationLevels;
    private Spinner mSpinnerEduLevelDesired;
    private TextView mTextEmploymentRateChange, mTextEmploymentRateEduLevel;
    private EducationalAttainmentData data;
    private BarModel currentBar;
    private BarModel futureBar;

    public EmploymentInfoFragment() {
        // Required empty public constructor
    }

    public static EmploymentInfoFragment newInstance() {
        return new EmploymentInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new EducationalAttainmentData(getActivity());
        mEducationLevels = (List<String>) data.getEducationLevels();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_employment_info, container, false);

        mTextEmploymentRateChange = (TextView) view.findViewById(R.id.tv_employment_rate_change);
        mTextEmploymentRateEduLevel = (TextView) view.findViewById(R.id.tv_employment_rate_edu_level);
        final BarChart mBarChart = (BarChart) view.findViewById(R.id.barChart);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        final String currentAge = preferences.getString(YourInformationActivity.KEY_PREF_AGE_GROUP, "15 to 24 years");
        final String currentEdu = preferences.getString(YourInformationActivity.KEY_PREF_CURRENT_EDU_LEVEL, "Less than Grade 9");

        currentBar = new BarModel(50f, 0xff78909c);
        currentBar.setLegendLabel("Current");
        futureBar = new BarModel(0f, 0xff5c6bc0);
        futureBar.setLegendLabel("Future");
        mBarChart.addBar(currentBar);
        mBarChart.addBar(futureBar);

        // Find views.
        mSpinnerEduLevelDesired = (Spinner) view.findViewById(R.id.spn_desired_edu_level);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_list_item_1, mEducationLevels);
        mSpinnerEduLevelDesired.setAdapter(adapter);

        mSpinnerEduLevelDesired.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String educationLevel = mSpinnerEduLevelDesired.getSelectedItem().toString();

                double odds = data.getEmploymentPercentage(educationLevel, currentAge, Gender.BOTH);
                double currentOdds = data.getEmploymentPercentage(currentEdu, currentAge, Gender.BOTH);
                currentBar.setValue((int) currentOdds);
                futureBar.setValue((int) odds);
                mBarChart.update();
                mBarChart.startAnimation();

                // Set text fields in employment rate paragraph.
                mTextEmploymentRateChange.setText(String.format("%.0f%%", odds - currentOdds));
                mTextEmploymentRateEduLevel.setText(educationLevel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Set to last education level by default.
        mSpinnerEduLevelDesired.setSelection(mEducationLevels.size() - 1);

        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnEmploymentInfoListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnEmploymentInfoListener {
    }

}
