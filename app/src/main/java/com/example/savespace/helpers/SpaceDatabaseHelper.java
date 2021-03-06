package com.example.savespace.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SpaceDatabaseHelper extends SQLiteOpenHelper {
    private static SpaceDatabaseHelper spaceInstance;

    // Database Info
    private static final String DATABASE_NAME = "SPACE_DATABASE";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_NOTES = "SAVENOTES";

    // NOTES Table Columns
    private static final String NOTE_ID = "id";
    private static final String NOTE_TITLE = "title";
    private static final String NOTE_INFO = "notes";
    private static final String NOTE_MODIFIED_DATE = "m_date";
    private static final String NOTE_MODIFIED_TIME = "m_time";

    // Resources
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String TAG = "SpaceDatabaseHelper";

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
            onCreate(db);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_NOTES + "(" +
                        NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        NOTE_TITLE + " text, " +
                        NOTE_INFO + " text NOT NULL, " +
                        NOTE_MODIFIED_DATE + " text NOT NULL, " +
                        NOTE_MODIFIED_TIME + " text NOT NULL" +
                ");"
        );
    }

    public static synchronized SpaceDatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (spaceInstance == null) {
            spaceInstance = new SpaceDatabaseHelper(context.getApplicationContext());
        }
        return spaceInstance;
    }


    private SpaceDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
        Hands on Databased functions
     */
    ////////////////////////////////////////////////////////////////////////////////////////////////
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
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /*
        Specific functions to modifying the table(s) within the database
     */
    public void addNote(SpaceNote spaceNote) {
        SQLiteDatabase db = getDB();

        db.beginTransaction();
        try {
            ContentValues params = new ContentValues();
            params.put(NOTE_TITLE, spaceNote.getTitle());
            params.put(NOTE_INFO, spaceNote.getNotes());
            params.put(NOTE_MODIFIED_DATE, spaceNote.getM_date());
            params.put(NOTE_MODIFIED_TIME, spaceNote.getM_time());

            db.insertOrThrow(TABLE_NOTES, null, params);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add Note to Database");
        } finally {
            db.endTransaction();
        }
    }

    // Method to modify or add a row into the database
    public void modifyNote(SpaceNote spaceNote) {
        SQLiteDatabase db = getDB();

        db.beginTransaction();
        try {
            ContentValues params = new ContentValues();
            params.put(NOTE_TITLE, spaceNote.getTitle());
            params.put(NOTE_INFO, spaceNote.getNotes());
            params.put(NOTE_MODIFIED_DATE, spaceNote.getM_date());
            params.put(NOTE_MODIFIED_TIME, spaceNote.getM_time());

            int rows = db.update(
                    TABLE_NOTES,
                    params,
                    NOTE_ID + "=?",
                    new String[]{Integer.toString(spaceNote.getId())}
                    );

            if (rows == 1) {
                String sqlSQ = String.format(
                        "SELECT %s FROM %s WHERE %s = ?",
                        NOTE_ID, TABLE_NOTES, NOTE_ID
                        );
                Cursor cursor = doQuery(sqlSQ, new String[]{String.valueOf(spaceNote.getId())});
                try {
                    if (cursor.moveToFirst()) {
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } else {
                db.insertOrThrow(TABLE_NOTES, null, params);
                db.setTransactionSuccessful();
            }

            // Another way of updating
            // but is more redundent since it reinitializes
            // db
            /*doUpdate(
                    "UPDATE " + TABLE_NOTES +
                            " SET " +
                            NOTE_TITLE + "=?, " +
                            NOTE_INFO + "=?, " +
                            NOTE_MODIFIED_DATE + "=?, " +
                            NOTE_MODIFIED_TIME + "=? ",
                    new String[]{
                            spaceNote.getTitle(),
                            spaceNote.getNotes(),
                            spaceNote.getM_date(),
                            spaceNote.getM_time()
                    }
            );*/
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to update user");
        } finally {
            db.endTransaction();
        }
    }

    // Method to Retrieve all Notes
    // Returns List of SpaceNote
    public ArrayList<SpaceNote> getAllNotes() {
        ArrayList<SpaceNote> spaceNotes = new ArrayList<>();

        String NOTE_SELECT_QUERY =
                String.format("SELECT %s, %s, %s, %s, %s FROM %s",
                        NOTE_ID,
                        NOTE_TITLE,
                        NOTE_INFO,
                        NOTE_MODIFIED_DATE,
                        NOTE_MODIFIED_TIME,
                        TABLE_NOTES);

        Cursor notesCursor = doQuery(NOTE_SELECT_QUERY);
        try {
            if (notesCursor.moveToFirst()) {
                do {
                    int id = Integer.parseInt(notesCursor.getString(notesCursor.getColumnIndex(NOTE_ID)));
                    String title = notesCursor.getString(notesCursor.getColumnIndex(NOTE_TITLE));
                    String notes = notesCursor.getString(notesCursor.getColumnIndex(NOTE_INFO));
                    String m_date = notesCursor.getString(notesCursor.getColumnIndex(NOTE_MODIFIED_DATE));
                    String m_time = notesCursor.getString(notesCursor.getColumnIndex(NOTE_MODIFIED_TIME));
                    SpaceNote temp = new SpaceNote(id, title, notes, m_date, m_time);
                    spaceNotes.add(temp);
                } while (notesCursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get notes from Database");
            e.printStackTrace();
        } finally {
            if (notesCursor != null && !notesCursor.isClosed()) {
                notesCursor.close();
            }
        }
        return spaceNotes;
    }

    // Method to retrieve one note given it id
    // return null if no note exists
    public SpaceNote getNote(int id) {
        String NOTE_SELECT_QUERY =
                String.format("SELECT  %s, %s, %s, %s from %s where %s=?",
                        NOTE_TITLE,
                        NOTE_INFO,
                        NOTE_MODIFIED_DATE,
                        NOTE_MODIFIED_TIME,
                        TABLE_NOTES,
                        NOTE_ID);
        SpaceNote note = null;
        Cursor notesCursor = doQuery(NOTE_SELECT_QUERY, new String[]{Integer.toString(id)});
        try {
            if (notesCursor.moveToFirst()) {
                String title = notesCursor.getString(notesCursor.getColumnIndex(NOTE_TITLE));
                String notes = notesCursor.getString(notesCursor.getColumnIndex(NOTE_INFO));
                String m_date = notesCursor.getString(notesCursor.getColumnIndex(NOTE_MODIFIED_DATE));
                String m_time = notesCursor.getString(notesCursor.getColumnIndex(NOTE_MODIFIED_TIME));
                note = new SpaceNote(id, title, notes, m_date, m_time);
                note.print();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get note with id : "+Integer.toString(id));
        } finally {
            if (notesCursor != null && !notesCursor.isClosed()) {
                notesCursor.close();
            }
        }
        return note;
    }

    // Method to delete Note
    public void deleteNote(ArrayList<Integer> noteIds) {
        ArrayList<Integer> records = new ArrayList<>();
        SQLiteDatabase db = getDB();
        db.beginTransaction();
        try {
            for (int i = 0; i < noteIds.size(); i++) {
                records.add(db.delete(TABLE_NOTES, NOTE_ID + "=?", new String[]{Integer.toString(noteIds.get(i))}));
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete "+noteIds.size()+" notes");
        } finally {
            db.endTransaction();
        }
    }

    // Method to delete All Notes
    public void deleteAllNotes() {
        SQLiteDatabase db = getDB();
        db.beginTransaction();
        try {
            db.delete(TABLE_NOTES, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete all Notes");
        } finally {
            db.endTransaction();
        }
    }

    /*
    NOTES:
        If we ever would like to add more than one
        row into the table consider using
        a compiled SQLiteStatement:
        https://www.techrepublic.com/blog/software-engineer/turbocharge-your-sqlite-inserts-on-android/

    RESOURCES:
        https://android-developers.googleblog.com/2009/01/avoiding-memory-leaks.html
        https://guides.codepath.com/android/local-databases-with-sqliteopenhelper#full-database-handler-source

    FUTURE ALT METHODS:
        https://www.androiddesignpatterns.com/2012/05/correctly-managing-your-sqlite-database.html
        https://guides.codepath.com/android/Persisting-Data-to-the-Device#object-relational-mappers
     */
}
