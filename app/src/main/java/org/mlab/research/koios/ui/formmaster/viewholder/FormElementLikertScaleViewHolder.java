package org.mlab.research.koios.ui.formmaster.viewholder;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.mlab.research.koios.R;
import org.mlab.research.koios.ui.formmaster.listener.ReloadListener;
import org.mlab.research.koios.ui.formmaster.model.BaseFormElement;
import org.mlab.research.koios.ui.formmaster.model.FormElementLikertScale;
import org.w3c.dom.Text;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by Riddhi - Rudra on 30-Jul-17.
 */

public class FormElementLikertScaleViewHolder extends BaseViewHolder {

    private AppCompatTextView mTextViewTitle;
    private AppCompatEditText mEditTextValue;
    private ReloadListener mReloadListener;
    private BaseFormElement mFormElement;
    private FormElementLikertScale mFormElementLikertScale;
    private int mPosition;

    public FormElementLikertScaleViewHolder(View v, Context context, ReloadListener reloadListener) {
        super(v);
        mTextViewTitle = (AppCompatTextView) v.findViewById(R.id.formElementTitle);
        mEditTextValue = (AppCompatEditText) v.findViewById(R.id.formElementValue);
        mReloadListener = reloadListener;
    }

    @Override
    public void bind(final int position, BaseFormElement formElement, final Context context) {
        mFormElement = formElement;
        mPosition = position;
        mFormElementLikertScale = (FormElementLikertScale) mFormElement;

        mTextViewTitle.setText(formElement.getTitle());
        mEditTextValue.setText(formElement.getValue());
        mEditTextValue.setHint(formElement.getHint());
        mEditTextValue.setFocusableInTouchMode(false);








        Dialog dialog = new Dialog(context);

        switch (mFormElementLikertScale.getLikertType()){
            case 4:
                dialog.setContentView(R.layout.dialog_likert4);
                break;
            case 5:
                dialog.setContentView(R.layout.dialog_likert5);
                break;
            case 7:
                dialog.setContentView(R.layout.dialog_likert7);
                break;
            default:
                dialog.setContentView(R.layout.dialog_likert5);
                break;
        }
        dialog.setTitle("Pick Answer");
        TextView t1 = (TextView) dialog.findViewById(R.id.t1);
        Button b1 = (Button) dialog.findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String val = t1.getText().subSequence(4, t1.getText().length()).toString();
                mEditTextValue.setText(val);
                mFormElementLikertScale.setValue(val);
                dialog.dismiss();
            }
        });
        TextView t2 = (TextView) dialog.findViewById(R.id.t2);
        Button b2 = (Button) dialog.findViewById(R.id.b2);
        b2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String val = t2.getText().subSequence(4, t2.getText().length()).toString();
                mEditTextValue.setText(val);
                mFormElementLikertScale.setValue(val);
                dialog.dismiss();
            }
        });
        TextView t3 = (TextView) dialog.findViewById(R.id.t3);
        Button b3 = (Button) dialog.findViewById(R.id.b3);
        b3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String val = t3.getText().subSequence(4, t3.getText().length()).toString();
                mEditTextValue.setText(val);
                mFormElementLikertScale.setValue(val);
                dialog.dismiss();
            }
        });
        TextView t4 = (TextView) dialog.findViewById(R.id.t4);
        Button b4 = (Button) dialog.findViewById(R.id.b4);
        b4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String val = t4.getText().subSequence(4, t4.getText().length()).toString();
                mEditTextValue.setText(val);
                mFormElementLikertScale.setValue(val);
                dialog.dismiss();
            }
        });

        if(mFormElementLikertScale.getUseCustomOptions()){
            b1.setText(mFormElementLikertScale.getOptions().get(0));
            b2.setText(mFormElementLikertScale.getOptions().get(1));
            b3.setText(mFormElementLikertScale.getOptions().get(2));
            b4.setText(mFormElementLikertScale.getOptions().get(3));
            t1.setText("1 - " + mFormElementLikertScale.getOptions().get(0));
            t2.setText("2 - " + mFormElementLikertScale.getOptions().get(1));
            t3.setText("3 - " + mFormElementLikertScale.getOptions().get(2));
            t4.setText("4 - " + mFormElementLikertScale.getOptions().get(3));
        }

        if (mFormElementLikertScale.getLikertType() != 4){
            TextView t5 = (TextView) dialog.findViewById(R.id.t5);
            Button b5 = (Button) dialog.findViewById(R.id.b5);
            b5.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    String val = t5.getText().subSequence(4, t5.getText().length()).toString();
                    mEditTextValue.setText(val);
                    mFormElementLikertScale.setValue(val);
                    dialog.dismiss();
                }
            });

            if(mFormElementLikertScale.getUseCustomOptions()){
                b5.setText(mFormElementLikertScale.getOptions().get(4));
                t5.setText("5 - " + mFormElementLikertScale.getOptions().get(4));
            }

            if (mFormElementLikertScale.getUseLegend()){
                t5.setVisibility(View.VISIBLE);
                b5.setText("5");
                b5.setTextSize(15);
            }

        }
        if (mFormElementLikertScale.getLikertType() != 4 && mFormElementLikertScale.getLikertType() != 5){
            TextView t6 = (TextView) dialog.findViewById(R.id.t6);
            Button b6 = (Button) dialog.findViewById(R.id.b6);
            b6.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    String val = t6.getText().subSequence(4, t6.getText().length()).toString();
                    mEditTextValue.setText(val);
                    mFormElementLikertScale.setValue(val);
                    dialog.dismiss();
                }
            });

            TextView t7 = (TextView) dialog.findViewById(R.id.t7);
            Button b7 = (Button) dialog.findViewById(R.id.b7);
            b7.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    String val = t7.getText().subSequence(4, t7.getText().length()).toString();
                    mEditTextValue.setText(val);
                    mFormElementLikertScale.setValue(val);
                    dialog.dismiss();
                }
            });

            if(mFormElementLikertScale.getUseCustomOptions()){
                b6.setText(mFormElementLikertScale.getOptions().get(5));
                b7.setText(mFormElementLikertScale.getOptions().get(6));
                t6.setText("6 - " + mFormElementLikertScale.getOptions().get(5));
                t7.setText("7 - " + mFormElementLikertScale.getOptions().get(6));
            }


            if (mFormElementLikertScale.getUseLegend()){
                t6.setVisibility(View.VISIBLE);
                b6.setText("6");
                b6.setTextSize(15);
                b7.setText("7");
                b7.setTextSize(15);
                t7.setVisibility(View.VISIBLE);
            }
        }


        if (mFormElementLikertScale.getUseLegend()){
            t1.setVisibility(View.VISIBLE);
            t2.setVisibility(View.VISIBLE);
            t3.setVisibility(View.VISIBLE);
            t4.setVisibility(View.VISIBLE);
            b1.setText("1");
            b1.setTextSize(15);
            b2.setText("2");
            b2.setTextSize(15);
            b3.setText("3");
            b3.setTextSize(15);
            b4.setText("4");
            b4.setTextSize(15);
        }


        /* ORIGINAL
        final AlertDialog dialog = new AlertDialog.Builder(context)
            .setTitle(mFormElementLikertScale.getPickerTitle())
            .setItems(options, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    mEditTextValue.setText(options[which]);
                    mFormElementLikertScale.setValue(options[which].toString());
                    mReloadListener.updateValue(position, options[which].toString());
                }
            })
            .create();
         */

        mEditTextValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        mTextViewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

}
