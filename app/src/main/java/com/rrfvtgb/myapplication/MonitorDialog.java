package com.rrfvtgb.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 * Created by rrfvtgb on 23/10/2017.
 */

public class MonitorDialog implements Runnable {
    private final long interval = 2000;
    private AlertDialog dialog;
    private Activity context;
    private Thread thread;
    private TextView processor;
    private TextView memory;
    private long lastCpu;
    private long lastCpuTimestamp;
    private float last_CPU;
    private long last_memUsage;
    private long last_memMax;
    private RefreshHandler mRedrawHandler = new RefreshHandler();


    public MonitorDialog(Activity context) {
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

        this.processor = this.dialog.findViewById(R.id.processor);
        this.memory = this.dialog.findViewById(R.id.memory);
    }

    /**
     * Renvoie l'utilisation en CPU
     */
    public float getCPUUsage() {
        long cpu = Process.getElapsedCpuTime();
        long timestamp = System.currentTimeMillis();

        if (timestamp == lastCpuTimestamp)
            return last_CPU;

        float result = (cpu - lastCpu) * 100f / (timestamp - lastCpuTimestamp);

        lastCpu = cpu;
        lastCpuTimestamp = timestamp;

        last_CPU = result;

        return result;
    }

    /**
     * Affiche le dialog et lance le thread en background
     */
    public void show() {
        if (this.thread != null) {
            this.dialog.hide();
        }

        this.dialog.show();
        this.processor = this.dialog.findViewById(R.id.processor);
        this.memory = this.dialog.findViewById(R.id.memory);


        this.thread = new Thread(this);
        this.thread.start();
        updateUI();
    }

    /**
     * Cache le dialog et stop le thread
     */
    public void hide() {
        this.dialog.hide();
        this.thread.interrupt();
        this.thread = null;
    }

    @Override
    public void run() {
        if (this.processor == null)
            return;

        long max = Runtime.getRuntime().maxMemory();

        synchronized (this) {
            last_memMax = max >> 20;
        }

        while (this.thread == Thread.currentThread()) {
            long cpu = Process.getElapsedCpuTime();

            long total = Runtime.getRuntime().totalMemory();

            synchronized (this) {
                last_memUsage = total >> 20;
            }

            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    /**
     * Mettre à jour l'UI (à partir du main Thread)
     */
    private void updateUI() {
        if (this.thread != null) {
            mRedrawHandler.sleep(interval);

            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);

            synchronized (this) {

                processor.setText(String.format(Locale.getDefault(), "%s %% CPU", df.format(getCPUUsage())));
                memory.setText(String.format(Locale.getDefault(), "%d Mo / %d Mo Memory", last_memUsage, last_memMax));
            }
        }
    }

    class RefreshHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            updateUI();
        }

        public void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    }
}
