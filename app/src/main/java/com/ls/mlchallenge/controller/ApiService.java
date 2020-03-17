package com.ls.mlchallenge.controller;

import android.content.Context;
import com.ls.mlchallenge.R;
import com.ls.mlchallenge.constants.ConectionConstants;
import com.ls.mlchallenge.listeners.BankListener;
import com.ls.mlchallenge.listeners.CreditCardsListener;
import com.ls.mlchallenge.listeners.InstallmentListener;
import com.ls.mlchallenge.model.Bank;
import com.ls.mlchallenge.model.CreditCard;
import com.ls.mlchallenge.model.Installment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private static ApiService singleInstance;
    private CreditCardsListener creditCardsListener;
    private BankListener banksListener;
    private InstallmentListener installmentListener;
    private LoadingInterface loadingInterface;

    private List<CreditCard> listCreditCards = new ArrayList<>();
    private List<Bank> listBanks;
    private List<Installment> listInstallments;

    private Context context;
    private CallEndpoints callEndpoints;

    private ApiService(Context context) {
        this.context = context;
        this.loadingInterface = (LoadingInterface) context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConectionConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        callEndpoints = retrofit.create(CallEndpoints.class);
    }

    public static ApiService getInstance(Context context) {
        if (singleInstance == null) {
            singleInstance = new ApiService(context);
        }

        return singleInstance;
    }

    public interface LoadingInterface {
        void showLoading();

        void hideLoading();
    }

    public void setCreditCardsListener(CreditCardsListener creditCardsListener) {
        this.creditCardsListener = creditCardsListener;
    }

    public void setBanksListener(BankListener banksListener) {
        this.banksListener = banksListener;
    }

    public void setInstallmentListener(InstallmentListener installmentListener) {
        this.installmentListener = installmentListener;
    }

    public void getCreditCards() {
        loadingInterface.showLoading();
        Call<List<CreditCard>> callCreditCards = callEndpoints.getCreditCards(context.getString(R.string.public_key));
        callCreditCards.enqueue(new Callback<List<CreditCard>>() {
            @Override
            public void onResponse(Call<List<CreditCard>> call, Response<List<CreditCard>> response) {
                if (!response.isSuccessful()) {
                    creditCardsListener.onResponseArrive(String.valueOf(response.code()), null);
                    loadingInterface.hideLoading();
                    return;
                }

                listCreditCards.clear();

                for (CreditCard cc : response.body()) {
                    if (cc.payment_type_id.equals(ConectionConstants.CREDIT_CARD))
                        listCreditCards.add(cc);
                }

                if (listCreditCards.size() > 0)
                    creditCardsListener.onResponseArrive("", listCreditCards);
                else
                    creditCardsListener.onResponseArrive(context.getString(R.string.unexpected_error), null);

                loadingInterface.hideLoading();

            }

            @Override
            public void onFailure(Call<List<CreditCard>> call, Throwable t) {
                creditCardsListener.onResponseArrive(t.getMessage(), null);
                loadingInterface.hideLoading();
            }
        });

    }

    public void getBanks(String idCreditCard) {
        loadingInterface.showLoading();
        Call<List<Bank>> callBanks = callEndpoints.getBanks(context.getString(R.string.public_key), idCreditCard);

        callBanks.enqueue(new Callback<List<Bank>>() {
            @Override
            public void onResponse(Call<List<Bank>> call, Response<List<Bank>> response) {
                if (!response.isSuccessful()) {
                    banksListener.onResponseArrive(String.valueOf(response.code()), null);
                    loadingInterface.hideLoading();
                    return;
                }

                listBanks = response.body();
                banksListener.onResponseArrive("", listBanks);
                loadingInterface.hideLoading();
            }

            @Override
            public void onFailure(Call<List<Bank>> call, Throwable t) {
                banksListener.onResponseArrive(t.getMessage(), null);
                loadingInterface.hideLoading();
            }
        });
    }

    public void getInstallments(int amount, String idCreditCard, String idBank) {
        loadingInterface.showLoading();
        Call<List<Installment>> callInstallments = callEndpoints.getInstallments(context.getString(R.string.public_key), amount, idCreditCard, idBank);

        callInstallments.enqueue(new Callback<List<Installment>>() {
            @Override
            public void onResponse(Call<List<Installment>> call, Response<List<Installment>> response) {
                if (!response.isSuccessful()) {
                    installmentListener.onResponseArrive(String.valueOf(response.code()), null);
                    loadingInterface.hideLoading();
                    return;
                }

                listInstallments = response.body();
                installmentListener.onResponseArrive("", listInstallments);
                loadingInterface.hideLoading();
            }

            @Override
            public void onFailure(Call<List<Installment>> call, Throwable t) {
                installmentListener.onResponseArrive(t.getMessage(), null);
                loadingInterface.hideLoading();
            }

        });
    }

}
