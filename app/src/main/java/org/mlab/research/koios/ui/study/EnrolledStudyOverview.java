package org.mlab.research.koios.ui.study;

public class EnrolledStudyOverview {
    private int studyId;
    private String studyName;
    private String univName;
    private String description;
    private String instructions;
    private String dateJoined;

    public EnrolledStudyOverview(int studyId, String studyName, String univName, String description, String instructions, String dateJoined){
        this.studyId = studyId;
        this.studyName = studyName;
        this.univName = univName;
        this.description = description;
        this.instructions = instructions;
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

    public String getStudyDescription() {
        return description;
    }

    public String getStudyInstructions() {
        return instructions;
    }

    public String getStudyDateJoined() {
        return dateJoined;
    }

}