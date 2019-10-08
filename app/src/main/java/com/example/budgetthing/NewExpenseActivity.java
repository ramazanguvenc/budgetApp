package com.example.budgetthing;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budgetthing.models.Expense;


public class NewExpenseActivity extends AppCompatActivity {

    public static final String EXTRA_TYPE = "com.example.budgetthing.EXTRA_TYPE";
    public static final String EXTRA_TOTAL = "com.example.budgetthing.EXTRA_TOTAL";
    public static final String EXTRA_DATE = "com.example.budgetthing.EXTRA_DATE";
    public static final String EXTRA_CATEGORY = "com.example.budgetthing.EXTRA_CATEGORY";
    Expense expense = new Expense();
    RadioGroup radioGroup;
    EditText amount_edit_text, category_edit_text;
    ImageButton check;
    ImageButton cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);
        category_edit_text = findViewById(R.id.category_edit_text);

        new MyEditTextDatePicker(this, R.id.date_picker, expense);
        new MyCategoryPicker(this,R.id.category_edit_text ,expense,  getResources().getStringArray(R.array.dialog_single_choice_array));
        radioGroup = findViewById(R.id.radio_group);
        amount_edit_text = findViewById(R.id.amount_edit_text);
        check = findViewById(R.id.check);
        cancel = findViewById(R.id.cancel);
        expense.type = "paid";
        expense.total = "Default";
        expense.category = "Default";


        setListeners();
    }

    private void setListeners() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.paid:
                        expense.type = "paid";
                        category_edit_text.setText("Not selected");
                        break;
                    case R.id.income:
                        expense.type = "income";
                        expense.category = "income";
                        category_edit_text.setText("income");
                        break;
                }
            }
        });

        amount_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                expense.total = editable.toString().split("₺")[1];
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(expense.total.equals("Default") || expense.category.equals("Default")){
                    //Do nothing
                }
                else{
                    if(expense.type.equals("income"))
                        expense.category = "income";
                    Intent data = new Intent();
                    data.putExtra(EXTRA_TYPE, expense.type);
                    data.putExtra(EXTRA_DATE, expense.date);
                    data.putExtra(EXTRA_TOTAL, expense.total);
                    data.putExtra(EXTRA_CATEGORY, expense.category);

                    setResult(RESULT_OK, data);
                    finish();
                }


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
    }
}
