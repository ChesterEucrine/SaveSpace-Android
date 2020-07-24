package com.example.savespace;

import android.util.Log;

public class SpaceNote {
    private int id;
    private String title;
    private String notes;
    private String m_date;
    private String m_time;
    private static final String TAG = "SPACENOTE";

    public SpaceNote(int id, String title, String notes, String m_date, String m_time)
    {
        this.id = id;
        this.title = title;
        this.notes = notes;
        this.m_date = m_date;
        this.m_time = m_time;
    }

    public void print() {
        Log.d(TAG, id+" "+title+" "+notes+" "+m_date+" "+m_time);
    }

    public int getId() {
        return id;
    }

    public String getM_date() {
        return m_date;
    }

    public String getNotes() {
        return notes;
    }

    public String getM_time() {
        return m_time;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setM_date(String m_date) {
        this.m_date = m_date;
    }

    public void setM_time(String m_time) {
        this.m_time = m_time;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
