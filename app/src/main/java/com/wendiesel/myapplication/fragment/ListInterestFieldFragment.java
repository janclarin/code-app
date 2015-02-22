package com.wendiesel.myapplication.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wendiesel.myapplication.R;
import com.wendiesel.myapplication.activity.InterestFieldDetailActivity;
import com.wendiesel.myapplication.data.TuitionData;

import java.util.Iterator;
import java.util.TreeSet;

public class ListInterestFieldFragment extends Fragment {

    public static final String KEY_INTEREST_FIELD = "key_interest_field";
    private OnListCareerPathListener mListener;
    private RecyclerView mRecyclerView;

    private TuitionData mTuitionData;
    private TreeSet<String> mInterestFields;

    public ListInterestFieldFragment() {
        // Required empty public constructor
    }

    public static ListInterestFieldFragment newInstance() {
        return new ListInterestFieldFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTuitionData = new TuitionData(getActivity().getApplicationContext());
        mInterestFields = (TreeSet<String>) mTuitionData.getFieldOfInterests();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_career_path, container, false);
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

        private String mInterestName;

        public InterestFieldHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            // Find the views.
            mTextInterestField = (TextView) itemView.findViewById(R.id.tv_field_of_interest);
            mTextAverageTuition = (TextView) itemView.findViewById(R.id.tv_average_tuition);
        }

        public void bindInterestField(String name, int tuition) {
            mInterestName = name;
            mTextInterestField.setText(name);
            mTextAverageTuition.setText(mAverageTuitionText + tuition);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), InterestFieldDetailActivity.class);
            intent.putExtra(KEY_INTEREST_FIELD, mInterestName);
            startActivity(intent);
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
