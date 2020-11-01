package org.mlab.research.koios.ui.study;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.mlab.research.koios.R;
import org.mlab.research.koios.ui.study.ItemClickListener;
import org.mlab.research.koios.ui.study.StudyOverview;

import java.util.ArrayList;
import java.util.List;

public class StudyOverviewAdapter extends RecyclerView.Adapter<StudyOverviewHolder> {

    List<StudyOverview> list = new ArrayList<>();
    ItemClickListener clickListener;

    public StudyOverviewAdapter(ItemClickListener clickListener, List<StudyOverview> list) {
        this.clickListener = clickListener;
        this.list = list;
    }

    @NonNull
    @Override
    public StudyOverviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.study_overview_layout,parent,false);
        return new StudyOverviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudyOverviewHolder holder, int position) {
        holder.studyName.setText(list.get(position).getStudyName());
        holder.surveyName.setText(list.get(position).getSurveyName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
