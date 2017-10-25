package com.rrfvtgb.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by fabie on 23/10/2017.
 */

public class Formulaire extends LinearLayout {
    JSONArray table;
    MainActivity acti;
    public void setTable(JSONObject j)
    {
        try{
            table=j.getJSONArray("service");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    void afficher() throws JSONException {
        this.removeAllViews();
        JSONObject tmp = null;
        int i;
        for(i=0;i<table.length();i++)
        {
            tmp=table.getJSONObject(i);
            if(tmp.optString("title")==acti.profil)
                break;
        }
        this.setOrientation(LinearLayout.VERTICAL);
        //ajout du titre
        TextView titre = new TextView(this.getContext());
        titre.setText(tmp.optString("title"));
        this.addView(titre);
        //ajout des element
        ArrayList<View> listeVue = new ArrayList<View>();
        JSONArray element = tmp.getJSONArray("element");
        EditText tmpEditText;
        TextView tmpTextView;
        RadioGroup tmpRadioGroup = null;
        RadioButton tmpButton;
        for(i=0;i<element.length();i++)
        {
            Log.d("JSON-FORMULAIRE", element.getJSONObject(i).optString("type"));
            switch(element.getJSONObject(i).optString("type"))
            {
                case "edit":
                    tmpEditText=new EditText(this.getContext());
                    listeVue.add(tmpEditText);
                    break;
                case "label":
                    tmpTextView=new TextView(this.getContext());
                    tmpTextView.setText(element.getJSONObject(i).optString("label"));
                    listeVue.add(tmpTextView);
                    break;
                case "button":
                    if(i==0 || element.getJSONObject(i-1).optString("type").contentEquals("button")==false || element.getJSONObject(i-1).optString("section").contentEquals( element.getJSONObject(i).optString("section"))==false )
                    {
                        if(tmpRadioGroup != null)
                        { listeVue.add(tmpRadioGroup);
                            }
                        tmpRadioGroup=new RadioGroup(this.getContext());

                    }
                    tmpButton=new RadioButton(this.getContext());
                    tmpButton.setText(element.getJSONObject(i).optString("label"));

                    tmpRadioGroup.addView(tmpButton);
                    //listeVue.add(tmpButton);
                    if(i==element.length()-1||element.getJSONObject(i+1).optString("type").contentEquals("button")== false)
                    {
                        listeVue.add(tmpRadioGroup);
                        tmpRadioGroup=null;
                    }
                    break;
            }
        }
        for(i=0;i<listeVue.size();i++)
        {
            this.addView(listeVue.get(i));
        }
    }
    public Formulaire(MainActivity context) {
        super(context);
        this.acti=context;
    }
}
