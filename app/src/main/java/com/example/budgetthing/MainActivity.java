package com.example.budgetthing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.budgetthing.adapters.SectionsPagerAdapter;
import com.example.budgetthing.models.Expense;
import com.example.budgetthing.viewmodel.ExpenseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    TextView date_title, balance_textView;
    public static final int NEW_EXPENSE_REQUEST = 666;
    public static final String __SUMMARY = "SAVED_TOTAL_SUMMARY";
    public static final String __SUMMARY_MARKET = "SAVED_TOTAL_SUMMARY_MARKET";
    public static final String __SUMMARY_RENT = "SAVED_TOTAL_SUMMARY_RENT";
    public static final String __SUMMARY_SOCIAL_STUFF = "SAVED_TOTAL_SUMMARY_SOCIAL_STUFF";
    public static final String __SUMMARY_OTHERS = "SAVED_TOTAL_SUMMARY_OTHERS";


    public static String summary_tag="";



    private ExpenseViewModel expenseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expenseViewModel = ViewModelProviders.of(this).get(ExpenseViewModel.class);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);




        final Intent intent = new Intent(this, NewExpenseActivity.class);

        fab.setOnClickListener(view -> startActivityForResult(intent, NEW_EXPENSE_REQUEST));

        setIds();
        setTexts();


    }

    private void setIds() {
        date_title = findViewById(R.id.date_TextView);
        balance_textView = findViewById(R.id.balance_TextView);

    }

    private void setTexts() {
        date_title.setText(new SimpleDateFormat("dd/MM/YYYY", Locale.getDefault()).format(new Date()));

        int summary = getTotalSummary();
        String balance = "Your Economic Outlook: " + summary + Expense.CASH_SIGN;
        balance_textView.setText(balance);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_EXPENSE_REQUEST && resultCode == RESULT_OK){
            String type = data.getStringExtra(NewExpenseActivity.EXTRA_TYPE);
            String total = data.getStringExtra(NewExpenseActivity.EXTRA_TOTAL);
            String date = data.getStringExtra(NewExpenseActivity.EXTRA_DATE);
            String category = data.getStringExtra(NewExpenseActivity.EXTRA_CATEGORY);

            Expense expense = new Expense(type, total, category, date);

            expenseViewModel.insert(expense);

            setTotalSummary(expense, true);

        }
    }



    public int getTotalSummary(){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        int defaultValue = 0;
        return sharedPref.getInt(__SUMMARY, defaultValue);
    }

    public int getCategorySummary(String category){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        int defaultValue = 0;

        switch (category){
            case "Market":
                return sharedPref.getInt(__SUMMARY_MARKET, defaultValue);
            case "Rent":
                return sharedPref.getInt(__SUMMARY_RENT, defaultValue);
            case "Social Stuff":
                return sharedPref.getInt(__SUMMARY_SOCIAL_STUFF, defaultValue);
            case "Others":
                return sharedPref.getInt(__SUMMARY_OTHERS, defaultValue);
        }

        return defaultValue;
    }

    public void setTotalSummary(Expense expense, boolean flag){
        int total = getTotalSummary();

        if(flag){
            if(expense.type.equals("paid"))
                total = total - Integer.parseInt(expense.total);
            else
                total += Integer.parseInt(expense.total);
        }
        else{
            if(expense.type.equals("paid"))
                total = total + Integer.parseInt(expense.total);
            else
                total = total - Integer.parseInt(expense.total);
        }

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(__SUMMARY, total);
        editor.apply();

        setCategorySummary(expense, flag);

        setTexts();

        Fragment summaryFragment = getSupportFragmentManager().findFragmentByTag(summary_tag);
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(summaryFragment);
        ft.attach(summaryFragment);
        ft.commit();



    }

    private void setCategorySummary(Expense expense, boolean flag) {
        int total = getCategorySummary(expense.category);

        if(flag)
            total += Integer.parseInt(expense.total);
        else
            total = total - Integer.parseInt(expense.total);

        String category = "ERROR";
        switch (expense.category){
            case "Market":
                category = __SUMMARY_MARKET;
                break;
            case "Rent":
                category = __SUMMARY_RENT;
                break;
            case "Social Stuff":
                category = __SUMMARY_SOCIAL_STUFF;
                break;
            case "Others":
                category = __SUMMARY_OTHERS;
                break;
        }

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(category, total);
        editor.apply();

    }
}