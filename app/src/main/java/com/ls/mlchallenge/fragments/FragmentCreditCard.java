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
import com.ls.mlchallenge.adapter.CreditCardsAdapter;
import com.ls.mlchallenge.listeners.OnItemClickListener;
import com.ls.mlchallenge.model.CreditCard;

import java.util.ArrayList;

public class FragmentCreditCard extends Fragment {
    private OnItemClickListener onCreditCardClickListener;
    private View view;
    private CreditCardsAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<CreditCard> creditCards = new ArrayList<>();
    public static String CREDIT_CARDS = "credit_cards";

    @SuppressLint("RtlHardcoded")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_credit_card, container, false);

        this.setExitTransition(new Slide(Gravity.LEFT));

        recyclerView = view.findViewById(R.id.recyclerViewCreditCards);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Bundle objectReceived = getArguments();
        if (objectReceived != null){
            creditCards = (ArrayList<CreditCard>) objectReceived.getSerializable(CREDIT_CARDS);

            adapter = new CreditCardsAdapter(creditCards);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreditCardClickListener.onCreditCardClick(adapter.getItems().get(recyclerView.getChildAdapterPosition(v)));
            }
        });

        return view;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onCreditCardClickListener = (OnItemClickListener) context;
    }


}
