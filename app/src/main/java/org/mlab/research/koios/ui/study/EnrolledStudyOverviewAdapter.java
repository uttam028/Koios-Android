package org.mlab.research.koios.ui.study;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.mlab.research.koios.R;

import java.util.ArrayList;
import java.util.List;

public class EnrolledStudyOverviewAdapter extends RecyclerView.Adapter<StudyOverviewHolder> {

    List<EnrolledStudyOverview> list = new ArrayList<>();
    ItemClickListener clickListener;

    public EnrolledStudyOverviewAdapter(ItemClickListener clickListener, List<EnrolledStudyOverview> list) {
        this.clickListener = clickListener;
        this.list = list;
    }

    @NonNull
    @Override
    public StudyOverviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.enrolled_study_overview_layout,parent,false);
        return new StudyOverviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudyOverviewHolder holder, int position) {
        holder.studyName.setText(list.get(position).getStudyName());
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
