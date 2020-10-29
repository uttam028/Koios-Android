package org.mlab.research.koios;

public class StudySensorConfig {

    /*{"id":4,"studyId":32,"name":"Sensor_Config_Mon Jan 28 14:45:42 GMT-600 2019","description":"",
            "createdBy":"","creationTime":"","creationTimeZone":"","modificationTime":"1548709286",
            "modificationTimeZone":"","publishTime":"1548709286","publishTimeZone":"360","publishedVersion":3,
            "state":2,"responseCount":0,"startTime":"","startTimeZone":"","endTime":"","endTimeZone":"","schedule":""}*/

    private int id;
    private int studyId;
    private String name;
    private String description;
    private String createdBy;
    private String creationTime;
    private String creationTimeZone;
    private String modificationTime;
    private String modificationTimeZone;
    private String publishTime;
    private String publishTimeZone;
    private int publishedVersion;
    private int state;
    private int responseCount;
    private String startTime;
    private String startTimeZone;
    private String endTime;
    private String endTimeZone;
    private String schedule;


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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getCreationTimeZone() {
        return creationTimeZone;
    }

    public void setCreationTimeZone(String creationTimeZone) {
        this.creationTimeZone = creationTimeZone;
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

    public int getResponseCount() {
        return responseCount;
    }

    public void setResponseCount(int responseCount) {
        this.responseCount = responseCount;
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

    @Override
    public String toString() {
        return "id" + id + ",studyId:" + studyId + ", name:" + name + ", description:" + description
                + ", createdBy:" + createdBy + ", creationTime:"+ creationTime + ", creationTimeZone:"+ creationTimeZone +", modificationTime:"+ modificationTime
                + ", modificationTimeZone:"+ modificationTimeZone + ", publishTime:"+ publishTime +", publishTimeZone:"+ publishTimeZone + ", publishedVersion:" + publishedVersion
                + ", state:"+ state +", responseCount:"+ responseCount +", startTime:"+ startTime + ", startTimeZone:"+ startTimeZone
                + ", endTime:"+ endTime + ", endTimeZone:"+ endTimeZone + ", schedule:"+ schedule;
    }
}
