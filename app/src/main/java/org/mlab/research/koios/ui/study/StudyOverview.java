package org.mlab.research.koios.ui.study;

public class StudyOverview {
    private int studyId;
    private int surveyId;
    private String studyName;
    private String surveyName;
    private String lastParticipation;
    private String nextDue;

    public StudyOverview(int studyId, int surveyId, String studyName, String surveyName, String lastParticipation, String nextDue){
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