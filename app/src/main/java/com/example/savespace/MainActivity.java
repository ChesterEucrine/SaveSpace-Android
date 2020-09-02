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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.savespace.helpers.SpaceAdapter;
import com.example.savespace.helpers.SpaceDatabaseHelper;
import com.example.savespace.helpers.SpaceNote;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<SpaceNote> spaceNotes;
    Toolbar toolbar;
    static public SpaceDatabaseHelper databaseHelpher;
    ListView main_list;
    SpaceAdapter spaceAdapter;
    private static final String TAG = "MAIN";

    @Override
    protected void onResume() {
        // fetch updated data
        super.onResume();
        spaceAdapter.updateList(databaseHelpher.getAllNotes());
        spaceAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        databaseHelpher = SpaceDatabaseHelper.getInstance(this);
        spaceNotes = new ArrayList<>();
        main_list = findViewById(R.id.main_list);
        setUpNotes();
    }

    public void setUpNotes() {
        spaceNotes = databaseHelpher.getAllNotes();
        for (SpaceNote n:spaceNotes) {
            n.print();
        }
        Log.d(TAG, "Length : "+spaceNotes.size());
        spaceAdapter = new SpaceAdapter(spaceNotes, this);
        main_list.setAdapter(spaceAdapter);

        main_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Touched", Toast.LENGTH_LONG).show();
                Intent edit = new Intent(getApplicationContext(), Edit_Activity.class);
                edit.putExtra("id", Integer.toString(spaceNotes.get(position).getId()));
                edit.putExtra("action", "1");
                startActivity(edit);
            }
        });

        main_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Long Hold", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    public void doAdd(View v) {
        Intent edit = new Intent(getApplicationContext(), Edit_Activity.class);
        edit.putExtra("action", "0");
        startActivity(edit);
    }

    public void doDelete(View v) {
        /*
        TODO:   Link to delete function MySQL
                Create Recycle bin table

         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mm_search:
                Toast.makeText(this, "Search", Toast.LENGTH_LONG).show();
                return true;
            case R.id.mm_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show();
                return true;
            case R.id.mm_delete:
                Toast.makeText(this, "Delete", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
     * TODO:
            Review SpaceAdapter and remove the animation part just have a simple adapter ??
     */

    /*
    *
    * https://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial
    * https://www.javatpoint.com/android-custom-listview
    * https://www.tutorialspoint.com/how-to-create-a-custom-listview-in-android
    * */
}
