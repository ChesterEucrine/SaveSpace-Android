package com.example.savespace.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.savespace.R;

public class LoadingDialogue {
    private Activity activity;
    private AlertDialog alertDialog;

    public LoadingDialogue(Activity a) {
        activity = a;
    }

    public void startLoadingDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));
        builder.setCancelable(true);

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void stopLoadingDialogue() {
        alertDialog.dismiss();
    }
}
