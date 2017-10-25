package com.rrfvtgb.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Process;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by rrfvtgb on 23/10/2017.
 */

public class MonitorDialog implements Runnable {
    private AlertDialog dialog;
    private Activity context;
    private Thread thread;
    private TextView processor;
    private TextView memory;

    private long lastCpu;
    private final long interval = 2000;

    public MonitorDialog(Activity context){
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
                hide();
            }
        });

        // 3. Get the AlertDialog from create()
        this.dialog = builder.create();

        this.processor = (TextView) this.dialog.findViewById(R.id.processor);
        this.memory = (TextView) this.dialog.findViewById(R.id.memory);
    }

    public void show(){
        this.dialog.show();
        this.processor = (TextView) this.dialog.findViewById(R.id.processor);
        this.memory = (TextView) this.dialog.findViewById(R.id.memory);

        if(this.thread == null) {
            this.thread = new Thread(this);
            this.thread.start();
        }
    }

    public void hide(){
        this.dialog.hide();
        this.thread.interrupt();
        this.thread = null;
    }

    @Override
    public void run() {
        if(this.processor == null)
            return;

        long max = Runtime.getRuntime().maxMemory();
        this.lastCpu = Process.getElapsedCpuTime();

        while(!this.thread.isInterrupted()) {
            long cpu = Process.getElapsedCpuTime();

            long total = Runtime.getRuntime().totalMemory();
            processor.setText((((float)(cpu - this.lastCpu)*100)/interval)+"% CPU");
            memory.setText(String.format(Locale.getDefault(), "%d Mo / %d Mo Memory", total >> 20,  max  >> 20));

            this.lastCpu = cpu;

            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
