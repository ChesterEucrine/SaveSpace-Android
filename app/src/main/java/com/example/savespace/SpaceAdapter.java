package com.example.savespace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SpaceAdapter extends ArrayAdapter<SpaceNote> {

    private Context context;
    private ArrayList<SpaceNote> spaceNotes;


    public SpaceAdapter (ArrayList<SpaceNote> sN, Context context) {
        super(context, 0, sN);
        spaceNotes = sN;
        this.context = context;
    }

    public void onClick(View v)
    {
        Toast.makeText(context, "Some item clicked!!!", Toast.LENGTH_SHORT).show();
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
    }*/

    public void updateList(ArrayList<SpaceNote> spaceNotes) {
        this.spaceNotes.clear();
        this.spaceNotes.addAll(spaceNotes);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        SpaceNote currentItem = spaceNotes.get(position);
        View listItem = convertView;
        if (listItem == null)
        {
            listItem = LayoutInflater.from(context).inflate(R.layout.note_list, parent, false);
        }

        TextView title = (TextView) listItem.findViewById(R.id.main_title);
        title.setText(currentItem.getTitle());

        TextView notes = listItem.findViewById(R.id.main_notes);
        notes.setText(currentItem.getNotes());

        TextView date = listItem.findViewById(R.id.main_modified);
        date.setText(currentItem.getM_date());

        TextView id = listItem.findViewById(R.id.main_id);
        id.setText(Integer.toString(currentItem.getId()));

        return listItem;
        /*ViewHolder viewHolder;

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
        }*/

        /*Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);/
        lastPosition = position;

        viewHolder.title.setText(spaceNote.getTitle());
        viewHolder.notes.setText(spaceNote.getNotes());

        //Modify below to print the time if the modified date is the same as the day the app is being viewed
        viewHolder.modified.setText(spaceNote.getM_date());
        return convertView;*/
    }
}
