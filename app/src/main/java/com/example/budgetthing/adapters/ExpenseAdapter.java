package com.example.budgetthing.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetthing.R;
import com.example.budgetthing.models.Expense;

public class ExpenseAdapter extends ListAdapter<Expense, ExpenseAdapter.ExpenseHolder> {

    private OnItemClickListener listener;
    private Context mContext;


    public ExpenseAdapter() {
        super(DIFF_CALLBACK);
    }



    @NonNull
    @Override
    public ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_item, parent, false);



        return new ExpenseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseHolder holder, int position) {
        Expense currentExpense = getItem(position);
        String total = currentExpense.getTotal() + Expense.CASH_SIGN;
        holder.textViewTotal.setText(total);
        holder.textViewDate.setText(currentExpense.getDate());
        if(currentExpense.getType().equals("income")){
            //green
            holder.textViewTotal.setTextColor(Color.parseColor("#008000"));
        }
        else{
            //red
            holder.textViewTotal.setTextColor(Color.parseColor("#d30412"));
        }

    }

    public Expense getExpenseAt(int position){
        return getItem(position);
    }

    class ExpenseHolder extends RecyclerView.ViewHolder {
        private TextView textViewTotal;
        private TextView textViewDate;

        ExpenseHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.row_date);
            textViewTotal = itemView.findViewById(R.id.row_total);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if(listener != null && position != RecyclerView.NO_POSITION){
                    listener.onItemClick(getItem(position));

                }
            });
        }
    }

    private static final DiffUtil.ItemCallback<Expense> DIFF_CALLBACK = new DiffUtil.ItemCallback<Expense>() {
        @Override
        public boolean areItemsTheSame(@NonNull Expense oldItem, @NonNull Expense newItem) {
            return oldItem.getUid() == newItem.getUid();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Expense oldItem, @NonNull Expense newItem) {
            return oldItem.getDate().equals(newItem.getDate())
                    && oldItem.getCategory().equals(newItem.getCategory())
                    && oldItem.getTotal().equals(newItem.getTotal())
                    && oldItem.getType().equals(newItem.getType());
        }
    };

    public interface OnItemClickListener{
        void onItemClick(Expense note);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
