package org.mlab.research.koios;

public class StudySurveyConfig {

// "id": 15,
//         "studyId": 37,
//         "name": "Initial Data Collection Questionnaire",
//         "description": "",
//         "createdBy": "",
//         "creationTime": "",
//         "creationTimeZone": "",
//         "modificationTime": "1600994203",
//         "modificationTimeZone": "",
//         "publishTime": "1600994203",
//         "publishTimeZone": "240",
//         "publishedVersion": 3,
//         "state": 2,
//         "responseCount": 0,
//         "startTime": "1598241600000",
//         "startTimeZone": "240",
//         "endTime": "1629777600000",
//         "endTimeZone": "240",
//         "schedule": "once",
//         "lifecycle": 1



    private int id;
    private int studyId;
    private String name;
    private String description;
    private String modificationTime;
    private String modificationTimeZone;
    private String publishTime;
    private String publishTimeZone;
    private int publishedVersion;
    private int state;
    private String startTime;
    private String startTimeZone;
    private String endTime;
    private String endTimeZone;
    private String schedule;
    private int lifecycle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudyId() {
        return studyId;
    }

    public void setStudyId(int studyId) {
        this.studyId = studyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(String modificationTime) {
        this.modificationTime = modificationTime;
    }

    public String getModificationTimeZone() {
        return modificationTimeZone;
    }

    public void setModificationTimeZone(String modificationTimeZone) {
        this.modificationTimeZone = modificationTimeZone;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getPublishTimeZone() {
        return publishTimeZone;
    }

    public void setPublishTimeZone(String publishTimeZone) {
        this.publishTimeZone = publishTimeZone;
    }

    public int getPublishedVersion() {
        return publishedVersion;
    }

    public void setPublishedVersion(int publishedVersion) {
        this.publishedVersion = publishedVersion;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTimeZone() {
        return startTimeZone;
    }

    public void setStartTimeZone(String startTimeZone) {
        this.startTimeZone = startTimeZone;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTimeZone() {
        return endTimeZone;
    }

    public void setEndTimeZone(String endTimeZone) {
        this.endTimeZone = endTimeZone;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public int getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(int lifecycle) {
        this.lifecycle = lifecycle;
    }

    @Override
    public String toString() {
        return "id" + id + ",studyId:" + studyId + ", name:" + name + ", description:" + description
                + ", modificationTime:"+ modificationTime
                + ", modificationTimeZone:"+ modificationTimeZone + ", publishTime:"+ publishTime +", publishTimeZone:"+ publishTimeZone + ", publishedVersion:" + publishedVersion
                + ", state:"+ state + ", startTime:"+ startTime + ", startTimeZone:"+ startTimeZone
                + ", endTime:"+ endTime + ", endTimeZone:"+ endTimeZone + ", schedule:"+ schedule + ", lifecyclee:" + lifecycle;
    }
}
