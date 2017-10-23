package com.rrfvtgb.myapplication;

import android.content.Context;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.View;
import android.view.textservice.TextInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class AffichageSysteme extends LinearLayout {
    public void afficher() throws Exception
    {
        this.removeAllViews();
        TextView text = new TextView(this.getContext());
        text.setText( "temps thread:"+Long.toString(SystemClock.currentThreadTimeMillis()));
        this.addView(text);


    }
    public AffichageSysteme(Context context) {
        super(context);
    }
}
