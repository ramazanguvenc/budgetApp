package com.example.budgetthing.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense_table")
public class Expense {



    @PrimaryKey(autoGenerate = true)
    private int uid;


    public String type;

    public String total;

    public String category;

    public String date;

    public Integer totalInt;


    public Expense(){

    }


    public Expense(String type, String cash, String category, String date){
        this.total = cash;
        this.category = category;
        this.type = type;
        this.date = date;
        this.totalInt = Integer.parseInt(total);
    }






    public void setUid(int id){
        this.uid = id;
    }

    public int getUid() {
        return uid;
    }

    public String getType() {
        return type;
    }

    public String getTotal() {
        return total;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public Integer getTotalInt() {
        return totalInt;
    }

    public static final String CASH_SIGN = "₺";

}
