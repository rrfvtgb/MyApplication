package com.rrfvtgb.myapplication;

import android.app.admin.ConnectEvent;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;

/**
 * Created by rrfvtgb on 23/10/2017.
 */

public class MonitorDialog {
    private AlertDialog dialog;
    private Context context;

    public MonitorDialog(Context context){
        this.context = context;
        this.create();
    }

    public void create() {

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setView(R.layout.dialog_monitor);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()

        {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });

        // 3. Get the AlertDialog from create()
        this.dialog = builder.create();
    }

    public void show(){
        this.dialog.show();
    }
}
