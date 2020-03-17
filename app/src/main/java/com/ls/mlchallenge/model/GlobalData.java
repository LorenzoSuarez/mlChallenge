package com.ls.mlchallenge.model;

import java.io.Serializable;

public class GlobalData implements Serializable {
    private int amount;
    private CreditCard creditCard;
    private Bank bank;

    private PayerCosts payerCosts;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public PayerCosts getPayerCosts() {
        return payerCosts;
    }

    public void setPayerCosts(PayerCosts payerCosts) {
        this.payerCosts = payerCosts;
    }

}
