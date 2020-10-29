package org.mlab.research.koios.ui.survey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mlab.research.koios.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SurveyOverviewAdapter extends RecyclerView.Adapter<SurveyOverviewHolder> {

    List<SurveyOverview> list = new ArrayList<>();
    ItemClickListener clickListener;

    public SurveyOverviewAdapter(ItemClickListener clickListener, List<SurveyOverview> list) {
        this.clickListener = clickListener;
        this.list = list;
    }

    @NonNull
    @Override
    public SurveyOverviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.survey_overview_layout,parent,false);
        return new SurveyOverviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SurveyOverviewHolder holder, int position) {
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
