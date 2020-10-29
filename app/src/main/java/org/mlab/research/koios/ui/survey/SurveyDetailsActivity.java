package org.mlab.research.koios.ui.survey;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

public class SurveyDetailsActivity extends AppCompatActivity {

    private static final String TAG = SurveyDetailsActivity.class.getSimpleName() + "_debug";

    private int studyId;
    private int surveyId;
    private ArrayList<StudySurveyTask> taskList = new ArrayList<>();
    private RecyclerView formRecyclerView;
    private FormBuilder formBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_details);

        studyId = getIntent().getIntExtra("study_id", 0);
        surveyId = getIntent().getIntExtra("survey_id", 0);
        Log.d(TAG, "study id:" + studyId + ", survey id:" + surveyId);

        taskList = Koios.getDbHelper().getTaskListOfTheSurvey(studyId, surveyId);

        setupToolBar();

        setupForm();
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
        inflater.inflate(R.menu.survey_submit_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.survey_submit);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toast.makeText(getApplicationContext(),"menu "+ item.getItemId() + "," + mRecyclerView.getChildCount(),Toast.LENGTH_SHORT).show();
        switch (item.getItemId()){
            case R.id.survey_submit:
//                startNextActivity();
//                updateAppState();
                return true;
            default:
                onBackPressed();
                return super.onOptionsItemSelected(item);
        }

    }

    private void setupForm(){
        formRecyclerView = (RecyclerView) findViewById(R.id.surveyFormRecyclerView);
        formBuilder = new FormBuilder(this, formRecyclerView);

        List<BaseFormElement> formItems = new ArrayList<>();
        for (StudySurveyTask task:taskList){

            if (task.getType().equalsIgnoreCase("instruction")){
                String starter = (task.getType().equalsIgnoreCase("instruction") ? "" : (task.getIsRequired()==1?"*Q. ":"Q. "));
                String taskText = starter + task.getTaskText();
                FormHeader header = FormHeader.createInstance(task.getTaskText());
                formItems.add(header);

            }else{
                String taskText = (task.getIsRequired()==1?"*Q. ":"Q. ") + task.getTaskText();
                FormHeader header = FormHeader.createInstance(taskText);
                formItems.add(header);
                BaseFormElement element = null;
                if (task.getType().equalsIgnoreCase("text")){
                    element = FormElementTextSingleLine.createInstance().setTitle("Type Answer");
                    formItems.add(element);
                }else if (task.getType().equalsIgnoreCase("textarea")){
                    element = FormElementTextMultiLine.createInstance().setTitle("Type Answer");
                    formItems.add(element);
                }else if (task.getType().equalsIgnoreCase("date")){
                    element = FormElementPickerDate.createInstance().setTitle("Date").setDateFormat("MMM dd, yyyy");
                    formItems.add(element);
                }else if (task.getType().equalsIgnoreCase("selection")){
                    String [] inputs = task.getPossibleInput().split("[|]");
                    element = FormElementPickerSingle.createInstance().setTitle("Select Answer").setOptions(Arrays.asList(inputs)).setPickerTitle("Pick any item");
                    formItems.add(element);
                }else if (task.getType().equalsIgnoreCase("choice")){
                    String [] inputs = task.getPossibleInput().split("[|]");
                    element = FormElementPickerMulti.createInstance().setTitle("Select Answers").setOptions(Arrays.asList(inputs)).setPickerTitle("Pick one or more").setNegativeText("reset");
                    element.setRequired(task.getIsRequired()==1);
                    formItems.add(element);
                }else if (task.getType().equalsIgnoreCase("recording")){
                    element = FormElementAudioRecorder.createInstance().setTitle("Click to Start");
                    formItems.add(element);

                }
            }




            formBuilder.addFormElements(formItems);
        }
    }

}

