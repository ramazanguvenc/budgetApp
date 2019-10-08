package com.example.budgetthing;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.example.budgetthing.models.Expense;

public class MyCategoryPicker implements View.OnClickListener {
    private Context _context;
    private EditText _editText;
    private String[] singleChoiceItems;
    private Expense expense;

    public MyCategoryPicker(Context context, int editTextViewID , Expense expense, String [] arr) {
        Activity act = (Activity) context;
        this._editText = act.findViewById(editTextViewID);
        this._editText.setOnClickListener(this);
        this._context = context;
        this.singleChoiceItems = arr;
        this.expense = expense;
    }

    @Override
    public void onClick(View v){
        int itemSelected = 0;
        new AlertDialog.Builder(_context)
                .setTitle("Select category")
                .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                        updateEditText(singleChoiceItems[selectedIndex]);
                        expense.category = singleChoiceItems[selectedIndex];
                    }
                })
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void updateEditText(String selected) {
        _editText.setText(selected);
        expense.category = selected;
    }
}
