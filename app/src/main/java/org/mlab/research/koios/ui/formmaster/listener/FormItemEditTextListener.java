package org.mlab.research.koios.ui.formmaster.listener;

import android.text.Editable;
import android.text.TextWatcher;

import org.mlab.research.koios.ui.formmaster.adapter.FormAdapter;
import org.mlab.research.koios.ui.formmaster.model.BaseFormElement;

/**
 * Edit text listener for form element edit texts
 * Created by Riddhi - Rudra on 30-Jul-17.
 */

public class FormItemEditTextListener implements TextWatcher {

    private int position;
    private FormAdapter formAdapter;

    public FormItemEditTextListener(FormAdapter formAdapter) {
        this.formAdapter = formAdapter;
    }

    public void updatePosition(int position) {
        this.position = position;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        // get current form element, existing value and new value
        BaseFormElement baseFormElement = formAdapter.getDataset().get(position);
        String currentValue = baseFormElement.getValue();
        String newValue = charSequence.toString();

        // trigger event only if the value is changed
        if (!currentValue.equals(newValue)) {
            baseFormElement.setValue(newValue);
            if (formAdapter.getValueChangeListener() != null)
                formAdapter.getValueChangeListener().onValueChanged(baseFormElement);
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

}
