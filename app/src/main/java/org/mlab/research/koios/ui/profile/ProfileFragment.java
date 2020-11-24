package org.mlab.research.koios.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import org.mlab.research.koios.BuildConfig;
import org.mlab.research.koios.CimonResponse;
import org.mlab.research.koios.Koios;
import org.mlab.research.koios.R;
import org.mlab.research.koios.StudySyncer;
import org.mlab.research.koios.Util;
import org.mlab.research.koios.ui.formmaster.FormBuilder;
import org.mlab.research.koios.ui.formmaster.model.BaseFormElement;
import org.mlab.research.koios.ui.formmaster.model.FormElementPickerDate;
import org.mlab.research.koios.ui.formmaster.model.FormElementPickerSingle;
import org.mlab.research.koios.ui.formmaster.model.FormElementTextSingleLine;
import org.mlab.research.koios.ui.formmaster.model.FormHeader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileFragment extends Fragment {
    private static final String TAG = ProfileFragment.class.getSimpleName()+"_debug";
    private static final String DEFAULT_TYPE_HINT = "Enter Text Here";
    private static final String DEFAULT_SELECT_HINT = "Click to Select";

    private static final int EMAIL_TAG = 1;
    private static final int FIRST_NAME_TAG = 2;
    private static final int LAST_NAME_TAG = 3;
    private static final int DEVICE_TAG = 4;
    private static final int BDATE_TAG = 5;
    private static final int GENDER_TAG = 6;
    private static final int ETHNICITY_TAG = 7;
    private static final int LANGUAGE_TAG = 8;
    private static final int EDUCATION_TAG = 9;
    private static final int ORG_TAG = 10;
    private static final int APP_TAG = 11;




    private RecyclerView mRecyclerView;
    private FormBuilder mFormBuilder;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "on create view");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupForm();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // TODO Add your menu entries here
        inflater.inflate(R.menu.profile_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_menu:
                updateProfile();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupForm(){
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.profileRecyclerView);
        mFormBuilder = new FormBuilder(getActivity(), mRecyclerView);

        String email = Util.getPreferenceData(Koios.getContext().getString(R.string.userEmail));
        FormElementTextSingleLine emailElement = FormElementTextSingleLine.createInstance().setTitle("Email").setValue(email);
        emailElement.setEnabled(false);
        emailElement.setTag(EMAIL_TAG);


        //Name Section
        FormHeader nameHeader = FormHeader.createInstance("");
        String firstName = Util.getPreferenceData(getString(R.string.firstName));
        FormElementTextSingleLine firstNameElement = FormElementTextSingleLine.createInstance().setTitle("First Name");
        firstNameElement.setTag(FIRST_NAME_TAG).setHint(DEFAULT_TYPE_HINT).setValue(firstName).setEnabled(true);

        String lastName = Util.getPreferenceData(getString(R.string.lastName));
        FormElementTextSingleLine lastNameElement = FormElementTextSingleLine.createInstance().setTitle("Last Name");
        lastNameElement.setTag(LAST_NAME_TAG).setHint(DEFAULT_TYPE_HINT).setValue(lastName).setEnabled(true);

        //Device Section
        FormHeader deviceHeader = FormHeader.createInstance("");
        String deviceModel = Util.getPreferenceData(this.getString(R.string.deviceModel));
        FormElementTextSingleLine deviceElement = FormElementTextSingleLine.createInstance().setTitle("Device Model");
        deviceElement.setTag(DEVICE_TAG).setHint(DEFAULT_TYPE_HINT).setValue(deviceModel);

        //Demographic Section
        FormHeader demographicHeader = FormHeader.createInstance("");
        String birthDate = Util.getPreferenceData(this.getString(R.string.birthDate));
        FormElementPickerDate birthdateElement = FormElementPickerDate.createInstance().setTitle("Birth Date");
        birthdateElement.setTag(BDATE_TAG).setHint(DEFAULT_SELECT_HINT).setValue(birthDate);

        String gender = Util.getPreferenceData(this.getString(R.string.gender));
        List<String> genderList = Arrays.asList(new String[]{"Male", "Female", "Other", "I do not like to share"});
        FormElementPickerSingle genderElement = FormElementPickerSingle.createInstance().setTitle("Gender");
        genderElement.setTag(GENDER_TAG).setOptions(genderList).setHint(DEFAULT_SELECT_HINT).setValue(gender);

        String ethnicity = Util.getPreferenceData(this.getString(R.string.ethnicity));
        List<String> ethnicityList = Arrays.asList(new String[]{"Caucasian", "Latino/Hispanic", "African", "Middle Eastern", "South Asian", "East Asian", "Mixed", "Other"});
        FormElementPickerSingle ethnicityElement = FormElementPickerSingle.createInstance().setTitle("Ethnicity");
        ethnicityElement.setTag(ETHNICITY_TAG).setOptions(ethnicityList).setHint(DEFAULT_SELECT_HINT).setValue(ethnicity);


        //Other Section
        FormHeader otherHeader = FormHeader.createInstance("");
        String language = Util.getPreferenceData(this.getString(R.string.language));
        List<String> langugaeList = Arrays.asList(new String[]{"English", "Spanish", "Chinese", "French", "Italian", "German", "Japanese", "Russian", "Hindi", "Arabic", "Other"});
        FormElementPickerSingle languageElement = FormElementPickerSingle.createInstance().setTitle("Language");
        languageElement.setTag(LANGUAGE_TAG).setOptions(langugaeList).setHint(DEFAULT_SELECT_HINT).setPickerTitle("Select Your Native Language").setValue(language);

        String education = Util.getPreferenceData(this.getString(R.string.education));
        List<String> educationList = Arrays.asList(new String[]{"High School", "Some College", "Bachelor's Degree", "Graduate School", "Doctorate"});
        FormElementPickerSingle educationElement = FormElementPickerSingle.createInstance().setTitle("Education");
        educationElement.setTag(EDUCATION_TAG).setOptions(educationList).setHint(DEFAULT_SELECT_HINT).setPickerTitle("Select Highest Level").setValue(education);

        String organization = Util.getPreferenceData(this.getString(R.string.organization));
        FormElementTextSingleLine orgElement = FormElementTextSingleLine.createInstance().setTitle("Organization");
        orgElement.setTag(ORG_TAG).setHint(DEFAULT_TYPE_HINT).setValue(organization).setEnabled(true);

        String version = BuildConfig.VERSION_NAME;
        FormElementTextSingleLine versionElement = FormElementTextSingleLine.createInstance().setTitle("App Version").setValue(version);
        versionElement.setEnabled(false).setTag(APP_TAG);

        FormHeader footer = FormHeader.createInstance("");

        List<BaseFormElement> formItems = new ArrayList<>();
        formItems.add(emailElement);

        formItems.add(nameHeader);
        formItems.add(firstNameElement);
        formItems.add(lastNameElement);

        formItems.add(deviceHeader);
        formItems.add(deviceElement);

        formItems.add(demographicHeader);
        formItems.add(birthdateElement);
        formItems.add(genderElement);
        formItems.add(ethnicityElement);

        formItems.add(otherHeader);
        formItems.add(languageElement);
        formItems.add(educationElement);
        formItems.add(orgElement);
        formItems.add(versionElement);

        formItems.add(footer);

        mFormBuilder.addFormElements(formItems);
    }

    private void updateProfile(){
        String uuid = Util.getPreferenceData(getString(R.string.uuid));
        String email = Util.getPreferenceData(getString(R.string.userEmail));
        String firstName = mFormBuilder.getFormElement(FIRST_NAME_TAG).getValue();
        String lastName = mFormBuilder.getFormElement(LAST_NAME_TAG).getValue();
        String deviceModel = mFormBuilder.getFormElement(DEVICE_TAG).getValue();
        String birthDate = mFormBuilder.getFormElement(BDATE_TAG).getValue();
        String gender = mFormBuilder.getFormElement(GENDER_TAG).getValue();
        String ethnicity = mFormBuilder.getFormElement(ETHNICITY_TAG).getValue();
        String language = mFormBuilder.getFormElement(LANGUAGE_TAG).getValue();
        String education = mFormBuilder.getFormElement(EDUCATION_TAG).getValue();
        String organization = mFormBuilder.getFormElement(ORG_TAG).getValue();

        Log.d(TAG, "tag should be same:"+ getString(R.string.firstName) + ", text data:" + firstName);

//        Util.saveDataToSharedPref(getString(R.string.firstName), firstName);
//        Util.saveDataToSharedPref(getString(R.string.lastName), lastName);
//        Util.saveDataToSharedPref(getString(R.string.deviceModel), deviceModel);
//        Util.saveDataToSharedPref(getString(R.string.birthDate), birthDate);
//        Util.saveDataToSharedPref(getString(R.string.gender), gender);
//        Util.saveDataToSharedPref(getString(R.string.ethnicity), ethnicity);
//        Util.saveDataToSharedPref(getString(R.string.language), language);
//        Util.saveDataToSharedPref(getString(R.string.education), education);
//        Util.saveDataToSharedPref(getString(R.string.organization), organization);


        Map<String, String> profile = new HashMap<>();
        profile.put(getString(R.string.firstName), firstName);
        profile.put(getString(R.string.lastName), lastName);
        profile.put(getString(R.string.deviceModel), deviceModel);
        profile.put(getString(R.string.birthDate), birthDate);
        profile.put(getString(R.string.gender), gender);
        profile.put(getString(R.string.ethnicity), ethnicity);
        profile.put(getString(R.string.language), language);
        profile.put(getString(R.string.education), education);
        profile.put(getString(R.string.organization), organization);

        for(Map.Entry<String, String> entry:profile.entrySet()){
            Util.saveDataToSharedPref(entry.getKey(), entry.getValue());
        }
        Toast.makeText(getActivity(), "Information Updated Successfully", Toast.LENGTH_SHORT).show();
        // make cimon enrollment call
        Call<CimonResponse> call = Koios.getService().uploadProfile(email, uuid, profile);
        try {
            call.enqueue(new Callback<CimonResponse>() {
                @Override
                public void onResponse(Call<CimonResponse> call, Response<CimonResponse> response) {
                    Log.d("Response from server", String.valueOf(response.body().getCode()) + ", " + response.body().getMessage());
                    if (response.body().getCode() == 0) {
                        // enrollment successful
                        Log.d(TAG, "profile update Succeeded");
                    } else {
                        Log.d(TAG, "profile update failed");
                    }
                }

                @Override
                public void onFailure(Call<CimonResponse> call, Throwable t) {
                }
            });
        } catch (Exception e) {
        }


    }

}