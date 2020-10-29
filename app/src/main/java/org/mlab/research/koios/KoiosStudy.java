package org.mlab.research.koios;

/*
{"id":32,"name":"RMPilot2","description":"Test for SRAlab","createdBy":"","creationTime":"","creationTimeZone":"",
        "state":1,"modificationTime":"1548709286","modificationTimeZone":"","isPublic":0,"instruction":"test public","iconUrl":""}
*/

public class KoiosStudy {
    private int id;
    private String name;
    private String description;
    private String createdBy;
    private String creationTime;
    private String creationTimeZone;
    private int state;
    private String modificationTime;
    private String modificationTimeZone;
    private int isPublic;
    private String instruction;
    private String iconUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @Override
    public String toString() {
        return "study id:" + id + ", name:" + name + ", description:" + description + ", created by:" + createdBy + ", creation time:" + creationTime
        + ", zone:" + creationTimeZone + ", state:" + state + ", modification time:" + modificationTime + ", zone:" + modificationTimeZone
        + ", isPublic:" + isPublic + ", instruction:" + instruction + ", iconUrl:" + iconUrl;
    }
}
