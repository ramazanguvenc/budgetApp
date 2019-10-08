package com.example.budgetthing.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.budgetthing.MainActivity;
import com.example.budgetthing.R;
import com.example.budgetthing.models.Expense;

public class Summary extends Fragment {


    private MainActivity myActivity;
    private TextView t_market, t_rent, t_social, t_others, t_total;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.summary, container, false);
        t_market = v.findViewById(R.id.market_summary_textview);
        t_rent = v.findViewById(R.id.rent_summary_textview);
        t_social = v.findViewById(R.id.socialstuff_summary_textview);
        t_others = v.findViewById(R.id.others_summary_textview);
        t_total = v.findViewById(R.id.total_summary_textview);

        myActivity = (MainActivity) getActivity();

        setTextViews();

        return v;
    }



    private void setTextViews() {
        int market = getCategorySummary("Market");
        int rent = getCategorySummary("Rent");
        int social_stuff = getCategorySummary("Social Stuff");
        int others = getCategorySummary("Others");
        int total = market + rent + social_stuff + others;

        String s_market = "Market: " + market + Expense.CASH_SIGN;
        String s_rent = "Rent: " + rent + Expense.CASH_SIGN;
        String s_socialstuff = "Social Stuff: " + social_stuff + Expense.CASH_SIGN;
        String s_others = "Others: " + others + Expense.CASH_SIGN;
        String s_total = "Total Expenses: " + total + Expense.CASH_SIGN;

        t_market.setText(s_market);
        t_rent.setText(s_rent);
        t_social.setText(s_socialstuff);
        t_others.setText(s_others);
        t_total.setText(s_total);
    }

    private int getCategorySummary(String category) {
        return myActivity.getCategorySummary(category);
    }
}
