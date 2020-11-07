package org.mlab.research.koios.ui.study;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.mlab.research.koios.CimonResponse;
import org.mlab.research.koios.CimonService;
import org.mlab.research.koios.Koios;
import org.mlab.research.koios.R;
import org.mlab.research.koios.StudySyncer;
import org.mlab.research.koios.Util;
import org.mlab.research.koios.CimonResponse;

import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mlab.research.koios.Util.getCurrentTimeUptoSecond;

public class StudyDetailsActivity extends AppCompatActivity{

    private static final String TAG = StudyDetailsActivity.class.getSimpleName() + "_debug";

    private int studyId;
    private TextView mStudyName;
    private String studyName;
    private TextView mStudyDescription;
    private String studyDescription;
    private TextView mStudyInstructions;
    private String studyInstructions;
    private boolean enrollmentStatus;
    private RecyclerView formRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_details);

        // get study name and id
        studyId = getIntent().getIntExtra("study_id", 0);
        studyName = getIntent().getStringExtra("study_name");
        studyDescription = getIntent().getStringExtra("study_description");
        studyInstructions = getIntent().getStringExtra("study_instructions");
        enrollmentStatus = getIntent().getBooleanExtra("enrolled", false);

        // change text in view to match study name that was clicked
        mStudyName = (TextView) findViewById(R.id.studyName);
        mStudyName.setText(studyName);

        // Programatically add study description
        if (studyDescription != null) {
            mStudyDescription = (TextView) findViewById(R.id.descriptionTextView);
            mStudyDescription.setText(studyDescription);
        }

        // Programatically add study instruction
        if (studyInstructions != null) {
            mStudyInstructions = (TextView) findViewById(R.id.instructionsTextView);
            mStudyInstructions.setText(studyInstructions);
        }


        Log.d(TAG, "study id:");


        setupToolBar();

    }

    private void setupToolBar() {

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (enrollmentStatus == false) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.study_enroll_menu, menu);
            return true;
        }
        return false;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.study_enroll_menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toast.makeText(getApplicationContext(),"menu "+ item.getItemId() + "," + mRecyclerView.getChildCount(),Toast.LENGTH_SHORT).show();
        switch (item.getItemId()){
            case R.id.study_enroll_menu:
                String uuid = Util.getUniqueDeviceId();
                String email = Util.getPreferenceData(Koios.getContext().getString(R.string.userEmail));
                String joinTime = getCurrentTimeUptoSecond();
                String joinTimeZone = TimeZone.getDefault().getDisplayName();



                // make cimon enrollment call
                Call<CimonResponse> call = Koios.getService().enrollToStudy(Integer.toString(studyId), email, uuid, joinTime, joinTimeZone);
                try {
                    call.enqueue(new Callback<CimonResponse>() {
                        @Override
                        public void onResponse(Call<CimonResponse> call, Response<CimonResponse> response) {
                            Log.d("Response from server", String.valueOf(response.body().getCode()) + ", " + response.body().getMessage());
                            if (response.body().getCode() == 0) {
                                // enrollment successful
                                Log.d("enrollment", "Enrollment Succeeded");

                                // todo: toast success notifcation
                                // from https://developer.android.com/guide/topics/ui/notifiers/toasts#java
                                Context context = getApplicationContext();
                                CharSequence text = "Enrollment successful";
                                int duration = Toast.LENGTH_SHORT;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();

                                // todo; syncStudies call
                                StudySyncer.getInstance().syncStudies();

                            } else {
                                // enrollment failed
                                Log.d("enrollment", "Enrollment Failed");
                            }
                        }

                        @Override
                        public void onFailure(Call<CimonResponse> call, Throwable t) {
                            Log.d("enrollment", t.getMessage());
                        }
                    });
                }
                catch (Exception e) {
                    Log.d("signup", "exception :" + e.getMessage());
                }


                return true;
            default:
                onBackPressed();
                return super.onOptionsItemSelected(item);
        }

    }



}