package com.ls.mlchallenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Bank implements Serializable {
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

    @SerializedName("processing_mode")
    @Expose
    public String processing_mode;

    @SerializedName("merchant_account_id")
    @Expose
    public String merchant_account_id;

}
