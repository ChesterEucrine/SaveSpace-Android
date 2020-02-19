package com.example.savespace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.savespace.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class SpaceAdapter extends ArrayAdapter<SpaceNote> {

    private Context mContext;
    private ArrayList<SpaceNote> spaceNotes;


    public SpaceAdapter (ArrayList<SpaceNote> sN, Context context, int resource) {
        super(context, resource);
        spaceNotes = sN;
        mContext = context;
    }

    public void onClick(View v)
    {
        Toast.makeText(mContext, "Some item clicked!!!", Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    /*
    private static class ViewHolder
    {
        TextView title;
        TextView notes;
        TextView modified;
    }

    public void onClick(View v)
    {
        int position = (Integer) v.getTag();
        Object spaceObject = getItem(position);
        SpaceNote spaceNote = (SpaceNote) spaceObject;

        switch (v.getId())
        {
            case R.id.main_title:
                Snackbar.make(v, "Modified date "+ spaceNote.getM_date(), Snackbar.LENGTH_LONG).setAction("No action", null).show();
            break;
        }
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        SpaceNote spaceNote = getItem(position);
        ViewHolder viewHolder;

        final View result;
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.note_list, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.main_title);
            viewHolder.notes = (TextView) convertView.findViewById(R.id.main_notes);
            viewHolder.modified = (TextView) convertView.findViewById(R.id.main_modified);

            result = convertView;
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        /*Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);/
        lastPosition = position;

        viewHolder.title.setText(spaceNote.getTitle());
        viewHolder.notes.setText(spaceNote.getNotes());

        //Modify below to print the time if the modified date is the same as the day the app is being viewed
        viewHolder.modified.setText(spaceNote.getM_date());
        return convertView;
    }*/
}
