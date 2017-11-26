package com.rrfvtgb.myapplication.element;

import android.content.Context;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.rrfvtgb.myapplication.R;

/**
 * Created by rrfvtgb on 24/11/2017.
 */

public class RadioElement implements FormulaireElement {

    protected Context c;
    protected LinearLayout layout;
    protected RadioGroup group;
    protected String label;
    protected TextView labelElement;

    public RadioElement(Context c){
        this.c = c;

        this.layout = new LinearLayout(c);

        this.labelElement = new TextView(c);

        this.group = new RadioGroup(c);
        this.group.setPadding(0, 20, 0, 20);

        this.layout.addView(this.labelElement);
        this.layout.addView(this.group);

        this.layout.setOrientation(LinearLayout.VERTICAL);
    }

    public RadioElement(Context c, String label){
        this(c);

        this.setLabel(label);
    }

    public void addValue(String label){
        RadioButton radio = new RadioButton(c);
        radio.setText(label);
        radio.setTextAppearance(R.style.Label);

        this.group.addView(radio);
    }

    public void setLabel(String label){
        this.label = label;

        this.labelElement.setText(label);
    }

    protected RadioButton selectedRadio(){
        // get selected radio button from radioGroup
        int selectedId = group.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        return group.findViewById(selectedId);
    }

    @Override
    public String getLabel() {
        if(this.label != null){
            return this.label;
        }

        RadioButton radioButton = selectedRadio();

        if(radioButton != null) {
            return radioButton.getText().toString();
        }else{
            return null;
        }
    }

    @Override
    public String getValue() {
        RadioButton radioButton = selectedRadio();

        if(radioButton == null) {
            return null;
        }else if(label == null){
            return "true";
        }else{
            return radioButton.getText().toString();
        }
    }

    @Override
    public void setValue(String value) {

    }

    @Override
    public View element() {
        return this.layout;
    }
}
