package com.example.savespace.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.savespace.R;

public class AlertDialogue {
    private Activity activity;
    private AlertDialog alertDialog;
    private Integer type;

    /*
        Function to load the type of dialogue needed
        i.e prompt_dialogue
            delete_dialogue
     */
    public AlertDialogue(Activity a, int t) {
        type = t;
        activity = a;
    }

    public void startLoadingDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(type, null));
        builder.setCancelable(true);

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void stopLoadingDialogue() {
        alertDialog.dismiss();
    }
}
