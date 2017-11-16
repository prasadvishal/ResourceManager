package com.mindfiresolutions.resourcemanager.resource;

/**
 * Created by Vishal Prasad on 5/4/2017.
 */

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.mindfiresolutions.resourcemanager.R;

import static com.mindfiresolutions.resourcemanager.utility.Constants.LIST_VIEW_BLUE;

public class MultiColorListViewAdapter extends SimpleAdapter {
    // private int[] colors = new int[]{0xffff00ff, 0x308BBCD8};

    public MultiColorListViewAdapter(Context context, List<HashMap<String, String>> items, int resource, String[] from, int[] to) {
        super(context, items, resource, from, to);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        int bgColor = (position % 2 == 0) ? Color.WHITE : LIST_VIEW_BLUE;
        view.setBackgroundColor(bgColor);
        return view;
    }
}