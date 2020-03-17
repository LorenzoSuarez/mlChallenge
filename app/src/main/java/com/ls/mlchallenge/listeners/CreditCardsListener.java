package com.ls.mlchallenge.listeners;

import com.ls.mlchallenge.model.CreditCard;

import java.util.List;

public interface CreditCardsListener {
    void onResponseArrive(String error, List<CreditCard> creditCards);
}
