package com.rrfvtgb.myapplication.element;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.rrfvtgb.myapplication.R;

/**
 * Created by rrfvtgb on 24/11/2017.
 */

public class EditElement implements FormulaireElement {

    TextInputLayout textLayout;
    EditText editText;

    public EditElement(Context c, String label){

        editText = new EditText(c);
        editText.setMaxLines(1);
        editText.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);

        textLayout = new TextInputLayout(c);
        textLayout.setHint(label);
        textLayout.setHintAnimationEnabled (true);
        textLayout.setHintEnabled(true);
        textLayout.addView(editText);
        textLayout.setHintTextAppearance(R.style.Label);
    }

    @Override
    public String getLabel() {
        return textLayout.getHint().toString();
    }

    @Override
    public String getValue() {
        return editText.getText().toString();
    }

    @Override
    public void setValue(String value) {
        editText.setText(value);
    }

    @Override
    public View element() {
        return textLayout;
    }
}
