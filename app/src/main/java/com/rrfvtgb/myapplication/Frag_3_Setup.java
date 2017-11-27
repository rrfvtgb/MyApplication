package com.rrfvtgb.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Frag_3_Setup extends Fragment implements View.OnClickListener {
    static final int IMPORT_JSON_REQUEST = 30;  // The request code

    private static final String AUTHORITY = "com.rrfvtgb.myapplication";
    private static final String TAG = "Frag_3_Home";
    protected Map<String, JSONObject> profils = new HashMap<>();
    protected ArrayList<String> profils_names = new ArrayList<>();

    protected Spinner profil_spinner;

    protected Button input;
    protected Button output;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_3__setup, container, false);

        input = view.findViewById(R.id.profil_in);
        output = view.findViewById(R.id.profil_out);

        load();
        //sélectionneur profil
        profil_spinner = view.findViewById(R.id.profil_select);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(),
                R.layout.frag_3_profil_item, profils_names);
        profil_spinner.setAdapter(adapter);

        input.setOnClickListener(this);
        output.setOnClickListener(this);

        return view;
    }

    protected void load() {
        if (profils.isEmpty() && this.getContext() != null) {
            preload(this.getContext());
        }
    }

    //charge les différents profils
    public void preload(Context c) {
        try {
            populateMap(c);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getActiveProfil() {
        if (profil_spinner != null) {
            return (String) profil_spinner.getSelectedItem();
        } else {
            return profils_names.get(0);
        }
    }

    public JSONObject getProfilData() {
        load();

        return profils.get(this.getActiveProfil());
    }

    protected void populateMap(Context c) throws JSONException {
        JSONObject data = readJSON(c);

        mergeJSON(data);
    }

    private void mergeJSON(JSONObject data) throws JSONException {
        JSONArray services = data.optJSONArray("service");

        for (int i = 0; i < services.length(); i++) {
            JSONObject s = services.getJSONObject(i);

            String title = s.getString("title");

            profils.put(title, s);
            profils_names.add(title);

            Log.i(TAG, "Added " + title);
        }
    }

    protected void saveJSON(JSONObject data) {
        File file = new File(this.getContext().getFilesDir(), "service.json");

        FileOutputStream outputStream;

        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(data.toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void saveProfil() {
        JSONObject profil = new JSONObject();
        JSONArray services = new JSONArray();

        try {
            profil.put("service", services);

            for (String name : profils.keySet()) {
                JSONObject data = profils.get(name);
                services.put(data);
            }

            saveJSON(profil);

            Log.i(TAG, "Saved profil");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected InputStream JSONStream(Context c) throws IOException {
        File cache = new File(c.getFilesDir(), "service.json");

        if (cache.exists()) {
            return new FileInputStream(cache);
        } else {
            return c.getAssets().open("service.json");
        }
    }

    protected JSONObject readJSON(Context c) {
        //Read text from file
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(JSONStream(c)));
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

    public void askImport() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        startActivityForResult(intent, IMPORT_JSON_REQUEST);
    }

    public void askExport() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_STREAM,
                FileProvider.getUriForFile(this.getContext(), AUTHORITY,
                        new File(this.getContext().getFilesDir(), "service.json")));
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);

        Log.i(TAG, "Exported profil");
    }

    @Override
    public void onClick(View view) {
        if (view == input) {
            askImport();
        } else if (view == output) {
            askExport();
        }
    }

    public void resultImport(Intent data) {
        try {
            Uri uri = data.getData();

            try {
                Log.i(TAG, "Importing " + uri);
                importFile(uri);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void importFile(Uri uri)
            throws IOException {
        //Read text from file
        StringBuilder text = new StringBuilder();
        InputStream is;

        try {
            is = this.getContext().getContentResolver().openInputStream(uri);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String result = text.toString();

        try {
            JSONObject json = new JSONObject(result);

            mergeJSON(json);

            saveProfil();

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(),
                    R.layout.frag_3_profil_item, profils_names);
            profil_spinner.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "Activity Result" + requestCode + " : " + resultCode);

        if (requestCode == Frag_3_Setup.IMPORT_JSON_REQUEST) {
            this.resultImport(data);
        }
    }
}
