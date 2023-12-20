package vn.edu.tdc.moneymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.model.ExpenseItem;

public class InnerListAdapter extends ArrayAdapter<ExpenseItem> {

    public InnerListAdapter(Context context, List<ExpenseItem> data) {
        super(context, R.layout.expenses_item_layout, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Implement how each item in the ListView should look
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(vn.edu.tdc.moneymanagement.R.layout.expenses_item_layout, parent, false);
        }

        // Set data to views within the expenses_item_layout
        return view;
    }
}