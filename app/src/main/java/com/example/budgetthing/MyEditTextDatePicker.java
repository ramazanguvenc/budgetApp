package com.example.budgetthing;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.budgetthing.models.Expense;

import java.util.Calendar;
import java.util.TimeZone;

public class MyEditTextDatePicker  implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private EditText _editText;
    private int _day;
    private int _month;
    private int _birthYear;
    private Context _context;
    private Calendar calendar;
    private Expense expense;

    public MyEditTextDatePicker(Context context, int editTextViewID, Expense expense)
    {
        Activity act = (Activity)context;
        this._editText = act.findViewById(editTextViewID);
        this._editText.setOnClickListener(this);
        this._context = context;
        calendar = Calendar.getInstance(TimeZone.getDefault());
        _day = calendar.get(Calendar.DAY_OF_MONTH);
        _month = calendar.get(Calendar.MONTH);
        _birthYear = calendar.get(Calendar.YEAR);
        this.expense = expense;
        updateDisplay();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        _birthYear = year;
        _month = monthOfYear;
        _day = dayOfMonth;
        updateDisplay();
    }
    @Override
    public void onClick(View v) {

        DatePickerDialog dialog = new DatePickerDialog(_context, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        dialog.getDatePicker().setBackgroundColor(R.color.colorGreen);
        dialog.show();

    }

    // updates the date in the birth date EditText
    private void updateDisplay() {

        _editText.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(_day).append("/").append(_month + 1).append("/").append(_birthYear).append(" "));
        expense.date = (_day + "/" + (_month + 1) + "/" + _birthYear + " ");

    }
}