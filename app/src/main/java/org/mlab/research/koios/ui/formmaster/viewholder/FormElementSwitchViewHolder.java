package org.mlab.research.koios.ui.formmaster.viewholder;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;

import org.mlab.research.koios.R;
import org.mlab.research.koios.ui.formmaster.listener.ReloadListener;
import org.mlab.research.koios.ui.formmaster.model.BaseFormElement;
import org.mlab.research.koios.ui.formmaster.model.FormElementSwitch;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;

/**
 * Created by Riddhi - Rudra on 30-Jul-17.
 */

public class FormElementSwitchViewHolder extends BaseViewHolder {

    public AppCompatTextView mTextViewTitle, mTextViewPositive, mTextViewNegative;
    public SwitchCompat mSwitch;
    private ReloadListener mReloadListener;
    private BaseFormElement mFormElement;
    private FormElementSwitch mFormElementSwitch;
    private int mPosition;

    public FormElementSwitchViewHolder(View v, Context context, ReloadListener reloadListener) {
        super(v);
        mTextViewTitle = (AppCompatTextView) v.findViewById(R.id.formElementTitle);
        mTextViewPositive = (AppCompatTextView) v.findViewById(R.id.formElementPositiveText);
        mTextViewNegative = (AppCompatTextView) v.findViewById(R.id.formElementNegativeText);
        mSwitch = (SwitchCompat) v.findViewById(R.id.formElementSwitch);
        mReloadListener = reloadListener;
    }

    @Override
    public void bind(final int position, BaseFormElement formElement, final Context context) {
        mFormElement = formElement;
        mPosition = position;
        mFormElementSwitch = (FormElementSwitch) mFormElement;

        mTextViewTitle.setText(mFormElementSwitch.getTitle());
        mTextViewPositive.setText(mFormElementSwitch.getPositiveText());
        mTextViewNegative.setHint(mFormElementSwitch.getNegativeText());
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mReloadListener.updateValue(position, b ? mFormElementSwitch.getPositiveText() : mFormElementSwitch.getNegativeText());
            }
        });
    }

}
