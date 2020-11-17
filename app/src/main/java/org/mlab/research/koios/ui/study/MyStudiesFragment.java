package org.mlab.research.koios.ui.study;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mlab.research.koios.Koios;
import org.mlab.research.koios.KoiosStudy;
import org.mlab.research.koios.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyStudiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyStudiesFragment extends Fragment implements ItemClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private EnrolledStudyOverviewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<EnrolledStudyOverview> overviewList;

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


        loadData();
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.myStudiesRecyclerView);
        adapter = new EnrolledStudyOverviewAdapter(this, overviewList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    public void loadData(){
        overviewList = new ArrayList<>();

        overviewList.add(new EnrolledStudyOverview(123, "My Enrolled Study 1", "University of Notre Dame",
                "Desc", "Instr", ""));
        overviewList.add(new EnrolledStudyOverview(123, "My Enrolled Study 2", "University of Notre Dame",
                "Desc", "Instr", ""));

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this.getContext(), StudyDetailsActivity.class);

        // TODO: make this pass a object isntead of several variables
        intent.putExtra("study_id", overviewList.get(position).getStudyId());
        intent.putExtra("study_name", overviewList.get(position).getStudyName());
        intent.putExtra("study_description", overviewList.get(position).getStudyDescription());
        intent.putExtra("study_instructions", overviewList.get(position).getStudyInstructions());
        intent.putExtra("enrolled", true);
        startActivity(intent);

    }

    //    public void loadData(final Callback<KoiosStudy> myCallback){
//        overviewList = new ArrayList<>();
//        // get open studies
//        String uuid = Util.getUniqueDeviceId();
//        String email = Util.getPreferenceData(Koios.getContext().getString(R.string.userEmail));
//
//
//
//        // make cimon call to get list of open studies
//       Call<ArrayList<KoiosStudy>> call = Koios.getService().getEnrolledStudies(email, "");
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