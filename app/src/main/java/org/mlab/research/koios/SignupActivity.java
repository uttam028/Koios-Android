package org.mlab.research.koios;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private final static String TAG = SignupActivity.class.getSimpleName() + "_debug";

    EditText emailText;
    TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailText = findViewById(R.id.textEmail);
        emailText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    hideKeyboard();
                }
            }
        });


        errorText = findViewById(R.id.textView);
        errorText.setText(R.string.emptyString);


        if (getAppState() == null || getAppState().isEmpty()) {
            //do nothing
        } else {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null && bundle.getString(getString(R.string.bundleSource)) != null) {
                Log.d(TAG, "email from bundle " + bundle.getString(getString(R.string.userEmail)) + ", appp state: " + getAppState());
                //coming from verification page
                emailText.setText(bundle.getString(getString(R.string.userEmail)));
            }
        }

    }


    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(emailText.getWindowToken(), 0);
    }

    public void signupAction(View view) {

        String identifier = Util.getUniqueDeviceId();
        String email = emailText.getText().toString().trim();
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorText.setText(R.string.emptyString);
            Util.saveDataToSharedPref(getString(R.string.userEmail), email);
            Util.saveDataToSharedPref(getString(R.string.uuid), identifier);

//            String uuid = Util.getUniqueDeviceId();

            Call<CimonResponse> call = Koios.getService().signup(email, identifier);
            try {
                call.enqueue(new Callback<CimonResponse>() {
                    @Override
                    public void onResponse(Call<CimonResponse> call, Response<CimonResponse> response) {
                        Log.d("Response from server", String.valueOf(response.body().getCode()) + ", " + response.body().getMessage());
                        if (response.body().getCode() == 0) {
                            updateAppState();
                            startNextActivity();
                        } else {
                            showError(response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<CimonResponse> call, Throwable t) {
                        showError(getString(R.string.serviceError));
                        Log.d("signup", "exception at call :" + t.getMessage());
                    }
                });
            } catch (Exception e) {
                Log.d("signup", "exception :" + e.getMessage());
                showError(getString(R.string.serviceError));
            }

        } else {

            Log.d("signup", "email pattern mistmatch");
            errorText.setText(R.string.invalidEmail);
        }
    }

    private void updateAppState() {
        Util.saveDataToSharedPref(getString(R.string.appState), getString(R.string.signedup));
        Log.d("signup", "app_state:" + getString(R.string.appState));

    }

    private String getAppState() {
        return Util.getPreferenceData(getString(R.string.appState));
    }

    private void startNextActivity() {
        Intent intent = new Intent(this, VerifyToken.class);
        startActivity(intent);
        finish();
    }

    private void showError(String errorMessage) {
        errorText.setText(errorMessage);
    }


}
