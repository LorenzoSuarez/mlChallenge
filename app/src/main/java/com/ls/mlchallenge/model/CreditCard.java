package com.ls.mlchallenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CreditCard implements Serializable {
    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("payment_type_id")
    @Expose
    public String payment_type_id;

    @SerializedName("secure_thumbnail")
    @Expose
    public String secure_thumbnail;

    @SerializedName("max_allowed_amount")
    @Expose
    public int max_allowed_amount;

}
