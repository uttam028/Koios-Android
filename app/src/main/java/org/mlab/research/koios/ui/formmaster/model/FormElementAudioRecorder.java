package org.mlab.research.koios.ui.formmaster.model;

public class FormElementAudioRecorder extends BaseFormElement {
    public static final String TAG = FormElementAudioRecorder.class.getSimpleName() + "_debug";

    public static FormElementAudioRecorder createInstance() {
        FormElementAudioRecorder formElementAudioRecorder = new FormElementAudioRecorder();
        formElementAudioRecorder.setType(BaseFormElement.TYPE_AUDIO_RECORDER);

        return formElementAudioRecorder;
    }

    public FormElementAudioRecorder setTag(int mTag) {
        return (FormElementAudioRecorder)  super.setTag(mTag);
    }

    public FormElementAudioRecorder setType(int mType) {
        return (FormElementAudioRecorder)  super.setType(mType);
    }

    public FormElementAudioRecorder setTitle(String mTitle) {
        return (FormElementAudioRecorder)  super.setTitle(mTitle);
    }

    public FormElementAudioRecorder setValue(String mValue) {
        return (FormElementAudioRecorder)  super.setValue(mValue);
    }

    public FormElementAudioRecorder setHint(String mHint) {
        return (FormElementAudioRecorder)  super.setHint(mHint);
    }

    public FormElementAudioRecorder setRequired(boolean required) {
        return (FormElementAudioRecorder)  super.setRequired(required);
    }

}
