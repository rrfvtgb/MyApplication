package com.rrfvtgb.myapplication;

import android.app.DialogFragment;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Formulaire extends LinearLayout {
    JSONArray table;
    MainActivity acti;
    String profil;

    public Formulaire(Context context) {
        super(context);

        this.setOrientation(LinearLayout.VERTICAL);
    }

    public void setTable(JSONObject j) {
        try {
            table = j.getJSONArray("service");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setProfil(JSONObject profil) throws JSONException {
        // Suppression des autres profils
        this.removeAllViews();

        // Ajout du titre
        TextView titre = new TextView(this.getContext());
        titre.setText(profil.optString("title"));
        titre.setTextAppearance(R.style.Title);
        titre.setPadding(0, 0, 0, 40);
        this.addView(titre);

        // Recuperation des elements
        JSONArray element = profil.getJSONArray("element");
        EditText tmpEditText;
        TextView tmpTextView;
        RadioGroup tmpRadioGroup = null;
        RadioButton tmpButton;
        for (int i = 0; i < element.length(); i++) {
            JSONObject el = element.getJSONObject(i);

            Log.d("JSON-FORMULAIRE", el.optString("type"));
            switch (el.optString("type")) {
                case "edit":
                    //

                    TextInputLayout textInput = new TextInputLayout(this.getContext());
                    textInput.setHint(el.optString("label"));
                    textInput.setHintAnimationEnabled (true);
                    textInput.setHintEnabled(true);

                            tmpEditText = new EditText(this.getContext());
                    textInput.addView(tmpEditText);
                    textInput.setHintTextAppearance(R.style.Label);

                    this.addView(textInput);

                    break;
                case "label":
                    tmpTextView = new TextView(this.getContext());
                    tmpTextView.setText(element.getJSONObject(i).optString("label"));
                    tmpTextView.setTextAppearance(R.style.Label);
                    this.addView(tmpTextView);
                    break;
                case "button":
                    if (i == 0
                            || !element.getJSONObject(i - 1).optString("type").contentEquals("button")
                            || !element.getJSONObject(i - 1).optString("section").contentEquals(element.getJSONObject(i).optString("section"))) {
                        tmpRadioGroup = new RadioGroup(this.getContext());
                        tmpRadioGroup.setPadding(0, 20, 0, 20);
                        this.addView(tmpRadioGroup);
                    }
                    tmpButton = new RadioButton(this.getContext());
                    tmpButton.setText(element.getJSONObject(i).optString("label"));
                    tmpButton.setTextAppearance(R.style.Label);

                    tmpRadioGroup.addView(tmpButton);
                    //listeVue.add(tmpButton);
                    break;
            }
        }
    }

    public void afficher() throws JSONException {
        JSONObject tmp = null;
        int i;
        for (i = 0; i < table.length(); i++) {
            tmp = table.getJSONObject(i);
            if (tmp.optString("title").equals(profil))
                break;
        }

        this.setProfil(tmp);
    }

    protected JSONObject readJSON() {
        //Read text from file
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(this.getContext().getAssets().open("service.json")));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) {
            //You'll need to add proper error handling here
        }

        String result = text.toString();

        //Set the text

        try {
            return new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void refresh() {
        this.setTable(readJSON());
    }

}
