package com.ls.mlchallenge.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.transition.Slide;

import com.ls.mlchallenge.R;
import com.ls.mlchallenge.model.GlobalData;

public class FragmentResume extends Fragment {

    private IFinish iFinish;
    private View view;
    public static String GLOBAL_DATA = "global_data";
    private GlobalData globalData;

    @SuppressLint({"RtlHardcoded", "SetTextI18n"})
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.setEnterTransition(new Slide(Gravity.RIGHT));
        this.setExitTransition(new Slide(Gravity.LEFT));

        view = inflater.inflate(R.layout.fragment_resume, container, false);

        Bundle objectReceived = getArguments();
        if (objectReceived != null) {
            globalData = (GlobalData) objectReceived.getSerializable(GLOBAL_DATA);

            ((TextView) view.findViewById(R.id.amount)).setText(getString(R.string.currency_symbol) + " " + globalData.getAmount());
            ((TextView) view.findViewById(R.id.credit_card)).setText(globalData.getCreditCard().name);
            ((TextView) view.findViewById(R.id.bank)).setText(globalData.getBank().name);
            ((TextView) view.findViewById(R.id.installments)).setText(globalData.getPayerCosts().recommended_message);
        }

        view.findViewById(R.id.btnFloating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iFinish.finishFragments();
            }
        });

        return view;
    }

    public interface IFinish {
        void finishFragments();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iFinish = (IFinish) context;
    }

}
