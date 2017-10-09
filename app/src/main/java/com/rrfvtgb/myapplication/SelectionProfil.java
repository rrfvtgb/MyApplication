package com.rrfvtgb.myapplication;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fabie on 09/10/2017.
 */

public class SelectionProfil extends LinearLayout {
    JSONArray table;
    public void setTable(JSONObject j)
    {
        try {
            table=j.getJSONArray("service");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void afficher() throws Exception
    {
        int i;
        Button b = null;
        for(i=0;i<table.length();i++)
        {
            b=new Button(this.getContext());
            String ch = table.getJSONObject(i).optString("title");
            b.setText(ch.toCharArray(),0,ch.length());
            this.addView(b);
        }

    }
    public SelectionProfil(Context context) {
        super(context);
    }
}
