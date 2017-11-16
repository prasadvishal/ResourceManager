package com.mindfiresolutions.resourcemanager.resource;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.utility.Constants;

import java.util.ArrayList;
import java.util.HashMap;

import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.ASSIGNED_FROM_DATE;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.ASSIGNED_TO;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.ASSIGNED_TO_DATE;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.AVAILABILITY;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.MAC_ID;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.MODEL;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUESTED_RESOURCE;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.RESOURCE_CATEGORY;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.RESOURCE_NAME;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.RESOURCE_TITLE;

/**
 * Created by Vishal Prasad on 5/23/2017.
 */

public class HardwareTypeSummaryRVAdapter extends RecyclerView.Adapter<HardwareTypeSummaryRVAdapter.MyViewHolder> {
    public ArrayList<HashMap<String, String>> myValues;

    public HardwareTypeSummaryRVAdapter(ArrayList<HashMap<String, String>> myValues) {
        this.myValues = myValues;
    }

    @Override
    public HardwareTypeSummaryRVAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.hardware_type_summary_cv, parent, false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(HardwareTypeSummaryRVAdapter.MyViewHolder holder, int position) {
        holder.hardwareTitle.setText(myValues.get(position).get(RESOURCE_NAME));
        holder.macId.setText(myValues.get(position).get(MAC_ID));
        holder.allocatedTo.setText(myValues.get(position).get(ASSIGNED_TO));
        if(myValues.get(position).get(AVAILABILITY).equals("Available")) {
            holder.resAvailablity.setBackgroundColor(Constants.GREEN);
            holder.assignedDetails.setVisibility(View.GONE);
            holder.allocatedTo.setVisibility(View.VISIBLE);
            holder.allocatedTo.setText(R.string.status_available);
            holder.allocatedTo.setTextColor(Constants.GREEN);
        }
        else {
            holder.resAvailablity.setBackgroundColor(Constants.RED);
            holder.assignedDetails.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public int getItemCount() {
        return myValues.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView  hardwareTitle, allocatedTo, macId;
        private View resAvailablity;
        private ImageView myRequestResourceIcon;
        private LinearLayout assignedDetails;

        public MyViewHolder(View itemView) {
            super(itemView);
            hardwareTitle = (TextView) itemView.findViewById(R.id.hrdwr_type_summ_hardware_tittle);
            allocatedTo = (TextView) itemView.findViewById(R.id.hrdwr_type_summ_assigned_to);
            macId = (TextView) itemView.findViewById(R.id.hrdwr_type_summ_srvc_tag);
            resAvailablity =  itemView.findViewById(R.id.resource_availability_indicator);
            assignedDetails = (LinearLayout)itemView.findViewById(R.id.assigned_details);


        }
    }
}
