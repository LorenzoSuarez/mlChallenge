package com.ls.mlchallenge.listeners;

import com.ls.mlchallenge.model.Bank;

import java.util.List;

public interface BankListener {
    void onResponseArrive(String error, List<Bank> banks);
}
