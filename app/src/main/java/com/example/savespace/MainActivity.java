package com.example.savespace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<SpaceNote> spaceNotes;
    //SpaceAdapter spaceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SpaceNote a = new SpaceNote(0, "Hello World", "Werukamu tsu za warudo!!!", "16-02-2020", "00:00");

        spaceNotes = new ArrayList<>();
        spaceNotes.add(a);

        ListView main_list = findViewById(R.id.main_list);
        SpaceAdapter spaceAdapter = new SpaceAdapter(spaceNotes, this);
        main_list.setAdapter(spaceAdapter);

        main_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent edit = new Intent(getApplicationContext(), Edit_Activity.class);
                startActivity(edit);
            }
        });

    }

    /*
     * TO DO
     * Review SpaceAdapter and remove the animation part just have a simple adapter
     */

    /*
    *
    * https://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial
    * https://www.javatpoint.com/android-custom-listview
    * https://www.tutorialspoint.com/how-to-create-a-custom-listview-in-android
    * */
}
