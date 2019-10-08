package com.example.budgetthing.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.budgetthing.models.Expense;

@Database(entities = {Expense.class}, exportSchema = false, version = 2)
public abstract class ExpenseDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "expense_db";
    private static ExpenseDatabase instance;

    public abstract ExpenseDao expenseDao();

    public static synchronized ExpenseDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ExpenseDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        ExpenseDao noteDao;

        private PopulateDbAsyncTask(ExpenseDatabase db){
            noteDao = db.expenseDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }



}
