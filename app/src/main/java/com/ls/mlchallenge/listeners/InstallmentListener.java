package com.ls.mlchallenge.listeners;

import com.ls.mlchallenge.model.Installment;

import java.util.List;

public interface InstallmentListener {
    void onResponseArrive(String error, List<Installment> installments);
}
