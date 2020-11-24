package org.mlab.research.koios;

/*
{"id":32,"name":"RMPilot2","description":"Test for SRAlab","createdBy":"","creationTime":"","creationTimeZone":"",
        "state":1,"modificationTime":"1548709286","modificationTimeZone":"","isPublic":0,"instruction":"test public","iconUrl":""}
*/

import android.os.Parcel;
import android.os.Parcelable;

public class KoiosStudy implements Parcelable {
    private int id;
    private String name;
    private String organization;
    private String description;
    private String createdBy;
    private String creationTime;
    private String creationTimeZone;
    private int state;
    private String modificationTime;
    private String modificationTimeZone;
    private int studyType;
    private String instruction;
    private String iconUrl;

    public KoiosStudy() {
    }

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

    public int getStudyType() {
        return studyType;
    }

    public void setStudyType(int studyType) {
        this.studyType = studyType;
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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "study id:" + id + ", name:" + name + ", description:" + description + ", created by:" + createdBy + ", creation time:" + creationTime
                + ", zone:" + creationTimeZone + ", state:" + state + ", modification time:" + modificationTime + ", zone:" + modificationTimeZone
                + ", studyType:" + studyType + ", instruction:" + instruction + ", iconUrl:" + iconUrl;
    }

    protected KoiosStudy(Parcel in) {
        id = in.readInt();
        name = in.readString();
        organization = in.readString();
        description = in.readString();
        createdBy = in.readString();
        creationTime = in.readString();
        creationTimeZone = in.readString();
        state = in.readInt();
        modificationTime = in.readString();
        modificationTimeZone = in.readString();
        studyType = in.readInt();
        instruction = in.readString();
        iconUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(organization);
        dest.writeString(description);
        dest.writeString(createdBy);
        dest.writeString(creationTime);
        dest.writeString(creationTimeZone);
        dest.writeInt(state);
        dest.writeString(modificationTime);
        dest.writeString(modificationTimeZone);
        dest.writeInt(studyType);
        dest.writeString(instruction);
        dest.writeString(iconUrl);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<KoiosStudy> CREATOR = new Parcelable.Creator<KoiosStudy>() {
        @Override
        public KoiosStudy createFromParcel(Parcel in) {
            return new KoiosStudy(in);
        }

        @Override
        public KoiosStudy[] newArray(int size) {
            return new KoiosStudy[size];
        }
    };
}