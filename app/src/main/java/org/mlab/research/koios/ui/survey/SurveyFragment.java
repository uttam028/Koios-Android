package org.mlab.research.koios.ui.survey;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.mlab.research.koios.Koios;
import org.mlab.research.koios.KoiosStudy;
import org.mlab.research.koios.R;
import org.mlab.research.koios.StudySurveyConfig;
import org.mlab.research.koios.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SurveyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SurveyFragment extends Fragment implements ItemClickListener {

    private static final String TAG = SurveyFragment.class.getSimpleName() + "_debug";

    private RecyclerView recyclerView;
    private SurveyOverviewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
//    private List<StudySurveyConfig> surveyList;
    private List<SurveyOverview> overviewList;



    public SurveyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SurveyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SurveyFragment newInstance() {
        SurveyFragment fragment = new SurveyFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "on create method...");
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
        observeLocalDB();
    }

    private void observeLocalDB(){
        final Observer<Boolean> surveyListObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d(TAG, "<<<<<<<<<<<,Survey list has been changed>>>>>>>>>>>>>>>");
                loadRecyclerView();
            }
        };
        Koios.getSurveyListChanged().observe(this, surveyListObserver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "on create view");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_survey, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "activity created from survey..");

        loadRecyclerView();
    }

    private void loadRecyclerView(){

        loadData();
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.surveyRecyclerView);
        adapter = new SurveyOverviewAdapter(this, overviewList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "view has been created.....");
    }

    @Override
    public void onItemClick(View view, int position) {
//        Toast.makeText(getContext(), "has been clicked " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this.getContext(), SurveyDetailsActivity.class);
        intent.putExtra("study_id", overviewList.get(position).getStudyId());
        intent.putExtra("survey_id", overviewList.get(position).getSurveyId());
        startActivityForResult(intent, Util.SURVEY_SUBMIT_REQUEST);
    }

    private void loadData(){
        overviewList = new ArrayList<>();
        for (KoiosStudy study:Koios.getDbHelper().getAllStudies()){
            for (StudySurveyConfig survey:Koios.getDbHelper().getSurveyConfigsOfTheStudy(study.getId())){
                String key = "survey-" + survey.getId() + "-lastresponse";
                String lastResponse = Util.getPreferenceData(key);
                if(survey.getSchedule().startsWith("once") && Util.isValidDate(lastResponse)){
                    //already took part in the survey, do not need to include them
                    continue;
                }
                overviewList.add(new SurveyOverview(survey.getStudyId(), survey.getId(), study.getName(), survey.getName(), survey.getSchedule(), lastResponse));
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Util.SURVEY_SUBMIT_SUCCESS){
            showDialog("Submission Successful", "Thank you for your participation to the survey.");
//            adapter.notifyDataSetChanged();
            loadRecyclerView();
        }else if (resultCode == Util.SURVEY_SUBMIT_FAILURE){

        }
    }

    private void showDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (title != null && !title.isEmpty()) {
            builder.setTitle(title);
        }
        if (message != null && !message.isEmpty()) {
            builder.setMessage(message);
        }
        //builder.setMessage("Are you sure?");
        builder.setPositiveButton("Ok, Got It.", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}