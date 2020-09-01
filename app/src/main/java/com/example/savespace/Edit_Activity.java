package com.example.savespace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.savespace.helpers.LoadingDialogue;
import com.example.savespace.helpers.SpaceDatabaseHelper;
import com.example.savespace.helpers.SpaceNote;

public class Edit_Activity extends AppCompatActivity {

    SpaceDatabaseHelper spaceDatabaseHelper;
    LoadingDialogue loadingDialogue;
    EditText title;
    EditText note;
    int id;
    int action;
    private static final String TAG = "Edit";

    /*
        Actions :
            0 = Create new note
            1 = edit already existing note
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO:
        //      Retrieve data from clicked note
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        spaceDatabaseHelper = SpaceDatabaseHelper.getInstance(this);
        loadingDialogue = new LoadingDialogue(this);
        Intent parent = getIntent();
        if (parent.hasExtra("action")) {
            action = Integer.parseInt(parent.getStringExtra("action"));
            Log.d(TAG, "Action retrieved : "+action);
        }
        else {
            action = 0;
            Log.d(TAG, "Action not retrieved");
        }
        if (parent.hasExtra("id")) {
            id = Integer.parseInt(parent.getStringExtra("id"));
            Log.d(TAG, "ID retrieved : "+id);
        }
        else {
            action = 0;
            Log.d(TAG, "ID retrieved");
        }

        title = findViewById(R.id.edit_title);
        note = findViewById(R.id.edit_note);
        if (action == 1) {
            noteSetUp(id);
        }
    }

    @Override
    public void onBackPressed() {
        String s_title = title.getText().toString();
        String message = note.getText().toString();
        if (!wasEdited(id, s_title, message)) {
            Toast.makeText(this, "No changes made", Toast.LENGTH_LONG).show();
            finish();
        }
        else
            loadingDialogue.startLoadingDialogue();
    }

    public void doBack(View v) {
        String s_title = title.getText().toString();
        String message = note.getText().toString();
        if (!wasEdited(id, s_title, message)) {
            Toast.makeText(this, "No changes made", Toast.LENGTH_LONG).show();
            finish();
        }
        else
            loadingDialogue.startLoadingDialogue();
    }

    public void doExit(View v) {
        loadingDialogue.stopLoadingDialogue();
        finish();
    }

    /*
        Function to check if an edit was made on the note
     */
    public Boolean wasEdited(int id, String title, String message) {
        SpaceNote currentNote = spaceDatabaseHelper.getNote(id);
        if (!title.equals(currentNote.getTitle()))
            return true;
        if (!message.equals(currentNote.getNotes()))
            return true;
        return false;
    }

    public void doSave(View v) {
        // TODO :
        //      Save note into the Database and update last saved
        // Retrieve SpaceNote data
        String s_title = title.getText().toString();
        String message = note.getText().toString();
        String date_time = spaceDatabaseHelper.getNow();
        String [] temp = date_time.split(" ");

        if (!wasEdited(id, s_title, message)) {
            Toast.makeText(this, "No changes made", Toast.LENGTH_LONG).show();
            return;
        }
        else {
            // Save data to database
            SpaceNote spaceNote = new SpaceNote(id, s_title, message, temp[0], temp[1]);
            if (action == 0) {
                spaceDatabaseHelper.addNote(spaceNote);
            } else {
                spaceDatabaseHelper.modifyNote(spaceNote);
            }
            Log.i(TAG, "Successfully Saved Note");
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
        }
    }

    public void doSaveAndExit(View v) {
        // TODO :
        //      Save note into the Database and update last saved
        // Retrieve SpaceNote data
        String s_title = title.getText().toString();
        String message = note.getText().toString();
        String date_time = spaceDatabaseHelper.getNow();
        String [] temp = date_time.split(" ");

        // Save data to database
        SpaceNote spaceNote = new SpaceNote(id, s_title, message, temp[0], temp[1]);
        if (action == 0) {
            spaceDatabaseHelper.addNote(spaceNote);
        } else {
            spaceDatabaseHelper.modifyNote(spaceNote);
        }
        Log.i(TAG, "Successfully Saved Note");
        Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
        finish();
    }

    public void noteSetUp(int id) {
        SpaceNote spaceNote = spaceDatabaseHelper.getNote(id);
        String s = "";
        if (!spaceNote.getTitle().isEmpty()) {
            s = spaceNote.getTitle();
        }
        title.setText(s);
        note.setText(spaceNote.getNotes());
    }

    /*
    TODO:
        Link notes to database
        Note Tags:
            - Business
            - Hobby
            - Task
            - Reminder
            - etc
     */
}
