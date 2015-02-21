package com.wendiesel.myapplication.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wendiesel.myapplication.R;

public class ListCareerPathFragment extends Fragment {

    private OnListCareerPathListener mListener;
    private RecyclerView mRecyclerView;

    public ListCareerPathFragment() {
        // Required empty public constructor
    }

    public static ListCareerPathFragment newInstance() {
        return new ListCareerPathFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_career_path, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list_career_paths);
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
}
