package org.mlab.research.koios.ui.survey;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.mlab.research.koios.CimonResponse;
import org.mlab.research.koios.Koios;
import org.mlab.research.koios.KoiosStudy;
import org.mlab.research.koios.R;
import org.mlab.research.koios.StudySurveyTask;
import org.mlab.research.koios.Util;
import org.mlab.research.koios.ui.formmaster.FormBuilder;
import org.mlab.research.koios.ui.formmaster.model.BaseFormElement;
import org.mlab.research.koios.ui.formmaster.model.FormElementAudioRecorder;
import org.mlab.research.koios.ui.formmaster.model.FormElementCommentMultiLine;
import org.mlab.research.koios.ui.formmaster.model.FormElementPickerDate;
import org.mlab.research.koios.ui.formmaster.model.FormElementPickerMulti;
import org.mlab.research.koios.ui.formmaster.model.FormElementPickerSingle;
import org.mlab.research.koios.ui.formmaster.model.FormElementTextMultiLine;
import org.mlab.research.koios.ui.formmaster.model.FormElementTextSingleLine;
import org.mlab.research.koios.ui.formmaster.model.FormHeader;
import org.mlab.research.koios.ui.formmaster.viewholder.FormAudioRecorderActivity;
import org.mlab.research.koios.ui.formmaster.viewholder.RecordingRequestListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SurveyDetailsActivity extends AppCompatActivity implements RecordingRequestListener {

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
        switch (item.getItemId()) {
            case R.id.survey_submit:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Are you sure?");
                builder.setMessage("Do you want to submit the survey");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (validateForm()) {
                            uploadData();
                        }
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

                return true;
            default:
                onBackPressed();
                return super.onOptionsItemSelected(item);
        }

    }

    private void uploadData() {
        ArrayList<SurveyResponse> responseList = new ArrayList<>();
        for (StudySurveyTask task : taskList) {
            if (task.getType().equalsIgnoreCase("instruction"))
                continue;
            BaseFormElement formElement = formBuilder.getFormElement(task.getTaskId());
            SurveyResponse surveyResponse = new SurveyResponse();
            surveyResponse.setStudyId(task.getStudyId());
            surveyResponse.setSurveyId(task.getSurveyId());
            surveyResponse.setTaskId(task.getTaskId());
            surveyResponse.setVersion(task.getVersion());
            if (task.getType().equalsIgnoreCase("recording")){
                surveyResponse.setAnswerType("object");
                surveyResponse.setObjectUrl(formElement.getValue());
            }else{
                surveyResponse.setAnswerType("value");
                surveyResponse.setAnswer(formElement.getValue());
            }
            surveyResponse.setSubmissionTime(System.currentTimeMillis()+"");
            if (task.getHasComment() == 1) {
                BaseFormElement commentElement = formBuilder.getFormElement(-task.getTaskId());
                if (commentElement != null && !commentElement.getValue().isEmpty()) {
                    surveyResponse.setComment(commentElement.getValue());
                }
            }
            responseList.add(surveyResponse);
        }
        if (responseList.size()>0){
            String uuid = Util.getUniqueDeviceId();
            String email = Util.getPreferenceData(Koios.getContext().getString(R.string.userEmail));
//        Log.d(TAG, "response size:" + r)

            Call<CimonResponse> call = Koios.getService().uplaodSurveyResponse(email, uuid, responseList);
            try {
                call.enqueue(new Callback<CimonResponse>() {
                    @Override
                    public void onResponse(Call<CimonResponse> call, Response<CimonResponse> response) {
                        if (response.body().getCode()==0){
                            //showDialog("Submission Successful", "Thank you for your participation to the survey.");
                            //update survey response time
                            String key = "survey-" + surveyId + "-lastresponse";
                            String value = Util.getCurrentTimeUptoSecond();
                            Util.saveDataToSharedPref(key, value);
                            setResult(Util.SURVEY_SUBMIT_SUCCESS);
                            finish();
                        }else {
                            showDialog("Submission Failure", "Please try later!");
                        }

                    }

                    @Override
                    public void onFailure(Call<CimonResponse> call, Throwable t) {
                        showDialog("Survey Submission", "Please try later!");
                    }
                });
            }catch (Exception e){

            }
        }

    }

    private boolean validateForm() {
        //if there was no comment type, then we could use formBuilder.validate(), but now we have to check manually
        boolean valid = true;
        for (StudySurveyTask task : taskList) {
            if (task.getType().equalsIgnoreCase("instruction")) {
                //do nothing
            } else {
                if (task.getIsRequired() == 1) {
                    BaseFormElement formElement = formBuilder.getFormElement(task.getTaskId());
                    if (task.getHasComment() == 1) {
                        BaseFormElement commentElement = formBuilder.getFormElement(-task.getTaskId());
                        if (formElement.getValue().isEmpty() && commentElement.getValue().isEmpty()) {
                            valid = false;
                            showDialog("Response Verification", "Answer required for question " + task.getTaskId());
                            break;
                        }
                    } else if (formElement.getValue().isEmpty()) {
                        valid = false;
                        showDialog("Response Verification", "Answer required for question " + task.getTaskId());
                        break;
                    }
                }
            }
        }
        return valid;
    }

    private void showDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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


    private void setupForm() {
        formRecyclerView = (RecyclerView) findViewById(R.id.surveyFormRecyclerView);
        formBuilder = new FormBuilder(this, formRecyclerView, this);


        List<BaseFormElement> formItems = new ArrayList<>();
        for (StudySurveyTask task : taskList) {

            if (task.getType().equalsIgnoreCase("instruction")) {
                FormHeader header = FormHeader.createInstance(task.getTaskText());
                formItems.add(header);

            } else {
                String taskText = (task.getIsRequired() == 1 ? "*Q. " : "Q. ") + task.getTaskText();
                FormHeader header = FormHeader.createInstance(taskText);
                formItems.add(header);
                BaseFormElement element = null;
                if (task.getType().equalsIgnoreCase("text")) {
                    element = FormElementTextSingleLine.createInstance().setTitle("Type Answer");
                    formItems.add(element);
                } else if (task.getType().equalsIgnoreCase("textarea")) {
                    element = FormElementTextMultiLine.createInstance().setTitle("Type Answer");
                    formItems.add(element);
                } else if (task.getType().equalsIgnoreCase("date")) {
                    element = FormElementPickerDate.createInstance().setTitle("Date").setDateFormat("MMM dd, yyyy");
                    formItems.add(element);
                } else if (task.getType().equalsIgnoreCase("selection")) {
                    String[] inputs = task.getPossibleInput().split("[|]");
                    element = FormElementPickerSingle.createInstance().setTitle("Select Answer").setOptions(Arrays.asList(inputs)).setPickerTitle("Pick any item");
                    formItems.add(element);
                } else if (task.getType().equalsIgnoreCase("choice")) {
                    String[] inputs = task.getPossibleInput().split("[|]");
                    element = FormElementPickerMulti.createInstance().setTitle("Select Answers").setOptions(Arrays.asList(inputs)).setPickerTitle("Pick one or more").setNegativeText("reset");
                    element.setRequired(task.getIsRequired() == 1);
                    formItems.add(element);
                } else if (task.getType().equalsIgnoreCase("recording")) {
                    element = FormElementAudioRecorder.createInstance().setTitle("Click to Start");
                    formItems.add(element);
                }
                if (element != null) {
                    element.setTag(task.getTaskId());
                    if (task.getIsRequired() == 1) {
                        element.setRequired(true);
                    } else {
                        element.setRequired(false);
                    }
                    if (task.getHasComment() == 1) {
                        BaseFormElement commentElement = FormElementCommentMultiLine.createInstance().setHint("Please Specify");
                        formItems.add(commentElement);
                        commentElement.setTag(-task.getTaskId());
                    }
                }
            }


            formBuilder.addFormElements(formItems);
        }
    }

    @Override
    public void processRecordingRequest(int elementTag) {
        Log.d(TAG, "process recording request");
        Intent intent = new Intent(this, FormAudioRecorderActivity.class);
        intent.putExtra("study_id", studyId);
        intent.putExtra("survey_id", surveyId);
        intent.putExtra("task_id", elementTag);
        startActivityForResult(intent, Util.RECORDING_UPLOAD_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Util.RECORDING_UPLOAD_REQUEST && resultCode == Util.RECORDING_UPLOAD_SUCCESS){
            try {
                int tag = data.getIntExtra("task_id", 0);
                String filePath = data.getStringExtra("file_path");
                Log.d(TAG, "tag:"+ tag + ", file path:"+ filePath);
                BaseFormElement formElement = formBuilder.getFormElement(tag);
                formElement.setValue(filePath);
                formElement.setTitle("You have answered");
                showRecordingUploadStatus("Success", "Please continue to the next question");

            }catch (Exception e){

            }

        }else if(requestCode == Util.RECORDING_UPLOAD_REQUEST && resultCode == Util.RECORDING_UPLOAD_FAILURE){
            showRecordingUploadStatus("Error", "Service not available, please try later.");
        }
    }

    private void showRecordingUploadStatus(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
}

