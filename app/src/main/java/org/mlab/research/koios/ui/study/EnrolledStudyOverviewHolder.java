package org.mlab.research.koios.ui.study;

import android.view.View;
import android.widget.TextView;

import org.mlab.research.koios.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EnrolledStudyOverviewHolder extends RecyclerView.ViewHolder{
    TextView univName;
    TextView studyName;


    public EnrolledStudyOverviewHolder(@NonNull View itemView) {
        super(itemView);
        univName = (TextView) itemView.findViewById(R.id.univName);
        studyName = (TextView) itemView.findViewById(R.id.studyName);
    }

}
