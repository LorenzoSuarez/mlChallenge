package com.ls.mlchallenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Installment implements Serializable {
    @SerializedName("issuer")
    @Expose
    public CreditCard issuer;

    @SerializedName("payer_costs")
    @Expose
    public ArrayList<PayerCosts> payer_costs;

}
