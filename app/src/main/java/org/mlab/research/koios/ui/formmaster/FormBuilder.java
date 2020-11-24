package org.mlab.research.koios.ui.formmaster;

import android.content.Context;
import android.text.TextUtils;

import org.mlab.research.koios.ui.formmaster.adapter.FormAdapter;
import org.mlab.research.koios.ui.formmaster.listener.OnFormElementValueChangedListener;
import org.mlab.research.koios.ui.formmaster.model.BaseFormElement;
import org.mlab.research.koios.ui.formmaster.viewholder.RecordingRequestListener;

import java.util.List;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/** Wrapper class around the adapter to assist in building form
 * Created by Adib on 16-Apr-17.
 */

public class FormBuilder {

    private FormAdapter mFormAdapter;

    /**
     * constructor without listener callback for changed values
     * @param context
     * @param recyclerView
     */
    public FormBuilder(Context context, RecyclerView recyclerView) {
        initializeFormBuildHelper(context, recyclerView, null, null);
    }

    /**
     * constructor with listener callback for changed values
     * @param context
     * @param recyclerView
     */
    public FormBuilder(Context context, RecyclerView recyclerView, OnFormElementValueChangedListener listener) {
        initializeFormBuildHelper(context, recyclerView, listener, null);
    }

    public FormBuilder(Context context, RecyclerView recyclerView, RecordingRequestListener recordinglistener) {
        initializeFormBuildHelper(context, recyclerView, null, recordinglistener);
    }


    /**
     * private method for initializing form build helper
     * @param context
     * @param recyclerView
     * @param listener
     */
    private void initializeFormBuildHelper(Context context, RecyclerView recyclerView, OnFormElementValueChangedListener listener, RecordingRequestListener recordingRequestListener) {

        // initialize form adapter
        this.mFormAdapter = new FormAdapter(context, listener, recordingRequestListener);

        // set up the recyclerview with adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setStackFromEnd(false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mFormAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    /**
     * add list of form elements to be shown
     * @param baseFormElements
     */
    public void addFormElements(List<BaseFormElement> baseFormElements) {
        this.mFormAdapter.addElements(baseFormElements);
    }

    /**
     * get value of specific form element
     * @param tag
     * @return
     */
    public BaseFormElement getFormElement(int tag) {
        return this.mFormAdapter.getValueAtTag(tag);
    }

    /**
     *
     * return true if the form is valid
     *
     * @return
     */
    public boolean isValidForm() {
        for (int i = 0; i < this.mFormAdapter.getItemCount(); i++) {
            BaseFormElement baseFormElement = this.mFormAdapter.getValueAtIndex(i);
            if (baseFormElement.isRequired() && TextUtils.isEmpty(baseFormElement.getValue())) {
                return false;
            }
        }
        return true;
    }

}