package com.rrfvtgb.myapplication.element;

import android.content.Context;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.rrfvtgb.myapplication.R;

/**
 * Created by rrfvtgb on 24/11/2017.
 */

public class RadioElement implements FormulaireElement {

    Context c;
    RadioGroup group;

    public RadioElement(Context c){
        this.c = c;
        this.group = new RadioGroup(c);
        this.group.setPadding(0, 20, 0, 20);
    }

    public void addValue(String label){
        RadioButton radio = new RadioButton(c);
        radio.setText(label);
        radio.setTextAppearance(R.style.Label);

        this.group.addView(radio);
    }



    @Override
    public String getValue() {
        // get selected radio button from radioGroup
        int selectedId = group.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        RadioButton radioButton = group.findViewById(selectedId);

        return radioButton.getText().toString();
    }

    @Override
    public void setValue(String value) {

    }

    @Override
    public View element() {
        return this.group;
    }
}
