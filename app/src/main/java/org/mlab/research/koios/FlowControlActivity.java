package org.mlab.research.koios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class FlowControlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String appState = Util.getPreferenceData(getString(R.string.appState));
        Intent intent = null;
        if (appState.equalsIgnoreCase(getString(R.string.signedup))) {
            intent = new Intent(this, VerifyToken.class);
        } else if (appState.equalsIgnoreCase(getString(R.string.verified))) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, SignupActivity.class);
        }
        startActivity(intent);
        finish();

    }
}