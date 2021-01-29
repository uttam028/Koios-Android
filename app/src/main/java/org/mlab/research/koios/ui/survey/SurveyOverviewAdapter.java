package org.mlab.research.koios.ui.survey;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mlab.research.koios.R;
import org.mlab.research.koios.Util;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
        SurveyOverview survey = list.get(position);
        holder.tvStudyName.setText(survey.getStudyName());
        holder.tvSurveyName.setText(survey.getSurveyName());
        Context context = holder.itemView.getContext();

        //logic to convert response time
        String lastResponseText = "";
        if (Util.isValidDate(survey.getLastParticipation())){
            int daysDifference = Util.getDayDifference(survey.getLastParticipation());
            if (daysDifference==0){
                lastResponseText = "Completed Today";
            }else if(daysDifference ==1){
                lastResponseText = "Completed Yesterday";
            }else if(daysDifference>1){
                lastResponseText = "Completed " + survey.getLastParticipation();
            }

            if (survey.getSchedule().startsWith("always")){
                int color = Util.handleSpecialCase(survey.getSurveyId());
                holder.tvLastParticipation.setTextColor(ContextCompat.getColor(context, color));
            }else if (survey.getSchedule().startsWith("week")){
                if (daysDifference>=7){
                    holder.tvLastParticipation.setTextColor(ContextCompat.getColor(context, R.color.colorError));
                }
            }else if (survey.getSchedule().startsWith("month")){
                if (daysDifference >= 31){
                    holder.tvLastParticipation.setTextColor(ContextCompat.getColor(context, R.color.colorError));
                }
            }
        }else{
            lastResponseText = "Not Completed Yet";
            //the color scheme from first responder study
            holder.tvLastParticipation.setTextColor(ContextCompat.getColor(context, R.color.colorNdBlue));

            if (!survey.getSchedule().startsWith("always")){
                holder.tvLastParticipation.setTextColor(ContextCompat.getColor(context, R.color.colorError));
            }
        }

        holder.tvLastParticipation.setText(lastResponseText);

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
