package com.example.budgetthing.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.budgetthing.database.ExpenseRepository;
import com.example.budgetthing.models.Expense;

import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {

    private ExpenseRepository repository;
    private LiveData<List<Expense>> allExpenses;

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        repository = new ExpenseRepository(application);
        allExpenses = repository.getAllExpenses();
    }

    public void insert(Expense expense){
        repository.insert(expense);
    }

    public void update(Expense expense){
        repository.update(expense);
    }

    public void delete(Expense expense){
        repository.delete(expense);
    }

    public LiveData<List<Expense>> getAllExpenses(){
        return allExpenses;
    }
}
