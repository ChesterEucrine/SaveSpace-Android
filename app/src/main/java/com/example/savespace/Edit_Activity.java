package com.example.savespace;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Edit_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO:
        //      Retrieve data from clicked note
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
    }

    public void doBack(View v) {
        finish();
    }

    public void doSave(View v) {
        // TODO :
        //      Save note into the Database and update last saved
        EditText note = findViewById(R.id.text_note);
        String message = note.getText().toString();
        Toast.makeText(this, "Note : "+message, Toast.LENGTH_LONG).show();
    }

    /*
    TODO:
        Link notes to database

     */
}
