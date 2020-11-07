package org.mlab.research.koios.ui.study;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.Toast;

import org.mlab.research.koios.CimonResponse;
import org.mlab.research.koios.Koios;
import org.mlab.research.koios.KoiosStudy;
import org.mlab.research.koios.R;
import org.mlab.research.koios.StudySurveyConfig;
import org.mlab.research.koios.StudySyncer;
import org.mlab.research.koios.Util;
import org.mlab.research.koios.ui.study.StudyOverviewAdapter;
import org.mlab.research.koios.ui.study.StudyOverview;
import org.mlab.research.koios.ui.survey.SurveyDetailsActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OpenStudiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OpenStudiesFragment extends Fragment implements ItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private StudyOverviewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<StudyOverview> overviewList;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OpenStudiesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OpenStudiesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OpenStudiesFragment newInstance(String param1, String param2) {
        OpenStudiesFragment fragment = new OpenStudiesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_open_studies, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        loadData();
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.openStudiesRecyclerView);
        adapter = new StudyOverviewAdapter(this, overviewList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this.getContext(), StudyDetailsActivity.class);

        // TODO: make this pass a object isntead of several variables
        intent.putExtra("study_id", overviewList.get(position).getStudyId());
        intent.putExtra("study_name", overviewList.get(position).getStudyName());
        intent.putExtra("study_description", overviewList.get(position).getStudyDescription());
        intent.putExtra("study_instructions", overviewList.get(position).getStudyInstructions());
        intent.putExtra("enrolled", false);
        startActivity(intent);

    }

    public void loadData(){
        overviewList = new ArrayList<>();

        overviewList.add(new StudyOverview(123, "Open Study 1", "University of Notre Dame",
                "Desc", "Instr", ""));
        overviewList.add(new StudyOverview(123, "Open Study 2", "University of Notre Dame",
                "Desc", "Instr", ""));


    }

//    public void loadData(final Callback<KoiosStudy> myCallback){
//        overviewList = new ArrayList<>();
//        // get open studies
//        String email = Util.getPreferenceData(Koios.getContext().getString(R.string.userEmail));
//
//
//
//        // make cimon call to get list of open studies
//        Call<ArrayList<KoiosStudy>> call = Koios.getService().getOpenStudies(email);
//        try {
//            call.enqueue(new Callback<ArrayList<KoiosStudy>>() {
//                @Override
//                public void onResponse(Call<ArrayList<KoiosStudy>> call, final Response<ArrayList<KoiosStudy>> response) {
//
//                    Log.d("tag", "This is a test");
//
//                    // get ArrayList of open studies
//                    ArrayList<KoiosStudy> openStudies = response.body() == null ? new ArrayList<>() : response.body());
//
//                    // HOW DO I UPDATE THE UI WITH openStudies??
//
//
//
//                }
//
//
//                @Override
//                public void onFailure(Call<ArrayList<KoiosStudy>> call, Throwable t) {
//                    Log.d("openstudies", "failed to get Open Studies data " + t.getMessage());
//                    callbacks.onError();
//                }
//            });
//        } catch (Exception e) {
//            Log.d("openstudies", "error in getting open studies data " + e.getMessage());
//        }
//    }
//
//    public interface myCallback {
//        void onSuccess(ArrayList<KoiosStudy> studies);
//
//        void onError();
//    }



}

