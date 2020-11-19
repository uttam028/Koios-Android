package org.mlab.research.koios.ui.survey;

public class SurveyResponse {
    private long studyId;
    private long surveyId;
    private int taskId;
    private int version;
    private String submissionTime;
    private String submissionTimeZone;
    private String answerType;
    private String answer;
    private String comment;
    private String objectUrl;

    public long getStudyId() {
        return studyId;
    }

    public void setStudyId(long studyId) {
        this.studyId = studyId;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(String submissionTime) {
        this.submissionTime = submissionTime;
    }

    public String getSubmissionTimeZone() {
        return submissionTimeZone;
    }

    public void setSubmissionTimeZone(String submissionTimeZone) {
        this.submissionTimeZone = submissionTimeZone;
    }

    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getObjectUrl() {
        return objectUrl;
    }

    public void setObjectUrl(String objectUrl) {
        this.objectUrl = objectUrl;
    }

    @Override
    public String toString() {
        return "SurveyResponse{" +
                "studyId=" + studyId +
                ", surveyId=" + surveyId +
                ", taskId=" + taskId +
                ", version=" + version +
                ", submissionTime='" + submissionTime + '\'' +
                ", submissionTimeZone='" + submissionTimeZone + '\'' +
                ", answerType='" + answerType + '\'' +
                ", answer='" + answer + '\'' +
                ", comment='" + comment + '\'' +
                ", objectUrl='" + objectUrl + '\'' +
                '}';
    }
}
