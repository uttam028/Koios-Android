package org.mlab.research.koios.ui.formmaster.viewholder;

import android.content.Context;
import android.view.View;

import org.mlab.research.koios.R;
import org.mlab.research.koios.ui.formmaster.model.BaseFormElement;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * ViewHolder for Header
 * Created by Riddhi - Rudra on 30-Jul-17.
 */

public class FormElementHeader extends BaseViewHolder {

    public AppCompatTextView mTextViewTitle;

    public FormElementHeader(View v) {
        super(v);
        mTextViewTitle = (AppCompatTextView) v.findViewById(R.id.formElementTitle);
    }

    @Override
    public void bind(int position, BaseFormElement formElement, final Context context) {
        mTextViewTitle.setText(formElement.getTitle());
    }

}
