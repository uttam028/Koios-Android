package org.mlab.research.koios.ui.study;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mlab.research.koios.Koios;
import org.mlab.research.koios.KoiosStudy;
import org.mlab.research.koios.R;
import org.mlab.research.koios.Util;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyStudiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyStudiesFragment extends Fragment implements ItemClickListener {

    private static final String TAG = MyStudiesFragment.class.getSimpleName() + "_debug";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private StudyOverviewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<KoiosStudy> overviewList;
    private int leavingStudyPosition = -1;


    public MyStudiesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyStudiesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyStudiesFragment newInstance(String param1, String param2) {
        MyStudiesFragment fragment = new MyStudiesFragment();
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
        observeLocalDB();
    }

    private void observeLocalDB(){
        final Observer<Boolean> studyListObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d(TAG, "<<<<<<<<<<<My study list has been changed>>>>>>>>>>>>>>>");
                loadRecyclerView();
            }
        };
        Koios.getStudyEnrolled().observe(this, studyListObserver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_studies, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadRecyclerView();
    }

    private void loadRecyclerView(){
        overviewList = Koios.getDbHelper().getAllStudies();
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.myStudiesRecyclerView);
        adapter = new StudyOverviewAdapter(this, overviewList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }


    @Override
    public void onItemClick(View view, int position) {
        leavingStudyPosition = position;
        Intent intent = new Intent(this.getContext(), StudyDetailsActivity.class);

        // TODO: make this pass a object isntead of several variables
        intent.putExtra("study", overviewList.get(position));
        intent.putExtra("action", "leave");
//        startActivity(intent);
        startActivityForResult(intent, Util.LEAVE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "request code:" + requestCode + ", result code:" + resultCode);

        if (requestCode == Util.LEAVE_REQUEST_CODE && resultCode == Util.STUDY_LEAVE_SUCCESS) {

            showConfirmation("Confirmation", "Thank you for your participation to the study.");
            try {
                overviewList.remove(leavingStudyPosition);
                adapter.notifyItemRemoved(leavingStudyPosition);
            }catch (Exception e){

            }
        } else if (requestCode == Util.LEAVE_REQUEST_CODE && resultCode == Util.STUDY_LEAVE_FAILURE) {
            showConfirmation("Error", "Service not available. Please try later.");
        }

    }

    private void showConfirmation(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }}