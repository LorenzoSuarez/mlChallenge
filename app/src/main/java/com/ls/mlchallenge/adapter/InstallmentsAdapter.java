package com.ls.mlchallenge.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.ls.mlchallenge.R;
import com.ls.mlchallenge.constants.ConectionConstants;
import com.ls.mlchallenge.model.Installment;

import java.util.ArrayList;


public class InstallmentsAdapter extends BaseExpandableListAdapter {
    private Context context;

    public ArrayList<Installment> getItems() {
        return items;
    }

    private ArrayList<Installment> items;
    private TextView expandibleTitle, recommended_message, ctf, tea;
    private String[] tea_ctf;

    public InstallmentsAdapter(Context context, ArrayList<Installment> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return items.get(listPosition).payer_costs.get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getChildView(final int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_installment_det, null);
        }

        recommended_message = convertView.findViewById(R.id.recommended_message);
        tea = convertView.findViewById(R.id.tea);
        ctf = convertView.findViewById(R.id.ctf);

        try {
            recommended_message.setText(items.get(listPosition).payer_costs.get(expandedListPosition).recommended_message);

            tea_ctf = items.get(listPosition).payer_costs.get(expandedListPosition).labels.get(0).split("\\|");
            if (tea_ctf[0].equals(ConectionConstants.RECOMMENDED_INSTALLMENT))
                tea_ctf = items.get(listPosition).payer_costs.get(expandedListPosition).labels.get(1).split("\\|");

            if (tea_ctf.length > 1) {
                ctf.setText(tea_ctf[0]);
                tea.setText(tea_ctf[1]);
            }
        } catch (Exception ignored) { }

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return items.get(listPosition).payer_costs.size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return items.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.items.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(final int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_installment_title, null);

            ExpandableListView mExpandableListView = (ExpandableListView) parent;
            mExpandableListView.expandGroup(listPosition);
        }

        //Set head:
        expandibleTitle = convertView.findViewById(R.id.card_title);
        expandibleTitle.setTypeface(null, Typeface.BOLD);
        expandibleTitle.setText(items.get(listPosition).issuer.name);
        expandibleTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
