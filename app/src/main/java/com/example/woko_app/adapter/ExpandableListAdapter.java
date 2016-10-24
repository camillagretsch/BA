package com.example.woko_app.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.example.woko_app.R;
import com.example.woko_app.models.AP;

import java.util.ArrayList;

/**
 * Created by camillagretsch on 31.08.16.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private ArrayList<Object> childitems;
    private LayoutInflater inflater;
    private ArrayList<String> parentItems, child;
    private int parentLayout;
    private int childLayout;
    private Typeface fontawesome;
    private Typeface fonticon;
    private AP ap;

    public ExpandableListAdapter(ArrayList<String> parents, ArrayList<Object> childern, int parentLayout, int childLayout, Typeface fontawesome, Typeface fonticon) {
        this.parentItems = parents;
        this.childitems = childern;
        this.parentLayout = parentLayout;
        this.childLayout = childLayout;
        this.fontawesome = fontawesome;
        this.fonticon = fonticon;
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }

    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        child = (ArrayList<String>) childitems.get(groupPosition);
        TextView textView = null;

        if (convertView == null) {
            convertView = inflater.inflate(childLayout, null);
        }

        textView = (TextView) convertView.findViewById(R.id.child_text);
        textView.setText(child.get(childPosition));
        textView.setTypeface(fonticon);

        return convertView;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(parentLayout, null);
        }

        ((CheckedTextView) convertView).setText(parentItems.get(groupPosition));
        ((CheckedTextView) convertView).setTypeface(fonticon);
        ((CheckedTextView) convertView).setChecked(isExpanded);

        if (isExpanded) {
            convertView.setBackgroundColor(parent.getResources().getColor(R.color.selector));
        } else {
            convertView.setBackgroundColor(parent.getResources().getColor(R.color.grey_dark));
        }
        return convertView;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    public int getChildrenCount(int groupPosition) {
        return ((ArrayList<String>) childitems.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition) {
        return null;
    }

    public int getGroupCount() {
        return parentItems.size();
    }

    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    public long getGroupId(int groupPosition) {
        return 0;
    }

    public boolean hasStableIds() {
        return false;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
