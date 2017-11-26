package com.rrfvtgb.myapplication;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.rrfvtgb.myapplication.element.EditElement;
import com.rrfvtgb.myapplication.element.FormulaireElement;
import com.rrfvtgb.myapplication.element.LabelElement;
import com.rrfvtgb.myapplication.element.RadioElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Formulaire extends LinearLayout implements ProfilListener {
    JSONArray table;
    MainActivity acti;
    String profil;
    ArrayList<FormulaireElement> elements = new ArrayList<>();

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
        elements.clear();

        // Ajout du titre
        TextView titre = new TextView(this.getContext());
        titre.setText(profil.optString("title"));
        titre.setTextAppearance(R.style.Title);
        titre.setPadding(0, 0, 0, 40);
        this.addView(titre);

        // Recuperation des elements
        JSONArray element = profil.getJSONArray("element");
        RadioElement tmpRadioGroup = null;
        for (int i = 0; i < element.length(); i++) {
            JSONObject el = element.getJSONObject(i);
            String type = el.optString("type");
            String label = el.optString("label");

            Log.d("JSON-FORMULAIRE", type);
            switch (type) {
                case "label":
                    this.addElement(
                            new LabelElement(this.getContext(), label)
                    );
                    break;
                case "button":
                    if (i == 0
                            || !element.getJSONObject(i - 1).optString("type").contentEquals("button")
                            || !element.getJSONObject(i - 1).optString("section").contentEquals(element.getJSONObject(i).optString("section"))) {
                        tmpRadioGroup = new RadioElement(this.getContext());
                        this.addElement(tmpRadioGroup);
                    }


                    tmpRadioGroup.addValue(label);
                    //listeVue.add(tmpButton);
                    break;

                case "radio":
                    RadioElement radio = new RadioElement(this.getContext(), label);

                    JSONArray values = el.optJSONArray("values");

                    for(int j = 0; j<values.length(); j++){
                        radio.addValue(values.getString(j));
                    }

                    this.addElement(radio);

                default:
                // Not found, using edit instead
                case "edit":
                    this.addElement(
                            new EditElement(this.getContext(), label)
                    );

                    break;
            }
        }
    }

    protected void addElement(FormulaireElement element){
        this.addView(element.element());

        elements.add(element);
    }

    public String computeValue(){
        JSONObject obj = new JSONObject();

        for(FormulaireElement element: elements){
            String value = element.getValue();
            String label = element.getLabel();

            if(value != null && label != null) {
                try {
                    obj.put(label, value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return obj.toString();
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
