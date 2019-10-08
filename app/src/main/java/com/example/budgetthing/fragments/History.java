package com.example.budgetthing.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetthing.MainActivity;
import com.example.budgetthing.R;
import com.example.budgetthing.adapters.ExpenseAdapter;
import com.example.budgetthing.models.Expense;
import com.example.budgetthing.viewmodel.ExpenseViewModel;

import java.util.List;

public class History extends Fragment {


    private ExpenseViewModel expenseViewModel;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.history, container, false);
        expenseViewModel = ViewModelProviders.of(this).get(ExpenseViewModel.class);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView_ExpenseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final ExpenseAdapter adapter = new ExpenseAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        adapter.setOnItemClickListener(new ExpenseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Expense note) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage(" Date: " + note.date
                + "\n Total: " + note.total
                + "\n Category: " + note.category
                + "\n Type: " + note.type);
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Okay",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });



                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        expenseViewModel.getAllExpenses().observe(this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenses) {
                adapter.submitList(expenses);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                ((MainActivity) getActivity()).setTotalSummary(adapter.getExpenseAt(viewHolder.getAdapterPosition()), false);
                expenseViewModel.delete(adapter.getExpenseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "Expense deleted.", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);

        return v;
    }


}
