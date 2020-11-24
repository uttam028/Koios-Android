package org.mlab.research.koios.ui.study;

import android.view.View;
import android.widget.TextView;

import org.mlab.research.koios.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudyOverviewHolder extends RecyclerView.ViewHolder{
    TextView studyName;
    TextView organizationName;
    TextView studyInfo;

    public StudyOverviewHolder(@NonNull View itemView) {
        super(itemView);
        organizationName = (TextView) itemView.findViewById(R.id.orgName);
        studyName = (TextView) itemView.findViewById(R.id.openStudyName);
        studyInfo = (TextView) itemView.findViewById(R.id.studyInfo);
    }

}
