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
import android.widget.TextView;

import com.wendiesel.myapplication.R;
import com.wendiesel.myapplication.activity.YourInformationActivity;
import com.wendiesel.myapplication.data.GeneralData;
import com.wendiesel.myapplication.data.TuitionData;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.Collection;
import java.util.Iterator;

public class ListInterestFieldFragment extends Fragment {

    private OnListCareerPathListener mListener;
    private RecyclerView mRecyclerView;

    private TuitionData mTuitionData;
    private Collection<String> mInterestFields;

    public ListInterestFieldFragment() {
        // Required empty public constructor
    }

    public static ListInterestFieldFragment newInstance() {
        return new ListInterestFieldFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        int science = preferences.getInt(YourInformationActivity.KEY_PREF_SCIENCE, 2);
        int math = preferences.getInt(YourInformationActivity.KEY_PREF_MATH, 2);
        int english = preferences.getInt(YourInformationActivity.KEY_PREF_ENGLISH, 2);
        int physed = preferences.getInt(YourInformationActivity.KEY_PREF_PHYSICAL_EDU, 2);
        int history = preferences.getInt(YourInformationActivity.KEY_PREF_SOCIAL_STUDIES, 2);
        mTuitionData = new TuitionData(getActivity().getApplicationContext());
        mInterestFields = mTuitionData.getFieldOfInterests(math, science, english, history, physed);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_interest_field, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list_career_paths);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new InterestFieldAdapter());

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

    public interface OnListCareerPathListener {
    }

    private class InterestFieldHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private static final String mAverageTuitionText = "Average annual tuition fee: $";

        /**
         * Views for each compliment.
         */
        private TextView mTextInterestField;
        private TextView mTextAverageTuition;
        private TextView mAboutText;
        private TextView mAverageSalary;
        private BarChart mBarChart;

        private String mInterestName;

        public InterestFieldHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            // Find the views.
            mTextInterestField = (TextView) itemView.findViewById(R.id.tv_field_of_interest);
            mTextAverageTuition = (TextView) itemView.findViewById(R.id.tv_average_tuition);
            mAboutText = (TextView) itemView.findViewById(R.id.tv_about);
            mAverageSalary = (TextView) itemView.findViewById(R.id.tv_average_salary);
            mBarChart = (BarChart) itemView.findViewById(R.id.barChart);
        }

        public void bindInterestField(String name, int tuition) {
            mInterestName = name;
            mTextInterestField.setText(name);
            mTextAverageTuition.setText(mAverageTuitionText + tuition + "/year");
            TuitionData data = new TuitionData(getActivity());
            mAboutText.setText(data.getDescription(mInterestName));
            double avgSalaryPerHour = data.getAverageSalary(mInterestName, null);
            String wageinfo = String.format("$%.2f/hour \n$%.0f/year (approximate)", avgSalaryPerHour, (avgSalaryPerHour * 2080));
            mAverageSalary.setText(wageinfo);

            String[] provinces = (String[]) GeneralData.getProvinces().toArray();
            TypedArray colorPalette = getActivity().getApplicationContext().getResources().obtainTypedArray(R.array.province_color);

            mBarChart.clearChart();

            //  mBarChart.addBar(new BarModel("CAN", mTuitionData.getAverageTuition(mInterestName, null), 0xffd50000));
            for (int n = 0; n < GeneralData.abbrev_provinces.length; n++) {
                int val = mTuitionData.getAverageTuition(mInterestName, provinces[n]);
                mBarChart.addBar(new BarModel(GeneralData.abbrev_provinces[n], val, colorPalette.getColor(n, 0)));
            }

        }

        @Override
        public void onClick(View v) {
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
