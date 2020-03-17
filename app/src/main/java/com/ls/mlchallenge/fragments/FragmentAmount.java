package com.ls.mlchallenge.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.transition.Slide;

import com.ls.mlchallenge.R;
import com.ls.mlchallenge.listeners.OnItemClickListener;

public class FragmentAmount extends Fragment {
    public View view;
    private OnItemClickListener onCreditCardClickListener;
    private String amount;

    private EditText input_amount;

    @SuppressLint("RtlHardcoded")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.setExitTransition(new Slide(Gravity.LEFT));

        view = inflater.inflate(R.layout.fragment_amount, container, false);

        input_amount = view.findViewById(R.id.input_amount);

        view.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = input_amount.getText().toString();
                onCreditCardClickListener.onAmountConfirm(amount);
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
