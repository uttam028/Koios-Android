package org.mlab.research.koios.ui.formmaster.viewholder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.mlab.research.koios.CimonResponse;
import org.mlab.research.koios.CimonService;
import org.mlab.research.koios.Koios;
import org.mlab.research.koios.R;
import org.mlab.research.koios.Util;

import java.io.File;
import java.io.IOException;

public class FormAudioRecorderActivity extends AppCompatActivity {

    private static final String TAG = FormAudioRecorderActivity.class.getSimpleName() + "_debug";

    private static String fileName = null;

    private ImageButton ibuttonReccorder;
    private MediaRecorder recorder = null;

    private ImageButton ibuttonPlayer;
    private MediaPlayer player = null;

    private TextView tvUploadInstruction;
    private TextView tvMicrophoneInstruction;
    boolean mStartRecording = true;
    boolean mStartPlaying = true;

    private int studyId;
    private int surveyId;
    private int taskId;


    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_audio_recorder);

        studyId = getIntent().getIntExtra("study_id", 0);
        surveyId = getIntent().getIntExtra("survey_id", 0);
        taskId = getIntent().getIntExtra("task_id", 0);

        setupToolBar();

        // Record to the external cache directory for visibility
        fileName = getExternalCacheDir().getAbsolutePath();
        fileName += "/recording.m4a";

        Log.d(TAG, "file name:"+ fileName);

        ActivityCompat.requestPermissions(this, permissions, Util.REQUEST_RECORD_AUDIO_PERMISSION);

        ibuttonReccorder = (ImageButton) findViewById(R.id.ibuttonRecorder);
        ibuttonPlayer = (ImageButton) findViewById(R.id.ibuttonPlayer);
        ibuttonPlayer.setVisibility(View.INVISIBLE);

        tvMicrophoneInstruction = (TextView) findViewById(R.id.tvMicrophoneInstruction);
        tvUploadInstruction = (TextView) findViewById(R.id.tvUploadInstrucction);

        tvMicrophoneInstruction.setText("Click on the microphone to begin recording");
        tvUploadInstruction.setText("");
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
        inflater.inflate(R.menu.form_audio_recorder_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.upload_audio_recording);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "option menu selected:"+ item.getItemId() + ", " + item.getTitle());
        //Toast.makeText(getApplicationContext(),"menu "+ item.getItemId() + "," + mRecyclerView.getChildCount(),Toast.LENGTH_SHORT).show();
        switch (item.getItemId()){
            case R.id.upload_audio_recording:
                revalidateUploadAction();
                return true;
            default:
                onBackPressed();
                return super.onOptionsItemSelected(item);
        }

    }

    private void revalidateUploadAction(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to upload the recording?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                uploadRecording();
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

    private void uploadRecording(){
        String email = Util.getPreferenceData(getString(R.string.userEmail));
        String uuid = Util.getPreferenceData(getString(R.string.uuid));
        File file = new File(fileName);

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);


        Call<CimonResponse> call = Koios.getService().uploadSurveyObject(email, uuid, studyId, body);
        try {
            call.enqueue(new Callback<CimonResponse>() {
                @Override
                public void onResponse(Call<CimonResponse> call, Response<CimonResponse> response) {
                    if (response.body().getCode()==0){
                        if (recorder !=null){
                            recorder.stop();
                            recorder = null;
                        }
                        if (player!=null){
                            player.stop();
                            player = null;
                        }

                        try {
                            file.delete();
                        }catch (Exception e){

                        }

                        String filePath = response.body().getMessage();
                        Intent intent = new Intent();
                        intent.putExtra("file_path", filePath);
                        intent.putExtra("task_id", taskId);
                        setResult(Util.RECORDING_UPLOAD_SUCCESS, intent);
                        finish();
                    }else{
                        setResult(Util.RECORDING_UPLOAD_FAILURE);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<CimonResponse> call, Throwable t) {
                    setResult(Util.RECORDING_UPLOAD_FAILURE);
                    finish();
                }
            });
        }catch (Exception e){
            setResult(Util.RECORDING_UPLOAD_FAILURE);
            finish();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case Util.REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();

    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        player = new MediaPlayer();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
//                stopPlaying();
                startPlaying(null);
            }
        });

        try {
            player.setDataSource(fileName);
//            player.setVolume(1, 1);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        player.release();
        player = null;

    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
        recorder.setAudioEncodingBitRate(16*44100);
        recorder.setAudioSamplingRate(44100);
//        recorder.set


        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }

        recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    public void startRecording(View view){
        onRecord(mStartRecording);
        if (mStartRecording) {
//            recordButton.setText("Stop recording");
            ibuttonReccorder.setImageDrawable(getDrawable(R.drawable.stop));
            tvMicrophoneInstruction.setText("Click the stop button to stop recording");
            tvUploadInstruction.setText("");
        } else {
//            recordButton.setText("Start recording");
            ibuttonReccorder.setImageDrawable(getDrawable(R.drawable.microphone));
            tvMicrophoneInstruction.setText("Click on the microphone to re-record");
            tvUploadInstruction.setText("Click upload to submit the file");
            if (ibuttonPlayer.getVisibility()==View.INVISIBLE){
                ibuttonPlayer.setVisibility(View.VISIBLE);
            }
        }
        mStartRecording = !mStartRecording;
    }

    public void startPlaying(View view){
        onPlay(mStartPlaying);
        if (mStartPlaying) {
//            playButton.setText("Stop playing");
            ibuttonPlayer.setImageDrawable(getDrawable(R.drawable.pause));
        } else {
//            playButton.setText("Start playing");
            ibuttonPlayer.setImageDrawable(getDrawable(R.drawable.play));
        }
        mStartPlaying = !mStartPlaying;

    }

    @Override
    public void onStop() {
        super.onStop();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }

        if (player != null) {
            player.release();
            player = null;
        }
    }
}