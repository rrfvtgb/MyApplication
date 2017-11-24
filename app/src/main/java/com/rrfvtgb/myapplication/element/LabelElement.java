package com.rrfvtgb.myapplication.element;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.rrfvtgb.myapplication.R;

/**
 * Created by rrfvtgb on 24/11/2017.
 */

public class LabelElement implements FormulaireElement {
    TextView view;

    public LabelElement(Context c, String label){
        view = new TextView(c);
        view.setText(label);
        view.setTextAppearance(R.style.Label);
    }

    @Override
    public String getLabel() {
        return null;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public void setValue(String value) {

    }

    @Override
    public View element() {
        return view;
    }
}
