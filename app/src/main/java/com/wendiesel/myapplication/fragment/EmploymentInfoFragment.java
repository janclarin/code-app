package com.wendiesel.myapplication.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.wendiesel.myapplication.R;
import com.wendiesel.myapplication.data.EducationalAttainmentData;

import java.util.List;

public class EmploymentInfoFragment extends Fragment {

    private OnEmploymentInfoListener mListener;

    private List<String> mEducationLevels;
    private Spinner mSpinnerEduLevelDesired;
    private TextView mTextAgeGroup;

    public EmploymentInfoFragment() {
        // Required empty public constructor
    }

    public static EmploymentInfoFragment newInstance() {
        return new EmploymentInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EducationalAttainmentData data = new EducationalAttainmentData(getActivity());
        mEducationLevels = (List<String>) data.getEducationLevels();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employment_info, container, false);

        // Find views.
        mSpinnerEduLevelDesired = (Spinner) view.findViewById(R.id.spn_desired_edu_level);
        mTextAgeGroup = (TextView) view.findViewById(R.id.tv_age_group);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_list_item_1, mEducationLevels);
        mSpinnerEduLevelDesired.setAdapter(adapter);

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
