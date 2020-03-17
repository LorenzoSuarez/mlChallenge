package com.ls.mlchallenge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.ls.mlchallenge.controller.ApiService;
import com.ls.mlchallenge.fragments.FragmentAmount;
import com.ls.mlchallenge.fragments.FragmentBank;
import com.ls.mlchallenge.fragments.FragmentCreditCard;
import com.ls.mlchallenge.fragments.FragmentInstallments;
import com.ls.mlchallenge.fragments.FragmentResume;
import com.ls.mlchallenge.listeners.BankListener;
import com.ls.mlchallenge.listeners.CreditCardsListener;
import com.ls.mlchallenge.listeners.InstallmentListener;
import com.ls.mlchallenge.listeners.OnItemClickListener;
import com.ls.mlchallenge.model.Bank;
import com.ls.mlchallenge.model.CreditCard;
import com.ls.mlchallenge.model.GlobalData;
import com.ls.mlchallenge.model.Installment;
import com.ls.mlchallenge.model.PayerCosts;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static android.util.Log.ASSERT;
import static android.util.Log.ERROR;
import static android.util.Log.WARN;

public class MainActivity extends AppCompatActivity implements
        OnItemClickListener,
        ApiService.LoadingInterface,
        FragmentResume.IFinish {

    public static FragmentManager fragmentManager;

    private StateProgressBar stateProgressBar;
    private Toolbar toolBar;
    private ApiService apiService;
    private GlobalData globalData;
    private Bundle bundle;
    private AtomicInteger currentFragment = new AtomicInteger(0);

    private static androidx.fragment.app.Fragment[] PAGES;
    private static String[] PAGE_TITLES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = getSupportFragmentManager();
        setContentView(R.layout.activity_main);

        PAGES = new androidx.fragment.app.Fragment[]{
                new FragmentAmount(),
                new FragmentCreditCard(),
                new FragmentBank(),
                new FragmentInstallments(),
                new FragmentResume(),
        };

        PAGE_TITLES = new String[]{
                getString(R.string.title_amount),
                getString(R.string.title_credit_cards),
                getString(R.string.title_banks),
                getString(R.string.title_installments),
                getString(R.string.title_resume)
        };

        stateProgressBar = findViewById(R.id.state_progress_bar);
        toolBar = findViewById(R.id.actionbar);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = null;
                GoToFragment(currentFragment.decrementAndGet());
            }
        });

        apiService = ApiService.getInstance(this);
        globalData = new GlobalData();
        GoToFragment(currentFragment.get());
    }

    private void GoToFragment(int fragmentNumber) {

        if (bundle != null) PAGES[fragmentNumber].setArguments(bundle);

        fragmentManager.beginTransaction().replace(R.id.fragment_base, PAGES[fragmentNumber]).commit();

        switch (fragmentNumber) {
            case 0:
                toolBar.setNavigationIcon(null);
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                break;
            case 1:
                if (toolBar.getNavigationIcon() == null)
                    toolBar.setNavigationIcon(getDrawable(R.drawable.ic_back_arrow));
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                break;
            case 2:
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                break;
            case 3:
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                stateProgressBar.setVisibility(View.VISIBLE);
                break;
            case 4:
                stateProgressBar.setVisibility(View.GONE);
                break;
        }

        ChangeTitle(PAGE_TITLES[fragmentNumber]);

    }

    private void GetCreditCards() {
        apiService.getCreditCards();
        apiService.setCreditCardsListener(new CreditCardsListener() {
            @Override
            public void onResponseArrive(String error, List<CreditCard> creditCards) {
                if (!error.isEmpty()) {
                    ShowMessages(error, ERROR);
                    return;
                }
                bundle = new Bundle();
                bundle.putSerializable(FragmentCreditCard.CREDIT_CARDS, (Serializable) creditCards);

                GoToFragment(currentFragment.incrementAndGet());
            }
        });
    }

    private void GetBanks(String idCreditCard) {
        apiService.getBanks(idCreditCard);
        apiService.setBanksListener(new BankListener() {
            @Override
            public void onResponseArrive(String error, List<Bank> banks) {
                if (!error.isEmpty()) {
                    ShowMessages(error, ERROR);
                    return;
                }

                if (banks.size() <= 0) {
                    ShowMessages(getString(R.string.error_bak), WARN);
                } else {
                    bundle = new Bundle();
                    bundle.putSerializable(FragmentBank.BANKS, (Serializable) banks);

                    GoToFragment(currentFragment.incrementAndGet());
                }

            }
        });
    }

    private void GetInstallments(int amount, String idCreditCard, String idBank) {
        apiService.getInstallments(amount, idCreditCard, idBank);
        apiService.setInstallmentListener(new InstallmentListener() {
            @Override
            public void onResponseArrive(String error, List<Installment> installments) {
                if (!error.isEmpty()) {
                    ShowMessages(error, ERROR);
                    return;
                }

                bundle = new Bundle();
                bundle.putSerializable(FragmentInstallments.INSTALLMENTS, (Serializable) installments);

                GoToFragment(currentFragment.incrementAndGet());
            }
        });
    }

    private void ChangeTitle(String title) {
        ((TextView) findViewById(R.id.toolbar_title)).setText(title);
    }

    private void ShowMessages(String msg, int type) {
        switch (type) {
            case ERROR:
                Snackbar.make(findViewById(R.id.fragment_base), msg, Snackbar.LENGTH_LONG).setBackgroundTint(getResources().getColor(R.color.error)).show();
                break;
            case WARN:
                Snackbar.make(findViewById(R.id.fragment_base), msg, Snackbar.LENGTH_LONG).setBackgroundTint(getResources().getColor(R.color.warning)).show();
                break;
            case ASSERT:
                Snackbar.make(findViewById(R.id.fragment_base), msg, Snackbar.LENGTH_LONG).setBackgroundTint(getResources().getColor(R.color.success)).show();
                break;
        }

    }

    @Override
    public void onAmountConfirm(String amount) {
        if (amount.isEmpty() || Integer.parseInt(amount) <= 0) {
            ShowMessages(getString(R.string.error_amount), ERROR);
            return;
        }
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        globalData.setAmount(Integer.parseInt(amount));
        GetCreditCards();
    }

    @Override
    public void onCreditCardClick(CreditCard creditCard) {
        if (globalData.getAmount() > creditCard.max_allowed_amount) {
            ShowMessages(getString(R.string.error_excessive_amount), ERROR);
        } else {
            globalData.setCreditCard(creditCard);
            GetBanks(creditCard.id);
        }
    }

    @Override
    public void onBankClick(Bank bank) {
        globalData.setBank(bank);
        GetInstallments(globalData.getAmount(), globalData.getCreditCard().id, globalData.getBank().id);
    }

    @Override
    public void onInstallmentClick(PayerCosts payerCosts) {
        globalData.setPayerCosts(payerCosts);

        if (payerCosts != null) {
            bundle = new Bundle();
            bundle.putSerializable(FragmentResume.GLOBAL_DATA, globalData);
        }

        GoToFragment(currentFragment.incrementAndGet());
    }

    @Override
    public void showLoading() {
        findViewById(R.id.loading).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        findViewById(R.id.loading).setVisibility(View.GONE);
    }

    @Override
    public void finishFragments() {
        startActivity(new Intent(MainActivity.this, FinishAcivity.class));
        finish();
    }
}
