package org.mlab.research.koios.ui.formmaster.viewholder;

import android.content.Context;
import android.view.View;

import org.mlab.research.koios.ui.formmaster.listener.FormItemEditTextListener;
import org.mlab.research.koios.ui.formmaster.model.BaseFormElement;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Base ViewHolder for all other viewholders
 * Created by Riddhi - Rudra on 30-Jul-17.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder implements BaseViewHolderInterface {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public FormItemEditTextListener getListener() {
        return null;
    }

    @Override
    public void bind(int position, BaseFormElement formElement, Context context) {

    }

}
