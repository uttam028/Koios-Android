package org.mlab.research.koios.ui.study;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.mlab.research.koios.CimonResponse;
import org.mlab.research.koios.Koios;
import org.mlab.research.koios.KoiosStudy;
import org.mlab.research.koios.R;
import org.mlab.research.koios.StudySyncer;
import org.mlab.research.koios.Util;

import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mlab.research.koios.Util.getCurrentTimeUptoSecond;

public class StudyDetailsActivity extends AppCompatActivity {

    private static final String TAG = StudyDetailsActivity.class.getSimpleName() + "_debug";

    private TextView tvStudyName;
    private TextView tvOrganization;
    private TextView tvStudyDescription;
    private TextView tvStudyInstructions;

    private KoiosStudy study;
    private String action = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_details);

        Bundle data = getIntent().getExtras();
        study = (KoiosStudy) data.getParcelable("study");
        action = getIntent().getStringExtra("action");

        // change text in view to match study name that was clicked
        tvStudyName = (TextView) findViewById(R.id.openStudyName);
        tvStudyName.setText(study.getName());

        tvOrganization = (TextView) findViewById(R.id.textViewOrganization);
        tvOrganization.setText(study.getOrganization());

        tvStudyDescription = (TextView) findViewById(R.id.descriptionTextView);
        tvStudyDescription.setText(study.getDescription());
        tvStudyInstructions = (TextView) findViewById(R.id.instructionsTextView);
        tvStudyInstructions.setText(study.getInstruction());
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
//        if (enrollmentStatus == false) {
//        }
//        return false;
        if (!action.isEmpty()) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.study_enroll_menu, menu);
            return true;
        }
        return false;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.study_enroll_menu);
        if (action.equalsIgnoreCase("enroll")) {
            item.setTitle("Enroll");
        } else if (action.equalsIgnoreCase("leave")) {
            item.setTitle("Leave");
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toast.makeText(getApplicationContext(),"menu "+ item.getItemId() + "," + mRecyclerView.getChildCount(),Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.study_enroll_menu:
                if (item.getTitle().toString().equalsIgnoreCase("enroll")){
                    revalidateEnrollAction();
                }else if (item.getTitle().toString().equalsIgnoreCase("leave")){
                    revalidateLeaveAction();
                }

                return true;
            default:
                onBackPressed();
                return super.onOptionsItemSelected(item);
        }

    }

    private void revalidateEnrollAction(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you like to join?");
//        builder.setMessage(message);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                enrollToStudy();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void revalidateLeaveAction(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure?");
        builder.setMessage("Do you want to leave the study?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                leaveFromStudy();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }


    private void leaveFromStudy(){
        String email = Util.getPreferenceData(Koios.getContext().getString(R.string.userEmail));

        Call<CimonResponse> call = Koios.getService().leaveStudy(study.getId(), email);
        try {
            call.enqueue(new Callback<CimonResponse>() {
                @Override
                public void onResponse(Call<CimonResponse> call, Response<CimonResponse> response) {
                    Log.d("Response from server", String.valueOf(response.body().getCode()) + ", " + response.body().getMessage());
                    if (response.body().getCode() == 0) {
                        // enrollment successful
                        Log.d("leave", "leave Succeeded");

                        // todo; syncStudies call
                        StudySyncer.getInstance().syncStudies();

                        returnResult(Util.STUDY_LEAVE_SUCCESS);
                    } else {
                        // enrollment failed
                        Log.d("leave", "leave Failed");
                        returnResult(Util.STUDY_LEAVE_FAILURE);
                    }

                }

                @Override
                public void onFailure(Call<CimonResponse> call, Throwable t) {
                    Log.d("leave", t.getMessage());
                    returnResult(Util.STUDY_LEAVE_FAILURE);

                }
            });
        }catch (Exception e){
            returnResult(Util.STUDY_LEAVE_FAILURE);
        }
    }

    private void enrollToStudy() {
        String uuid = Util.getUniqueDeviceId();
        String email = Util.getPreferenceData(Koios.getContext().getString(R.string.userEmail));
        String joinTime = getCurrentTimeUptoSecond();
        String joinTimeZone = TimeZone.getDefault().getDisplayName();


        // make cimon enrollment call
        Call<CimonResponse> call = Koios.getService().enrollToStudy(study.getId(), email, uuid, joinTime, joinTimeZone);
        try {
            call.enqueue(new Callback<CimonResponse>() {
                @Override
                public void onResponse(Call<CimonResponse> call, Response<CimonResponse> response) {
                    Log.d("Response from server", String.valueOf(response.body().getCode()) + ", " + response.body().getMessage());
                    if (response.body().getCode() == 0) {
                        // enrollment successful
                        Log.d("enrollment", "Enrollment Succeeded");

                        // todo; syncStudies call
                        StudySyncer.getInstance().syncStudies();


                        returnResult(Util.STUDY_ENROLLMENT_SUCCESS);
                    } else {
                        // enrollment failed
                        Log.d("enrollment", "Enrollment Failed");
                        returnResult(Util.STUDY_ENROLLMENT_FAILURE);
                    }
                }

                @Override
                public void onFailure(Call<CimonResponse> call, Throwable t) {
                    Log.d("enrollment", t.getMessage());
                    returnResult(Util.STUDY_ENROLLMENT_FAILURE);
                }
            });
        } catch (Exception e) {
            Log.d("signup", "exception :" + e.getMessage());
            returnResult(Util.STUDY_ENROLLMENT_FAILURE);
        }

    }

    private void returnResult(int resultCode){
        setResult(resultCode, new Intent());
        finish();
    }

}