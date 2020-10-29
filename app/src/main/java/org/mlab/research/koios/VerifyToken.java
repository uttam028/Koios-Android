package org.mlab.research.koios;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VerifyToken extends AppCompatActivity {


    EditText editTextToken;
    TextView textViewVerifyError;
    TextView textViewVerifyInst;
    Button buttonVerify;
    Button buttonTryEmail;

    CimonService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_token);

        // Build the CIMON web service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Util.baseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(CimonService.class);


        String instruction = getString(R.string.instVerificationCodeText) + " " + Util.getPreferenceData(getString(R.string.userEmail));
        textViewVerifyInst = findViewById(R.id.textViewVerifyInst);
        textViewVerifyInst.setText(instruction);


        textViewVerifyError = findViewById(R.id.textViewVerifyError);
        textViewVerifyError.setText(getString(R.string.emptyString));

        buttonVerify = findViewById(R.id.buttonVerify);

        buttonVerify.setText(getString(R.string.buttonTextResend));

        editTextToken = findViewById(R.id.editTextToken);
        editTextToken.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    hideKeyboard();
                }
            }
        });
        editTextToken.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    buttonVerify.setText(R.string.buttonTextVerify);
                } else {
                    buttonVerify.setText(R.string.buttonTextResend);
                }
            }
        });

    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(editTextToken.getWindowToken(), 0);
    }


    public void verifyAction(View view) {

        String uuid = Util.getUniqueDeviceId();
        String email = Util.getPreferenceData(getString(R.string.userEmail));
        if (buttonVerify.getText().toString().equalsIgnoreCase(getString(R.string.buttonTextResend))) {
            //resend action
            Call<CimonResponse> call = service.signup(email, uuid);
            try {
                call.enqueue(new Callback<CimonResponse>() {
                    @Override
                    public void onResponse(Call<CimonResponse> call, Response<CimonResponse> response) {
                    }

                    @Override
                    public void onFailure(Call<CimonResponse> call, Throwable t) {
                        textViewVerifyError.setText(R.string.serviceUnavailable);
                    }
                });
            } catch (Exception e) {
                textViewVerifyError.setText(R.string.serviceUnavailable);
            }

        } else {
            //verify action
            String tokenStr = editTextToken.getText().toString().trim();
            if (tokenStr.length() < 4) {
                textViewVerifyError.setText(R.string.invalidToken);
            } else {
                Call<CimonResponse> call = service.verifyToken(email, uuid, tokenStr);
                try {
                    call.enqueue(new Callback<CimonResponse>() {
                        @Override
                        public void onResponse(Call<CimonResponse> call, Response<CimonResponse> response) {
                            if (response.body().getCode()==0){
                                //success
                                Util.uploadTokenIfRequired();
                                updateAppState();
                                startNextActivity();
                            }else {
                                textViewVerifyError.setText(R.string.invalidToken);
                            }
                        }

                        @Override
                        public void onFailure(Call<CimonResponse> call, Throwable t) {
                            textViewVerifyError.setText(R.string.serviceUnavailable);
                        }
                    });
                }catch (Exception e){
                    textViewVerifyError.setText(R.string.serviceUnavailable);
                }
            }

        }
    }


    private void updateAppState(){
        Util.saveDataToSharedPref(getString(R.string.appState), getString(R.string.verified));
        Log.d("verified", "app_state:"+ getString(R.string.appState));

    }

    private void startNextActivity(){
        //Intent intent = new Intent(this, ProfileActivity.class);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    public void signupAnotherEmail(View view) {
        Log.d("verification", "program comes here...");
        Intent intent = new Intent(this, SignupActivity.class);
        Bundle bundle = new Bundle();
        String key = getString(R.string.userEmail);
        bundle.putString(key, Util.getPreferenceData(key));

        String sourceKey = getString(R.string.bundleSource);
        bundle.putString(sourceKey, "verification");
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

}
