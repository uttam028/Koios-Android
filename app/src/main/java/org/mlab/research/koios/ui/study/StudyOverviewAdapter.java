package org.mlab.research.koios.ui.study;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mlab.research.koios.KoiosStudy;
import org.mlab.research.koios.R;
import org.mlab.research.koios.Util;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudyOverviewAdapter extends RecyclerView.Adapter<StudyOverviewHolder> {

    List<KoiosStudy> list = new ArrayList<>();
    ItemClickListener clickListener;

    public StudyOverviewAdapter(ItemClickListener clickListener, List<KoiosStudy> list) {
        this.clickListener = clickListener;
        this.list = list;
    }

    @NonNull
    @Override
    public StudyOverviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.study_overview_layout, parent, false);
        return new StudyOverviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudyOverviewHolder holder, int position) {
        KoiosStudy study = list.get(position);
        holder.studyName.setText(study.getName());
        holder.organizationName.setText(study.getOrganization());
        String info = "";
//        String joinInfo = Util.getPreferenceData()
        if (study.getStudyType()==1){
            info = "Invited Study";
        }else {
            info = "Public Study";
        }
        holder.studyInfo.setText(info);
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
