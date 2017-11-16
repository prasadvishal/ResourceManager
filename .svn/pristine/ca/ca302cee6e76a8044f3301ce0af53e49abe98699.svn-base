package com.mindfiresolutions.resourcemanager.resource;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mindfiresolutions.resourcemanager.R;

import java.util.List;

/**
 * Created by Vishal Prasad on 5/18/2017.
 */

public class CustomSpinnerAdapter extends ArrayAdapter<RowItem> {

        LayoutInflater flater;

public CustomSpinnerAdapter(Activity context, int resouceId, int textviewId, List<RowItem> list){

        super(context,resouceId,textviewId, list);
        flater = context.getLayoutInflater();
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {

        RowItem rowItem = getItem(position);

        View rowview = flater.inflate(R.layout.customized_spinner_dropdown,null,true);

        TextView txtTitle = (TextView) rowview.findViewById(R.id.title);
        txtTitle.setText(rowItem.getTitle());

        if(position==0)
        {
            txtTitle.setTextColor(Color.GRAY);
        }

        return rowview;
        }
        }