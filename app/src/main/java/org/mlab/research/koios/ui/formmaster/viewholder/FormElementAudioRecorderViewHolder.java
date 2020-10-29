package org.mlab.research.koios.ui.formmaster.viewholder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import org.mlab.research.koios.R;
import org.mlab.research.koios.ui.formmaster.model.BaseFormElement;
import org.mlab.research.koios.ui.survey.SurveyDetailsActivity;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

public class FormElementAudioRecorderViewHolder extends BaseViewHolder {
    public final static String TAG = FormElementAudioRecorderViewHolder.class.getSimpleName() + "_debug";

    public AppCompatTextView mTextViewTitle;
    public AppCompatEditText mEditTextValue;
//    public FormItemEditTextListener mFormCustomEditTextListener;

//    public FormElementButtonViewHolder(View v, FormItemEditTextListener listener) {
//        super(v);
//        mTextViewTitle = (AppCompatTextView) v.findViewById(R.id.formElementTitle);
//        mEditTextValue = (AppCompatEditText) v.findViewById(R.id.formElementValue);
//        mFormCustomEditTextListener = listener;
//        mEditTextValue.addTextChangedListener(mFormCustomEditTextListener);
//        mEditTextValue.setMaxLines(1);
//    }
    public FormElementAudioRecorderViewHolder(View v) {
        super(v);
        mTextViewTitle = (AppCompatTextView) v.findViewById(R.id.formElementTitle);
        mEditTextValue = (AppCompatEditText) v.findViewById(R.id.formElementValue);
    }

//    @Override
//    public FormItemEditTextListener getListener() {
//        return mFormCustomEditTextListener;
//    }

    @Override
    public void bind(int position, BaseFormElement formElement, final Context context) {
        mTextViewTitle.setText(formElement.getTitle());
        mEditTextValue.setText("");
        mEditTextValue.setVisibility(View.INVISIBLE);

        mTextViewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), FormAudioRecorderActivity.class);
                itemView.getContext().startActivity(intent);
            }
        });
    }
}
