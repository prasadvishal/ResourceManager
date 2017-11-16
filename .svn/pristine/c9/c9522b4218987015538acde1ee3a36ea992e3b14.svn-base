package com.mindfiresolutions.resourcemanager.resource;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.utility.Constants;

import java.util.ArrayList;
import java.util.HashMap;

import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.ASSIGNED_BY;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.ASSIGNED_FROM_DATE;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.ASSIGNED_TO;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.ASSIGNED_TO_DATE;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUESTED_RESOURCE;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.RESOURCE_CATEGORY;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.RESOURCE_TITLE;

/**
 * Created by Vishal Prasad on 5/19/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    public ArrayList<HashMap<String,String>> myValues;
    public RecyclerViewAdapter(ArrayList<HashMap<String,String>> myValues){
        this.myValues= myValues;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mRequestTitle.setText(myValues.get(position).get(RESOURCE_TITLE));
        holder.mResourceType.setText(myValues.get(position).get(RESOURCE_CATEGORY));
        holder.mRequestedTo.setText(myValues.get(position).get(ASSIGNED_BY));
        holder.mFromDate.setText(myValues.get(position).get(ASSIGNED_FROM_DATE));
        holder.mResourceCategory.setText(myValues.get(position).get(REQUESTED_RESOURCE));
        holder.mTillDate.setText(myValues.get(position).get(ASSIGNED_TO_DATE));
        if(myValues.get(position).get(RESOURCE_CATEGORY).equals("Software"))
        {
            holder.myRequestResourceIcon.setImageResource(R.drawable.software_resource_icon);
        }
        else if(myValues.get(position).get(RESOURCE_CATEGORY).equals("Hardware"))
        {
            holder.myRequestResourceIcon.setImageResource(R.drawable.hardware_resource_icon);
        }
        else if(myValues.get(position).get(RESOURCE_CATEGORY).equals("Shared Resource"))
        {
            holder.myRequestResourceIcon.setImageResource(R.drawable.hardware_resource_icon);
        }

    }


    @Override
    public int getItemCount() {
        return myValues.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mResourceType,mRequestTitle,mRequestedTo,mFromDate,mTillDate, mResourceCategory;
        private ImageView myRequestResourceIcon;
        MyViewHolder(View itemView) {
            super(itemView);
            mRequestTitle = (TextView)itemView.findViewById(R.id.user_my_req_tittle);
            mResourceCategory = (TextView)itemView.findViewById(R.id.user_my_req_category);
            mResourceType = (TextView)itemView.findViewById(R.id.user_my_req_res_type_lable);
            mRequestedTo = (TextView)itemView.findViewById(R.id.user_my_req_assigned_to);
            mFromDate = (TextView)itemView.findViewById(R.id.user_my_req_from_date);
            mTillDate = (TextView)itemView.findViewById(R.id.user_my_req_to_date);
            myRequestResourceIcon = (ImageView)itemView.findViewById(R.id.user_my_req_res_type_icon);
        }
    }
}