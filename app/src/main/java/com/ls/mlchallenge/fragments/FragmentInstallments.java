package com.ls.mlchallenge.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.transition.Slide;

import com.ls.mlchallenge.R;
import com.ls.mlchallenge.adapter.InstallmentsAdapter;
import com.ls.mlchallenge.listeners.OnItemClickListener;
import com.ls.mlchallenge.model.Installment;

import java.util.ArrayList;

public class FragmentInstallments extends Fragment {

    private OnItemClickListener onInstallmentClickListener;
    private View view;
    private InstallmentsAdapter adapter;
    private ExpandableListView expandableListView;
    private ArrayList<Installment> installments = new ArrayList<>();
    public static String INSTALLMENTS = "installments";

    @SuppressLint("RtlHardcoded")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.setEnterTransition(new Slide(Gravity.RIGHT));
        this.setExitTransition(new Slide(Gravity.LEFT));

        view = inflater.inflate(R.layout.fragment_installments, container, false);
        expandableListView = view.findViewById(R.id.recyclerViewInstallments);

        Bundle objectReceived = getArguments();
        if (objectReceived != null) {
            installments = (ArrayList<Installment>) objectReceived.getSerializable(INSTALLMENTS);

            adapter = new InstallmentsAdapter(getContext(), installments);
            expandableListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                view.setClickable(true);
                onInstallmentClickListener.onInstallmentClick(adapter.getItems().get(groupPosition).payer_costs.get(childPosition));

                return false;
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onInstallmentClickListener = (OnItemClickListener) context;
    }

}
