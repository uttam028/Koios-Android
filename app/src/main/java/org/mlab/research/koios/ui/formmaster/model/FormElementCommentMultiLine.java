package org.mlab.research.koios.ui.formmaster.model;


/**
 * Created by Riddhi - Rudra on 28-Jul-17.
 */

public class FormElementCommentMultiLine extends BaseFormElement {

    public FormElementCommentMultiLine() {
    }

    public static FormElementCommentMultiLine createInstance() {
        FormElementCommentMultiLine FormElementTextMultiLine = new FormElementCommentMultiLine();
        FormElementTextMultiLine.setType(BaseFormElement.TYPE_COMMENT_MULTILINE);
        return FormElementTextMultiLine;
    }

    public FormElementCommentMultiLine setTag(int mTag) {
        return (FormElementCommentMultiLine)  super.setTag(mTag);
    }

    public FormElementCommentMultiLine setType(int mType) {
        return (FormElementCommentMultiLine)  super.setType(mType);
    }

    public FormElementCommentMultiLine setTitle(String mTitle) {
        return (FormElementCommentMultiLine)  super.setTitle(mTitle);
    }

    public FormElementCommentMultiLine setValue(String mValue) {
        return (FormElementCommentMultiLine)  super.setValue(mValue);
    }

    public FormElementCommentMultiLine setHint(String mHint) {
        return (FormElementCommentMultiLine)  super.setHint(mHint);
    }

    public FormElementCommentMultiLine setRequired(boolean required) {
        return (FormElementCommentMultiLine)  super.setRequired(required);
    }

}
