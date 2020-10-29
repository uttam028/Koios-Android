package org.mlab.research.koios.ui.formmaster.viewholder;

import android.content.Context;

import org.mlab.research.koios.ui.formmaster.listener.FormItemEditTextListener;
import org.mlab.research.koios.ui.formmaster.model.BaseFormElement;

/**
 * Base ViewHolder method instance
 * Created by Riddhi - Rudra on 30-Jul-17.
 */

public interface BaseViewHolderInterface {
    FormItemEditTextListener getListener();
    void bind(int position, BaseFormElement formElement, Context context);
}
