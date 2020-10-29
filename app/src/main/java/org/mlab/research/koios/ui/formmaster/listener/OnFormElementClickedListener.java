package org.mlab.research.koios.ui.formmaster.listener;


import org.mlab.research.koios.ui.formmaster.model.BaseFormElement;

/**
 * Callback to activity when any data in form adapter is changed
 */

public interface OnFormElementClickedListener {

    void onClick(BaseFormElement baseFormElement);

}