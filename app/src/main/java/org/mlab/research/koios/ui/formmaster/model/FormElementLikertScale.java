package org.mlab.research.koios.ui.formmaster.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Riddhi - Rudra on 28-Jul-17.
 */

public class FormElementLikertScale extends BaseFormElement {

    private String pickerTitle; // custom title for picker
    private List<String> mOptions; // list of options for single and multi picker
    private List<String> mOptionsSelected; // list of selected options for single and multi picker
    private int likertType;
    private Boolean useLegend;
    private Boolean useCustomOptions;

    public FormElementLikertScale() {
    }

    public static FormElementLikertScale createInstance() {
        FormElementLikertScale FormElementLikertScale = new FormElementLikertScale();
        FormElementLikertScale.setType(BaseFormElement.TYPE_LIKERT_SCALE);
        FormElementLikertScale.setPickerTitle("Pick one");
        return FormElementLikertScale;
    }

    public FormElementLikertScale setTag(int mTag) {
        return (FormElementLikertScale)  super.setTag(mTag);
    }


    public FormElementLikertScale setType(int mType) {
        return (FormElementLikertScale)  super.setType(mType);
    }

    public FormElementLikertScale setTitle(String mTitle) {
        return (FormElementLikertScale)  super.setTitle(mTitle);
    }

    public FormElementLikertScale setValue(String mValue) {
        return (FormElementLikertScale)  super.setValue(mValue);
    }

    public FormElementLikertScale setHint(String mHint) {
        return (FormElementLikertScale)  super.setHint(mHint);
    }

    public FormElementLikertScale setRequired(boolean required) {
        return (FormElementLikertScale)  super.setRequired(required);
    }

    // custom setters
    public FormElementLikertScale setOptions(List<String> mOptions) {
        this.mOptions = mOptions;
        return this;
    }

    public FormElementLikertScale setOptionsSelected(List<String> mOptionsSelected) {
        this.mOptionsSelected = mOptionsSelected;
        return this;
    }

    public FormElementLikertScale setPickerTitle(String title) {
        this.pickerTitle = title;
        return this;
    }

    public FormElementLikertScale setLikertType(int likertType) {
        this.likertType = likertType;
        return this;
    }

    public FormElementLikertScale setUseLegend(Boolean useLegend) {
        this.useLegend = useLegend;
        return this;
    }

    public FormElementLikertScale setUseCustomOptions(Boolean useCustomOptions) {
        this.useCustomOptions = useCustomOptions;
        return this;
    }

    // custom getters
    public List<String> getOptions() {
        return (this.mOptions == null) ? new ArrayList<String>() : this.mOptions;
    }

    public List<String> getOptionsSelected() {
        return (this.mOptionsSelected == null) ? new ArrayList<String>() : this.mOptionsSelected;
    }

    public String getPickerTitle() {
        return this.pickerTitle;
    }

    public int getLikertType() { return this.likertType; }
    public Boolean getUseLegend() { return this.useLegend; }
    public Boolean getUseCustomOptions() { return this.useCustomOptions; }


}
