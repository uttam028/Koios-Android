package org.mlab.research.koios.ui.study;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mlab.research.koios.Koios;
import org.mlab.research.koios.KoiosStudy;
import org.mlab.research.koios.MainActivity;
import org.mlab.research.koios.R;
import org.mlab.research.koios.Util;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OpenStudiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OpenStudiesFragment extends Fragment implements ItemClickListener {

    private static final String TAG = OpenStudiesFragment.class.getSimpleName() + "_debug";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private StudyOverviewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<KoiosStudy> overviewList;
    private int enrolledStudyPosition = -1;

    ProgressDialog progress;


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

        Log.d(TAG, "actvity created for fragment..");
        loadData();

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this.getContext(), StudyDetailsActivity.class);

        // TODO: make this pass a object isntead of several variables
        intent.putExtra("study", overviewList.get(position));
        intent.putExtra("action", "enroll");
        enrolledStudyPosition = position;

//        startActivity(intent);
        startActivityForResult(intent, Util.ENROLL_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "request code:" + requestCode + ", result code:" + resultCode);

        if (requestCode == Util.ENROLL_REQUEST_CODE && resultCode == Util.STUDY_ENROLLMENT_SUCCESS) {

            showConfirmation("Confirmation", "You have successfully enrolled to the study.");
            try {
                overviewList.remove(enrolledStudyPosition);
                adapter.notifyItemRemoved(enrolledStudyPosition);
            }catch (Exception e){

            }
        } else if (requestCode == Util.ENROLL_REQUEST_CODE && resultCode == Util.STUDY_ENROLLMENT_FAILURE) {
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
    }

    private void updateRecycleView() {
        Log.d(TAG, "update recyclw view:" + overviewList.size());
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.openStudiesRecyclerView);
        adapter = new StudyOverviewAdapter(this, overviewList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void loadData() {
        overviewList = new ArrayList<>();
        // get open studies
        String uuid = Util.getUniqueDeviceId();
        String email = Util.getPreferenceData(Koios.getContext().getString(R.string.userEmail));

        overviewList = new ArrayList<>();

        Call<ArrayList<KoiosStudy>> call = Koios.getService().getOpenStudies(email);
        progress = new ProgressDialog(getParentFragment().getContext());
        progress.setMessage("Please wait...It is downloading");
        progress.setIndeterminate(false);
        progress.setCancelable(false);
        progress.show();
        try {
            call.enqueue(new Callback<ArrayList<KoiosStudy>>() {
                @Override
                public void onResponse(Call<ArrayList<KoiosStudy>> call, Response<ArrayList<KoiosStudy>> response) {
                    progress.dismiss();
                    for (KoiosStudy study : response.body()) {
                        Log.d(TAG, "response :" + study.getId() + "," + study.getName());
                        overviewList.add(study);
                    }
                    updateRecycleView();
                }

                @Override
                public void onFailure(Call<ArrayList<KoiosStudy>> call, Throwable t) {

                }
            });
        } catch (Exception e) {

        }
    }

}

