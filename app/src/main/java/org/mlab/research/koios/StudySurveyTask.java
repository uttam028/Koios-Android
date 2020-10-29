package org.mlab.research.koios;

public class StudySurveyTask {
    /*{
        "studyId": 37,
            "surveyId": 15,
            "version": 3,
            "taskId": 1,
            "taskText": "Gender:",
            "type": "selection",
            "possibleInput": "Male|Female|Other (please specify below)",
            "orderId": 1,
            "isActive": 1,
            "isRequired": 1,
            "hasComment": 1,
            "hasUrl": 0,
            "parentTaskId": 0,
            "hasChild": 0,
            "childTriggeringInput": null,
            "defaultInput": null
    }*/
    private int studyId;
    private int surveyId;
    private int version;
    private int taskId;
    private String taskText;
    private String type;
    private String possibleInput;
    private int orderId;
    private int isActive;
    private int isRequired;
    private int hasComment;
    private int hasUrl;
    private int parentTaskId;
    private int hasChild;
    private String childTriggeringInput;
    private String defaultInput;

    public int getStudyId() {
        return studyId;
    }

    public void setStudyId(int studyId) {
        this.studyId = studyId;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPossibleInput() {
        return possibleInput;
    }

    public void setPossibleInput(String possibleInput) {
        this.possibleInput = possibleInput;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(int isRequired) {
        this.isRequired = isRequired;
    }

    public int getHasComment() {
        return hasComment;
    }

    public void setHasComment(int hasComment) {
        this.hasComment = hasComment;
    }

    public int getHasUrl() {
        return hasUrl;
    }

    public void setHasUrl(int hasUrl) {
        this.hasUrl = hasUrl;
    }

    public int getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(int parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    public int getHasChild() {
        return hasChild;
    }

    public void setHasChild(int hasChild) {
        this.hasChild = hasChild;
    }

    public String getChildTriggeringInput() {
        return childTriggeringInput;
    }

    public void setChildTriggeringInput(String childTriggeringInput) {
        this.childTriggeringInput = childTriggeringInput;
    }

    public String getDefaultInput() {
        return defaultInput;
    }

    public void setDefaultInput(String defaultInput) {
        this.defaultInput = defaultInput;
    }

//    @Override
//    public String toString() {
//        return "study Id:" + studyId + ", config Id:" + sensorConfigId + ", version:" + version + ", action code:" + sensorActionCode + ", type:" + type
//                + ", isEnabled:" + isEnabled + ", frequency:" + frequency + ", timebound:" + timeBound + ", battery bound:" + batteryBound + ", param1:"
//                + param1 + ", param2:" + param2 + ", param3:" + param3;
//    }
}
