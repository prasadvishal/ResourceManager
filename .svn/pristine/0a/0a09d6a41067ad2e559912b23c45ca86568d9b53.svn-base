package com.mindfiresolutions.resourcemanager.resource;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindfiresolutions.resourcemanager.R;

import java.util.ArrayList;
import java.util.HashMap;

import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUESTED_BY;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUESTED_ON;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUESTED_TILL;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.RESOURCE_CATEGORY;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.RESOURCE_NAME;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.RESOURCE_TITLE;

/**
 * Created by Vishal Prasad on 7/10/2017.
 */

public class PendingRequestCVAdapter extends RecyclerView.Adapter<PendingRequestCVAdapter.MyViewHolder> {
    public ArrayList<HashMap<String, String>> myValues = new ArrayList<>();

    public PendingRequestCVAdapter() {


    }
    public void setViewitemsList(ArrayList<HashMap<String,String>> myViews){
        myValues.clear();
        myValues.addAll(myViews);
    }

    @Override
    public PendingRequestCVAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_request_cardview_item, parent, false);
        return new PendingRequestCVAdapter.MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(PendingRequestCVAdapter.MyViewHolder holder, int position) {
        holder.mRequestTitle.setText(myValues.get(position).get(RESOURCE_TITLE));
        holder.mRequestedBy.setText(myValues.get(position).get(REQUESTED_BY));
        holder.mFromDate.setText(myValues.get(position).get(REQUESTED_ON));
        holder.mResourceName.setText(myValues.get(position).get(RESOURCE_NAME));
        holder.mTillDate.setText(myValues.get(position).get(REQUESTED_TILL));
        if (myValues.get(position).get(RESOURCE_CATEGORY).equals("Software")) {
            holder.myRequestResourceIcon.setImageResource(R.drawable.software_resource_icon);
        } else if (myValues.get(position).get(RESOURCE_CATEGORY).equals("Hardware")) {
            holder.myRequestResourceIcon.setImageResource(R.drawable.hardware_resource_icon);
        } else if (myValues.get(position).get(RESOURCE_CATEGORY).equals("Shared Resource")) {
            holder.myRequestResourceIcon.setImageResource(R.drawable.shared_resource_icon);
        }

    }


    @Override
    public int getItemCount() {
        return myValues.size();
    }

    public HashMap<String,String> getMyViewsListRes(int position) {
        return (myValues.get(position));
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mRequestTitle, mRequestedBy, mFromDate, mTillDate, mResourceName;
        private ImageView myRequestResourceIcon;

        MyViewHolder(View itemView) {
            super(itemView);
            mRequestTitle = (TextView) itemView.findViewById(R.id.pending_req_tittle);
            mResourceName = (TextView) itemView.findViewById(R.id.pending_req_resource_name);
            mRequestedBy = (TextView) itemView.findViewById(R.id.pending_req_requested_by);
            mFromDate = (TextView) itemView.findViewById(R.id.pending_req_from_date);
            mTillDate = (TextView) itemView.findViewById(R.id.pending_req_till_date);
            myRequestResourceIcon = (ImageView) itemView.findViewById(R.id.pending_req_res_type_icon);
        }
    }
}