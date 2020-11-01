package org.mlab.research.koios.ui.study;

public class StudyOverview {
    private int studyId;
    private String studyName;
    private String univName;
    private String dateJoined;

    public StudyOverview(int studyId, String studyName, String univName, String dateJoined){
        this.studyId = studyId;
        this.studyName = studyName;
        this.univName = univName;
        this.dateJoined = dateJoined;
    }

    public int getStudyId() {
        return studyId;
    }

    public String getStudyName() {
        return studyName;
    }

    public String getUnivName() {
        return univName;
    }

    public String getDateJoined() {
        return dateJoined;
    }

}