package com.example.budgetthing.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.budgetthing.models.Expense;

import java.util.List;

public class ExpenseRepository {

    private ExpenseDao expenseDao;

    private LiveData<List<Expense>> allExpenses;

    public ExpenseRepository(Application application){
        ExpenseDatabase database = ExpenseDatabase.getInstance(application);
        expenseDao = database.expenseDao();
        allExpenses = expenseDao.getAllExpenses();
    }

    public void insert(Expense expense){
        new ExpenseAsyncTask(expenseDao).execute(new ExpenseWithCommand(Command.INSERT, expense));
    }

    public void update(Expense expense){
        new ExpenseAsyncTask(expenseDao).execute(new ExpenseWithCommand(Command.UPDATE, expense));
    }

    public void delete(Expense expense){
        new ExpenseAsyncTask(expenseDao).execute(new ExpenseWithCommand(Command.DELETE, expense));
    }

    public LiveData<List<Expense>> getAllExpenses(){
        return allExpenses;
    }

    private static class ExpenseAsyncTask extends AsyncTask<ExpenseWithCommand, Void, Void> {

        private ExpenseDao expenseDao;

        private ExpenseAsyncTask(ExpenseDao expenseDao){
            this.expenseDao = expenseDao;
        }

        @Override
        protected Void doInBackground(ExpenseWithCommand... expenseWithCommands) {

            switch (expenseWithCommands[0].type){
                case INSERT:
                    expenseDao.insert(expenseWithCommands[0].expense);
                    break;
                case UPDATE:
                    expenseDao.update(expenseWithCommands[0].expense);
                    break;
                case DELETE:
                    expenseDao.delete(expenseWithCommands[0].expense);
                    break;
            }

            return null;
        }
    }

    private class ExpenseWithCommand{
        Command type;
        Expense expense;

        public ExpenseWithCommand(Command type, Expense expense) {
            this.type = type;
            this.expense = expense;
        }
    }

    private enum Command{
        INSERT, UPDATE, DELETE
    }
}
