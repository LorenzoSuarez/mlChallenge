package com.ls.mlchallenge.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Slide;

import com.ls.mlchallenge.R;
import com.ls.mlchallenge.adapter.BanksAdapter;
import com.ls.mlchallenge.listeners.OnItemClickListener;
import com.ls.mlchallenge.model.Bank;

import java.util.ArrayList;

public class FragmentBank extends Fragment  {
    private OnItemClickListener onBankClickListener;
    private View view;
    private BanksAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<Bank> banks = new ArrayList<>();
    public static String BANKS = "banks";

    @SuppressLint("RtlHardcoded")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.setEnterTransition(new Slide(Gravity.RIGHT));
        this.setExitTransition(new Slide(Gravity.LEFT));

        view = inflater.inflate(R.layout.fragment_bank, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewBanks);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(null);

        Bundle objectReceived = getArguments();
        if (objectReceived != null) {
            banks = (ArrayList<Bank>) objectReceived.getSerializable(BANKS);

            adapter = new BanksAdapter(banks);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBankClickListener.onBankClick(adapter.getItems().get(recyclerView.getChildAdapterPosition(v)));
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onBankClickListener = (OnItemClickListener) context;
    }
}
