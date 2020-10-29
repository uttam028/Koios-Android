package org.mlab.research.koios.ui.formmaster.viewholder;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.mlab.research.koios.R;

public class FormAudioRecorderActivity extends AppCompatActivity {

    private static final String TAG = FormAudioRecorderActivity.class.getSimpleName() + "_debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_audio_recorder);

        Log.d(TAG, "loading audio recorder activity...");
//        invalidateOptionsMenu();
//        setupToolBar();
    }

//    private void setupToolBar() {
//
//        final ActionBar actionBar = getSupportActionBar();
//
//        if (actionBar != null) {
//            actionBar.setDisplayShowTitleEnabled(true);
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeButtonEnabled(true);
//        }
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.form_audio_recorder_menu, menu);
//        return true;
//    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        MenuItem item = menu.findItem(R.id.upload_audio_recording);
//        return super.onPrepareOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Log.d(TAG, "option menu selected:"+ item.getItemId() + ", " + item.getTitle());
//        //Toast.makeText(getApplicationContext(),"menu "+ item.getItemId() + "," + mRecyclerView.getChildCount(),Toast.LENGTH_SHORT).show();
//        switch (item.getItemId()){
//            case R.id.upload_audio_recording:
////                startNextActivity();
////                updateAppState();
//                return true;
//            default:
//                onBackPressed();
//                return super.onOptionsItemSelected(item);
//        }
//
//    }

}