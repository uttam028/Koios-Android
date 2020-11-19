package org.mlab.research.koios.ui.survey;

public class SurveyOverview {
    private int studyId;
    private int surveyId;
    private String studyName;
    private String surveyName;
    private String lastParticipation;
    private String nextDue;

    public SurveyOverview(int studyId, int surveyId, String studyName, String surveyName, String lastParticipation, String nextDue){
        this.studyId = studyId;
        this.surveyId = surveyId;
        this.studyName = studyName;
        this.surveyName = surveyName;
        this.lastParticipation = lastParticipation;
        this.nextDue = nextDue;
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

    public String getLastParticipation() {
        return lastParticipation;
    }

    public String getNextDue() {
        return nextDue;
    }
}
