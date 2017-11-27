package com.rrfvtgb.myapplication.element;

import android.view.View;

public interface FormulaireElement {
    String getLabel();

    String getValue();

    void setValue(String value);

    View element();
}
