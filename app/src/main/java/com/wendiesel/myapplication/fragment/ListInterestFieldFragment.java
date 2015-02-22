package com.wendiesel.myapplication.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wendiesel.myapplication.R;
import com.wendiesel.myapplication.activity.YourInformationActivity;
import com.wendiesel.myapplication.data.GeneralData;
import com.wendiesel.myapplication.data.TuitionData;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

public class ListInterestFieldFragment extends Fragment {

    private OnListCareerPathListener mListener;
    private RecyclerView mRecyclerView;
    private InterestFieldAdapter mAdapter;

    private TuitionData mTuitionData;
    private Collection<String> mInterestFields;
    private Collection<String> mRecommendedInterestFields;

    public ListInterestFieldFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Sort by recommendation.
        sortByRecommendation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_interest_field, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list_career_paths);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new InterestFieldAdapter();
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnListCareerPathListener) activity;
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

    /**
     * Sorts by recommendation by default.
     */
    public void sortByRecommendation() {
        if (mRecommendedInterestFields != null) {
            mInterestFields = mRecommendedInterestFields;
            mAdapter.notifyDataSetChanged();
        } else {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
            int science = preferences.getInt(YourInformationActivity.KEY_PREF_SCIENCE, 2);
            int math = preferences.getInt(YourInformationActivity.KEY_PREF_MATH, 2);
            int english = preferences.getInt(YourInformationActivity.KEY_PREF_ENGLISH, 2);
            int physEd = preferences.getInt(YourInformationActivity.KEY_PREF_PHYSICAL_EDU, 2);
            int history = preferences.getInt(YourInformationActivity.KEY_PREF_SOCIAL_STUDIES, 2);
            mTuitionData = new TuitionData(getActivity().getApplicationContext());
            mInterestFields = mRecommendedInterestFields = mTuitionData.getFieldOfInterests(math, science, english, history, physEd);
        }
    }

    /**
     * Sorts the career paths based on new sort order.
     *
     * @param sortOrder
     */
    public void sortBy(TuitionData.SortOrder sortOrder) {
        if (sortOrder == null) {
            sortByRecommendation();
        } else {
            mInterestFields = mTuitionData.getFieldOfInterests(sortOrder);
            mAdapter.notifyDataSetChanged();
        }
    }

    public interface OnListCareerPathListener {
    }

    private class InterestFieldHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private static final String mAverageTuitionText = "Current average tuition fee: $";

        /**
         * Views for each compliment.
         */
        private View mLayoutExpand;
        private TextView mTextInterestField;
        private TextView mTextAverageTuition;
        private TextView mAboutText;
        private TextView mAverageSalary;
        private ImageView mArrowIndicator;
        private BarChart mBarChart;
        private LineChartView mLineChart;

        private LineChartData mLineChartData;
        private String mInterestName;

        public InterestFieldHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            // Find the views.
            mLayoutExpand = itemView.findViewById(R.id.ll_interest_field_info);
            mTextInterestField = (TextView) itemView.findViewById(R.id.tv_field_of_interest);
            mTextAverageTuition = (TextView) itemView.findViewById(R.id.tv_average_tuition);
            mAboutText = (TextView) itemView.findViewById(R.id.tv_about);
            mAverageSalary = (TextView) itemView.findViewById(R.id.tv_average_salary);
            mArrowIndicator = (ImageView) itemView.findViewById(R.id.iv_expansion_indicator);
            mBarChart = (BarChart) itemView.findViewById(R.id.barChart);
            mLineChart = (LineChartView) itemView.findViewById(R.id.lineChart);

            // Set to view options for line chart.
            mLineChart.setInteractive(false);
            mLineChart.setZoomEnabled(false);
            mLineChart.setContainerScrollEnabled(false, ContainerScrollType.HORIZONTAL);
            mLineChart.setValueSelectionEnabled(true);
        }

        public void bindInterestField(String name, int tuition) {
            hideInformation();

            mInterestName = name;
            double avgSalaryPerHour = mTuitionData.getAverageSalary(mInterestName, 2015);
            String wageInfo = String.format("Current average wage: $%.2f/hr or $%.0f/yr", avgSalaryPerHour, (avgSalaryPerHour * 2080));
            mTextInterestField.setText(name);
            mTextAverageTuition.setText(mAverageTuitionText + tuition + "/yr");
            mAboutText.setText(mTuitionData.getDescription(mInterestName));
            mAverageSalary.setText(wageInfo);

            // Line chart.
            List<PointValue> values = new ArrayList<>();
            List<AxisValue> axisValues = new ArrayList<>();
            for (int year = 2011; year <= 2015; year++) {
                double salary = mTuitionData.getAverageSalary(mInterestName, year);
                values.add(new PointValue((float) year, (float) salary));
                axisValues.add(new AxisValue((float) year, Integer.toString(year).toCharArray()));
            }

            // Set lines.
            Line line = new Line(values)
                    .setColor(getResources().getColor(R.color.theme_accent))
                    .setShape(ValueShape.CIRCLE)
                    .setFilled(true)
                    .setHasPoints(true)
                    .setHasLabels(true);
            List<Line> lines = new ArrayList<>();
            lines.add(line);

            // Set line chart data.
            mLineChartData = new LineChartData(lines);

            // Axes
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            axisX.setName("Year");
            axisX.setAutoGenerated(false);
            axisX.setValues(axisValues);
            axisY.setName("Wage ($)");
            mLineChartData.setAxisXBottom(axisX);
            mLineChartData.setAxisYLeft(axisY);

            mLineChart.setLineChartData(mLineChartData);

            String[] provinces = (String[]) GeneralData.getProvinces().toArray();
            TypedArray colorPalette = getActivity().getApplicationContext().getResources().obtainTypedArray(R.array.province_color);

            // Bar chart.
            mBarChart.clearChart();
            for (int n = 0; n < GeneralData.abbrev_provinces.length; n++) {
                int val = mTuitionData.getAverageTuition(mInterestName, provinces[n]);
                mBarChart.addBar(new BarModel(GeneralData.abbrev_provinces[n], val, colorPalette.getColor(n, 0)));
            }
        }

        @Override
        public void onClick(View v) {
            if (mLayoutExpand.getVisibility() == View.GONE) {
                showInformation();
            } else {
                hideInformation();
            }
        }

        /**
         * Expands the inner layout to show more info.
         */
        private void showInformation() {
            // Open layout.
            mLayoutExpand.setVisibility(View.VISIBLE);
            mArrowIndicator.setBackgroundResource(R.drawable.hide_indicator);
        }

        /**
         * Hides the inner layout to show less info.
         */
        private void hideInformation() {
            // Close the expansion every time.
            mLayoutExpand.setVisibility(View.GONE);
            mArrowIndicator.setBackgroundResource(R.drawable.show_indicator);
        }
    }

    private class InterestFieldAdapter extends RecyclerView.Adapter<InterestFieldHolder> {
        @Override
        public InterestFieldHolder onCreateViewHolder(ViewGroup parent, int position) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_interest_field, parent, false);
            return new InterestFieldHolder(view);
        }

        @Override
        public void onBindViewHolder(InterestFieldHolder holder, int position) {
            Iterator<String> iterator = mInterestFields.iterator();
            for (int i = 0; i < position; i++) iterator.next();
            String interestField = iterator.next();
            int tuition = mTuitionData.getAverageTuition(interestField, null);
            holder.bindInterestField(interestField, tuition);
        }

        @Override
        public int getItemCount() {
            return mInterestFields.size();
        }
    }
}
