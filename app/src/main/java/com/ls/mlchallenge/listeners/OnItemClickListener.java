package com.ls.mlchallenge.listeners;

import com.ls.mlchallenge.model.Bank;
import com.ls.mlchallenge.model.CreditCard;
import com.ls.mlchallenge.model.PayerCosts;

public interface OnItemClickListener {
    void onAmountConfirm(String amount);
    void onCreditCardClick(CreditCard creditCard);
    void onBankClick(Bank bank);
    void onInstallmentClick(PayerCosts payerCosts);
}
