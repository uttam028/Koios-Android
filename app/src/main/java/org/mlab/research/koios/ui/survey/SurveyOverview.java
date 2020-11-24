package org.mlab.research.koios.ui.survey;

public class SurveyOverview {
    private int studyId;
    private int surveyId;
    private String studyName;
    private String surveyName;
    private String schedule;
    private String lastParticipation;

    public SurveyOverview(int studyId, int surveyId, String studyName, String surveyName, String schedule, String lastParticipation){
        this.studyId = studyId;
        this.surveyId = surveyId;
        this.studyName = studyName;
        this.surveyName = surveyName;
        this.schedule = schedule;
        this.lastParticipation = lastParticipation;
    }

    public int getStudyId() {
        return studyId;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public String getStudyName() {
        return studyName;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public String getSchedule() {
        return schedule;
    }

    public String getLastParticipation() {
        return lastParticipation;
    }

}
