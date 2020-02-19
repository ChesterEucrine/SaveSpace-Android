package com.example.savespace;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<SpaceNote> spaceNotes;
    ArrayList<String> food;
    //SpaceAdapter spaceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        food = new ArrayList<String>();
        food.add("abx");
        food.add("gang");
        food.add("last words");
        food.add("incels");
        food.add("ISIS");
        food.add("Karen");
        food.add("Terrorists");
        food.add("mixer");
        food.add("Objective");
        food.add("Overdose");
        food.add("positive");
        food.add("Trying");
        food.add("Murder");

        //SpaceNote a = new SpaceNote(0, "Hello World", "Werukamu tsu za warudo!!!", "16-02-2020", "00:00");
        /*SpaceNote b = new SpaceNote(0, "Hello World", "Werukamu tsu za warudo!!!", "16-02-2020", "00:00");
        SpaceNote c = new SpaceNote(0, "Hello World", "Werukamu tsu za warudo!!!", "16-02-2020", "00:00");
        SpaceNote d = new SpaceNote(0, "Hello World", "Werukamu tsu za warudo!!!", "16-02-2020", "00:00");
        SpaceNote e = new SpaceNote(0, "Hello World", "Werukamu tsu za warudo!!!", "16-02-2020", "00:00");
        SpaceNote f = new SpaceNote(0, "Hello World", "Werukamu tsu za warudo!!!", "16-02-2020", "00:00");
        SpaceNote g = new SpaceNote(0, "Hello World", "Werukamu tsu za warudo!!!", "16-02-2020", "00:00");
        SpaceNote h = new SpaceNote(0, "Hello World", "Werukamu tsu za warudo!!!", "16-02-2020", "00:00");
        SpaceNote i = new SpaceNote(0, "Hello World", "Werukamu tsu za warudo!!!", "16-02-2020", "00:00");
        SpaceNote j = new SpaceNote(0, "Hello World", "Werukamu tsu za warudo!!!", "16-02-2020", "00:00");
        SpaceNote k = new SpaceNote(0, "Hello World", "Werukamu tsu za warudo!!!", "16-02-2020", "00:00");
        SpaceNote l = new SpaceNote(0, "Hello World", "Werukamu tsu za warudo!!!", "16-02-2020", "00:00");*/


        //spaceNotes = new ArrayList<String>();
        //spaceNotes.add(a);
        /*spaceNotes.add(b);
        spaceNotes.add(c);
        spaceNotes.add(d);
        spaceNotes.add(e);
        spaceNotes.add(f);
        spaceNotes.add(g);
        spaceNotes.add(h);
        spaceNotes.add(i);
        spaceNotes.add(j);
        spaceNotes.add(k);
        spaceNotes.add(l);*/

        //spaceAdapter = new SpaceAdapter(spaceNotes, this, R.layout.note_list);
        //ListView main_list = findViewById(R.id.main_list);
        //main_list.setAdapter(spaceAdapter);

        ListView main_list = findViewById(R.id.main_list);
        ArrayAdapter<String> foodAdapter = new ArrayAdapter<String>(this, R.layout.activity_main);
        main_list.setAdapter(foodAdapter);

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
