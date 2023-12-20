package vn.edu.tdc.moneymanagement.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.adapter.ExpenseAdapter;

public class HistoryFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //List<String> fakedata = Arrays.asList("1000", "2000", "3000");

        View fragment = inflater.inflate(vn.edu.tdc.moneymanagement.R.layout.history_layout, container, false);
        RecyclerView expenseRecyclerView = fragment.findViewById(R.id.recyclerViewExpense);

//        // Create and set up your custom adapter
//        ExpenseAdapter expenseAdapter = new ExpenseAdapter(fakedata);
//        expenseRecyclerView.setAdapter(expenseAdapter);
//
//
//        // Set the layout manager
//        LinearLayoutManager layoutManager = new LinearLayoutManager(fragment.getContext());
//        expenseRecyclerView.setLayoutManager(layoutManager);

        return fragment;
    }
}
