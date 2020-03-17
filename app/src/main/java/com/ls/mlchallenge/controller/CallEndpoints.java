package com.ls.mlchallenge.controller;

import com.ls.mlchallenge.constants.ConectionConstants;
import com.ls.mlchallenge.model.Bank;
import com.ls.mlchallenge.model.CreditCard;
import com.ls.mlchallenge.model.Installment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CallEndpoints {
    @GET(ConectionConstants.PAYMENT_METHODS)
    Call<List<CreditCard>> getCreditCards(@Query(ConectionConstants.PUBLIC_KEY) String public_key);

    @GET(ConectionConstants.PAYMENT_METHODS + "/" + ConectionConstants.CARD_ISSUERS)
    Call<List<Bank>> getBanks(@Query(ConectionConstants.PUBLIC_KEY) String public_key,
                              @Query(ConectionConstants.PAYMENT_METHOD_ID) String payment_method_id);

    @GET(ConectionConstants.PAYMENT_METHODS + "/" + ConectionConstants.INSTALLMENTS)
    Call<List<Installment>> getInstallments(@Query(ConectionConstants.PUBLIC_KEY) String public_key,
                                            @Query(ConectionConstants.AMOUNT) int amount,
                                            @Query(ConectionConstants.PAYMENT_METHOD_ID) String payment_method_id,
                                            @Query(ConectionConstants.ISSUER_ID) String issuer_id);
}
