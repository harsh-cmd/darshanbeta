package com.example.darshanbeta.Fragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.darshanbeta.R;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    Context context;
    List<String> listDataHeader;
    HashMap<String, List<String>>  listDataItem;


    ExpandableListAdapter(Context context,List<String> listDataHeader, HashMap<String, List<String>>  listDataItem)
    {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataItem = listDataItem;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listDataItem.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listDataItem.get(listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headTitle  = (String)getGroup(groupPosition);
        if(convertView==null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_list_group,null);
        }
        TextView header = convertView.findViewById(R.id.textViewHeader);
        header.setText(headTitle);
        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String child = (String)getChild(groupPosition,childPosition);
        if(convertView==null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_list_item,null);
        }
        TextView header = convertView.findViewById(R.id.textViewItem);
        header.setText(child);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
