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

import java.util.ArrayList;

/**
 * Created by fabie on 09/10/2017.
 */

public class SelectionProfil extends LinearLayout {
    JSONArray table;
    MainActivity acti;
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
        this.removeAllViews();
        int i;
        ArrayList<Button> listeButton;
        listeButton = new ArrayList<Button>();
        Button b = null;
        for(i=0;i<table.length();i++)
        {
            listeButton.add(new Button(this.getContext()));
            final String ch = table.getJSONObject(i).optString("title");
            listeButton.get(listeButton.size()-1).setText(ch.toCharArray(),0,ch.length());
            listeButton.get(listeButton.size()-1).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    acti.profil = new String(ch);
                }
            });
            this.addView( listeButton.get(listeButton.size()-1));
        }

    }
    public SelectionProfil(MainActivity acti) {
        super(acti);
        this.acti=acti;
        this.setOrientation(LinearLayout.VERTICAL);

    }
}
