package org.mlab.research.koios.ui.survey;

import android.view.View;
import android.widget.TextView;

import org.mlab.research.koios.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SurveyOverviewHolder extends RecyclerView.ViewHolder{
    TextView tvSurveyName;
    TextView tvStudyName;
    TextView tvLastParticipation;

    public SurveyOverviewHolder(@NonNull View itemView) {
        super(itemView);
        tvSurveyName = (TextView) itemView.findViewById(R.id.surveyName);
        tvStudyName = (TextView) itemView.findViewById(R.id.openStudyName);
        tvLastParticipation = (TextView) itemView.findViewById(R.id.lastParticipation);
    }

}
