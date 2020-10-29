package org.mlab.research.koios.ui.formmaster.viewholder;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.mlab.research.koios.R;
import org.mlab.research.koios.ui.formmaster.listener.FormItemEditTextListener;
import org.mlab.research.koios.ui.formmaster.model.BaseFormElement;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by Riddhi - Rudra on 30-Jul-17.
 */

public class FormElementTextMultiLineViewHolder extends BaseViewHolder {

    public AppCompatTextView mTextViewTitle;
    public AppCompatEditText mEditTextValue;
    public FormItemEditTextListener mFormCustomEditTextListener;

    public FormElementTextMultiLineViewHolder(View v, FormItemEditTextListener listener) {
        super(v);
        mTextViewTitle = (AppCompatTextView) v.findViewById(R.id.formElementTitle);
        mEditTextValue = (AppCompatEditText) v.findViewById(R.id.formElementValue);
        mFormCustomEditTextListener = listener;
        mEditTextValue.addTextChangedListener(mFormCustomEditTextListener);
        mEditTextValue.setMaxLines(4);
        mEditTextValue.setSingleLine(false);
        mEditTextValue.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_MULTI_LINE);
    }

    @Override
    public FormItemEditTextListener getListener() {
        return mFormCustomEditTextListener;
    }

    @Override
    public void bind(int position, BaseFormElement formElement, final Context context) {
        mTextViewTitle.setText(formElement.getTitle());
        mEditTextValue.setText(formElement.getValue());
        mEditTextValue.setHint(formElement.getHint());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditTextValue.requestFocus();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mEditTextValue, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }

}
