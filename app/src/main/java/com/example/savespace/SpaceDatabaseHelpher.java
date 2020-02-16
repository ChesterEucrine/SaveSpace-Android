package com.example.savespace;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SpaceDatabaseHelpher extends SQLiteOpenHelper {

    private String DB_Name;
    private Context DB_Context;

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table SaveNotes(id int primary key autoincrement, title text, notes text not null, m_date text, m_time text);");
        db.execSQL("Insert into SaveNotes(title, notes, m_date, m_time) values('Hello', 'These are the new notes', '2020-02-11', '09:56');");
        db.execSQL("Insert into SaveNotes(title, notes, m_date, m_time) values('', 'Hello World', '2020-02-11', '09:57');");
    }

    public SpaceDatabaseHelpher(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    /*
     */
}
