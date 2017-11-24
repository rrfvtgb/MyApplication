package com.rrfvtgb.myapplication.element;

import android.view.View;

public interface FormulaireElement {
    public String getLabel();
    public String getValue();
    public void setValue(String value);
    public View element();
}
