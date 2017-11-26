package com.rrfvtgb.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class Frag_1_Home extends Fragment {
    private static final String TAG = "Frag_1_Home";
    public Formulaire vueFormulaire;

    private JSONObject profilData;

    /*
    public Frag_1_Home(MainActivity acti){
        this.acti=acti;
    }
*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_1__home, container, false);
        RelativeLayout v = view.findViewById(R.id.tab1);

        if(vueFormulaire == null) {
            vueFormulaire = new Formulaire(this.getContext());

            if(profilData != null){
                try {
                    vueFormulaire.setProfil(profilData);
                    profilData = null;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else{
            ((ViewGroup) vueFormulaire.getParent()).removeView(vueFormulaire);
        }

        v.addView(vueFormulaire);


        return view;
    }

    public void setProfilData(JSONObject obj) throws JSONException {
        if(vueFormulaire != null) {
            vueFormulaire.setProfil(obj);
        }else{
            profilData = obj;
        }
    }

    public String computeValue(){
        return vueFormulaire.computeValue();
    }
}
