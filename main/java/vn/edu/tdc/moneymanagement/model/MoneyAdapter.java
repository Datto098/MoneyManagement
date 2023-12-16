package vn.edu.tdc.moneymanagement.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.tdc.edu.moneymanagement.R;

public class MoneyAdapter extends RecyclerView.Adapter<MoneyAdapter.ViewHolder> {

    private final List<String> data;

    public MoneyAdapter(List<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflow_monney_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = data.get(position);

        // Use the custom adapter for the ListView
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ListView expenseListView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            expenseListView = itemView.findViewById(R.id.listViewExpense);
        }
    }
}
