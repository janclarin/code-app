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

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ColumnChartView;

public class EmploymentInfoFragment extends Fragment {

    private OnEmploymentInfoListener mListener;

    private List<String> mEducationLevels;
    private Spinner mSpinnerEduLevelDesired;
    private TextView mTextEmploymentRateChange, mTextEmploymentRateEduLevel;
    private EducationalAttainmentData data;

    public EmploymentInfoFragment() {
        // Required empty public constructor
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
        final ColumnChartView columnChart = (ColumnChartView) view.findViewById(R.id.barChart);

        // Set column chart settings.
        columnChart.setZoomEnabled(false);
        columnChart.setValueSelectionEnabled(false);
        columnChart.setInteractive(false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        final String currentAge = preferences.getString(YourInformationActivity.KEY_PREF_AGE_GROUP, "15 to 24 years");
        final String currentEdu = preferences.getString(YourInformationActivity.KEY_PREF_CURRENT_EDU_LEVEL, "Less than Grade 9");

        // Find views.
        mSpinnerEduLevelDesired = (Spinner) view.findViewById(R.id.spn_desired_edu_level);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_list_item_1, mEducationLevels);
        mSpinnerEduLevelDesired.setAdapter(adapter);

        mSpinnerEduLevelDesired.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String educationLevel = mSpinnerEduLevelDesired.getSelectedItem().toString();

                double currentOdds = data.getEmploymentPercentage(currentEdu, currentAge, Gender.BOTH);
                double odds = data.getEmploymentPercentage(educationLevel, currentAge, Gender.BOTH);

                // Column chart logic.
                List<AxisValue> axisValues = new ArrayList<>();
                axisValues.add(new AxisValue(0, "Current".toCharArray()));
                axisValues.add(new AxisValue(1, "Future".toCharArray()));

                List<Column> columns = new ArrayList<>();
                List<SubcolumnValue> currentValues = new ArrayList<>();
                List<SubcolumnValue> futureValues = new ArrayList<>();

                currentValues.add(new SubcolumnValue((float) currentOdds, 0xff66BB6A));
                futureValues.add(new SubcolumnValue((float) odds, 0xff2196F3));

                Column currentColumn = new Column(currentValues);
                Column futureColumn = new Column(futureValues);
                columns.add(currentColumn);
                columns.add(futureColumn);

                ColumnChartData chartData = new ColumnChartData(columns);

                Axis axisX = new Axis();
                Axis axisY = new Axis().setHasLines(true);

                axisX.setAutoGenerated(false);
                axisX.setValues(axisValues);
                axisY.setName("Employment rate (%)");
                chartData.setAxisXBottom(axisX);
                chartData.setAxisYLeft(axisY);

                columnChart.setColumnChartData(chartData);

                // Set text fields in employment rate paragraph.
                double difference = odds - currentOdds;
                String rateChange = (difference >= 0) ? String.format("%.0f%% higher", difference) :
                        String.format("%.0f%% lower", Math.abs(difference));
                mTextEmploymentRateChange.setText(rateChange);
                mTextEmploymentRateEduLevel.setText(educationLevel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Set to last education level by default.
        mSpinnerEduLevelDesired.setSelection(mEducationLevels.size() - 2);

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
