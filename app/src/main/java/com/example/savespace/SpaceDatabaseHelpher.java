package com.example.savespace;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.File;
import java.text.SimpleDateFormat;

public class SpaceDatabaseHelpher extends SQLiteOpenHelper {

    private String DB_Path;
    private String DB_Name;
    private Context DB_Context;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

    public SpaceDatabaseHelpher(Context context, String name) {
        super(context, name, null, 1);
        DB_Context = context;
        DB_Name = name;
    }

    public String getNow()
    {
        return sdf.format(new java.util.Date());
    }

    public Cursor doQuery(String sql, String [] params)
    {
        try
        {
            Cursor mCur = getReadableDatabase().rawQuery(sql, params);
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            System.err.println("-- doQuery --\n"+sql);
            mSQLException.printStackTrace(System.err);
            return null;
        }
    }

    public void doUpdate (String sql, String [] params)
    {
        try
        {
            getWritableDatabase().execSQL(sql, params);
        }
        catch (SQLException mSQLException)
        {
            System.err.println("-- doUpdate --\n"+sql);
            mSQLException.printStackTrace(System.err);
        }
    }

    public Cursor doQuery(String sql)
    {
        try
        {
            Cursor mCur = getReadableDatabase().rawQuery(sql, null);
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            System.err.println("-- doQuery --\n"+sql);
            mSQLException.printStackTrace();
            return null;
        }
    }

    public void doUpdate(String sql) {
        try {
            this.getWritableDatabase().execSQL(sql);
        } catch (SQLException m) {
            System.err.println("-- doUpdate --\n" + sql);
            m.printStackTrace(System.err);
        }
    }

    public long getSize()
    {
        final SQLiteDatabase db = getReadableDatabase();

        final String dbPath     = db.getPath();
        final File   dbFile     = new File(dbPath);
        final long dbFileLength = dbFile.length();

        return (dbFileLength);
    }

    public SQLiteDatabase getDB()
    {
        return getWritableDatabase();
    }

}
