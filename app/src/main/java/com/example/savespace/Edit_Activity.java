package com.example.savespace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.savespace.helpers.AlertDialogue;
import com.example.savespace.helpers.SpaceDatabaseHelper;
import com.example.savespace.helpers.SpaceNote;

import java.util.ArrayList;

public class Edit_Activity extends AppCompatActivity {

    SpaceDatabaseHelper spaceDatabaseHelper;
    Toolbar toolbar;
    AlertDialogue promptDialogue;
    AlertDialogue deleteDialogue;
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

        toolbar = findViewById(R.id.edit_toolbar);
        toolbar.setTitle("Note");
        setSupportActionBar(toolbar);

        spaceDatabaseHelper = SpaceDatabaseHelper.getInstance(this);
        promptDialogue = new AlertDialogue(this, R.layout.prompt_dialog);
        deleteDialogue = new AlertDialogue(this, R.layout.delete_dialog);
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
            Log.d(TAG, "ID not retrieved");
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
            promptDialogue.startLoadingDialogue();
    }

    public void doBack(View v) {
        String s_title = title.getText().toString();
        String message = note.getText().toString();
        if (!wasEdited(id, s_title, message)) {
            Toast.makeText(this, "No changes made", Toast.LENGTH_LONG).show();
            finish();
        }
        else
            promptDialogue.startLoadingDialogue();
    }

    public void doExit(View v) {
        promptDialogue.stopLoadingDialogue();
        finish();
    }

    /*
        Function to check if an edit was made on the note
     */
    public Boolean wasEdited(int id, String title, String message) {
        if (action == 0)
            return true;
        SpaceNote currentNote = spaceDatabaseHelper.getNote(id);
        if (!title.equals(currentNote.getTitle()))
            return true;
        if (!message.equals(currentNote.getNotes()))
            return true;
        return false;
    }

    public void doDelete(View v) {
        ArrayList<Integer> noteIds = new ArrayList<>();
        noteIds.add(id);
        spaceDatabaseHelper.deleteNote(noteIds);
        Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void doExitDialogue(View v) {
        deleteDialogue.stopLoadingDialogue();
    }

    public void doSave() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mm_edit_save:
                doSave();
                return true;
            case R.id.mm_edit_delete:
                deleteDialogue.startLoadingDialogue();
                return true;
            case R.id.mm_edit_lock:
                Toast.makeText(this, "Yet to implement", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    TODO:
        Consider a colour scheme
        Link notes to database
        Note Tags:
            - Business
            - Hobby
            - Task
            - Reminder
            - etc
     */
}
