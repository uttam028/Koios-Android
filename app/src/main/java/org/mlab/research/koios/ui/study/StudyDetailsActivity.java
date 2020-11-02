package org.mlab.research.koios.ui.study;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import org.mlab.research.koios.Koios;
import org.mlab.research.koios.R;
import org.mlab.research.koios.StudySurveyTask;
import org.mlab.research.koios.ui.formmaster.FormBuilder;
import org.mlab.research.koios.ui.formmaster.model.BaseFormElement;
import org.mlab.research.koios.ui.formmaster.model.FormElementAudioRecorder;
import org.mlab.research.koios.ui.formmaster.model.FormElementPickerDate;
import org.mlab.research.koios.ui.formmaster.model.FormElementPickerMulti;
import org.mlab.research.koios.ui.formmaster.model.FormElementPickerSingle;
import org.mlab.research.koios.ui.formmaster.model.FormElementTextMultiLine;
import org.mlab.research.koios.ui.formmaster.model.FormElementTextSingleLine;
import org.mlab.research.koios.ui.formmaster.model.FormHeader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudyDetailsActivity extends AppCompatActivity {

    private static final String TAG = StudyDetailsActivity.class.getSimpleName() + "_debug";

    private int studyId;
    private TextView mStudyName;
    private String studyName;
    private RecyclerView formRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_details);

        // get study name and id
        studyId = getIntent().getIntExtra("study_id", 0);
        studyName = getIntent().getStringExtra("study_name");

        // change text in view to match study name that was clicked
        mStudyName = (TextView) findViewById(R.id.studyName);
        mStudyName.setText(studyName);





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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.study_enroll_menu, menu);
        return true;
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
//                startNextActivity();
//                updateAppState();
                return true;
            default:
                onBackPressed();
                return super.onOptionsItemSelected(item);
        }

    }



}