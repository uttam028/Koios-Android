package org.mlab.research.koios.ui.formmaster.model;

/**
 * Created by Adib on 16-Apr-17.
 */

public class BaseFormElement {

    // different types for the form elements
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_EDITTEXT_TEXT_SINGLELINE = 1;
    public static final int TYPE_EDITTEXT_TEXT_MULTILINE = 2;
    public static final int TYPE_EDITTEXT_NUMBER = 3;
    public static final int TYPE_EDITTEXT_EMAIL = 4;
    public static final int TYPE_EDITTEXT_PHONE = 5;
    public static final int TYPE_EDITTEXT_PASSWORD = 6;
    public static final int TYPE_PICKER_DATE = 7;
    public static final int TYPE_PICKER_TIME = 8;
    public static final int TYPE_PICKER_SINGLE = 9;
    public static final int TYPE_PICKER_MULTI = 10;
    public static final int TYPE_SWITCH = 11;
    public static final int TYPE_AUDIO_RECORDER = 12;
    public static final int TYPE_COMMENT_MULTILINE=13;

    // private variables
    private int mTag; // unique tag to identify the object
    private int mType; // type for the form element
    private String mTitle; // title to be shown on left
    private String mValue; // value to be shown on right
    private String mHint; // value to be shown if mValue is null
    private boolean mRequired; // value to set is the field is required
    private boolean enabled=true;

    // setters
    public BaseFormElement setTag(int mTag) {
        this.mTag = mTag;
        return this;
    }

    public BaseFormElement setType(int mType) {
        this.mType = mType;
        return this;
    }

    public BaseFormElement setTitle(String mTitle) {
        this.mTitle = mTitle;
        return this;
    }

    public BaseFormElement setValue(String mValue) {
        this.mValue = mValue;
        return this;
    }

    public BaseFormElement setHint(String mHint) {
        this.mHint = mHint;
        return this;
    }

    public BaseFormElement setRequired(boolean required) {
        this.mRequired = required;
        return this;
    }

    public BaseFormElement setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    // getters
    public int getTag() {
        return this.mTag;
    }

    public int getType() {
        return this.mType;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getValue() {
        return (this.mValue == null) ? "" : this.mValue;
    }

    public String getHint() {
        return (this.mHint == null) ? "" : this.mHint;
    }

    public boolean isRequired() {
        return this.mRequired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "BaseFormElement{" +
                "mTag=" + mTag +
                ", mType=" + mType +
                ", mTitle='" + mTitle + '\'' +
                ", mValue='" + mValue + '\'' +
                ", mHint='" + mHint + '\'' +
                ", mRequired=" + mRequired +
                '}';
    }
}
