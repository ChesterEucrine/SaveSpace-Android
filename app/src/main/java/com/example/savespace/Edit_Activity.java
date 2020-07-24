package com.example.savespace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Edit_Activity extends AppCompatActivity {

    SpaceDatabaseHelper spaceDatabaseHelper;
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

    public void doBack(View v) {
        finish();
    }

    public void doSave(View v) {
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
        Toast.makeText(this, "Saved Maybe", Toast.LENGTH_LONG).show();
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
