package org.mlab.research.koios.ui.survey;

import android.view.View;
import android.widget.TextView;

import org.mlab.research.koios.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SurveyOverviewHolder extends RecyclerView.ViewHolder{
    TextView surveyName;
    TextView studyName;


    public SurveyOverviewHolder(@NonNull View itemView) {
        super(itemView);
        surveyName = (TextView) itemView.findViewById(R.id.surveyName);
        studyName = (TextView) itemView.findViewById(R.id.studyName);
    }

}
