package vn.edu.tdc.moneymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.model.ExpenseItem;


public class ListAdapter extends RecyclerView.Adapter {
    private ArrayList<ExpenseItem> items;
    private  LayoutInflater inflater;

    public ListAdapter(Context context, ArrayList<ExpenseItem> items) {
        this.inflater = LayoutInflater.from(context);
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 = (ViewHolder) holder;

        ArrayList<ExpenseItem> list = new ArrayList<>();

        ExpenseItem expenseItem =  items.get(position);
        String day = expenseItem.getDate().getDayOfMonth() + "";
        String monthAndYear = expenseItem.getDate().getMonthValue() + "/" + expenseItem.getDate().getYear();
        holder1.lblDay.setText(day);
        holder1.lblMonthAndYear.setText(monthAndYear);
        holder1.lblContent.setText(expenseItem.getContent());

        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedNumber = decimalFormat.format(expenseItem.getTotalMoney());

        holder1.lblMoney.setText( formattedNumber);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        LinearLayout linearItem;

        TextView lblDay;
        TextView lblMonthAndYear;
        TextView lblContent;
        TextView lblMoney;

        public ViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerviewParent);
            lblDay = itemView.findViewById(R.id.lblDay);
            lblMonthAndYear = itemView.findViewById(R.id.lblMonthAndYear);
            lblContent = itemView.findViewById(R.id.lblContent);
            lblMoney = itemView.findViewById(R.id.lblMoney);
            linearItem = itemView.findViewById(R.id.linearItem);
        }
    }
}


