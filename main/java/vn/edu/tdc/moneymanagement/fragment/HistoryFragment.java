package vn.edu.tdc.moneymanagement.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.adapter.HistoryAdapter;
import vn.edu.tdc.moneymanagement.database.MyDatabase;
import vn.edu.tdc.moneymanagement.model.SpendingAccount;
import vn.edu.tdc.moneymanagement.model.Util;

public class HistoryFragment extends Fragment {
    private MyDatabase myDatabase;
    private ArrayList<SpendingAccount> spendingAccounts;
    private HistoryAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragment = inflater.inflate(vn.edu.tdc.moneymanagement.R.layout.history_layout, container, false);
        AppCompatButton btnFindItem = fragment.findViewById(R.id.btnFindItem);
        AppCompatButton btnCancel = fragment.findViewById(R.id.btnCancel);
        AppCompatButton btnStartDay = fragment.findViewById(R.id.btnStartDay);
        AppCompatButton btnEndDay = fragment.findViewById(R.id.btnEndDay);
        RecyclerView expenseRecyclerView = fragment.findViewById(R.id.recyclerViewExpense);

        myDatabase = new MyDatabase(getContext());

        spendingAccounts = new ArrayList<>();
        spendingAccounts = myDatabase.getAllSpendingAccounts();

        for (SpendingAccount account : spendingAccounts
        ) {
            Log.d("object spending", account.toString());
        }

        adapter = new HistoryAdapter(getContext(), spendingAccounts);
        expenseRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        btnFindItem.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {

                if (Util.isValidDateFormat(btnStartDay.getText().toString()) && Util.isValidDateFormat(btnEndDay.getText().toString())) {
                    btnStartDay.setEnabled(false);
                    btnEndDay.setEnabled(false);
                    btnCancel.setVisibility(View.VISIBLE);
                    btnFindItem.setVisibility(View.GONE);
                    LocalDate startDay = Util.convertStringToDate(btnStartDay.getText().toString());
                    LocalDate endDay = Util.convertStringToDate(btnEndDay.getText().toString());

                    ArrayList<SpendingAccount> newData = myDatabase.getSpendingAccountsInDateRange(startDay, endDay);
                    spendingAccounts.clear();
                    spendingAccounts.addAll(newData);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStartDay.setEnabled(true);
                btnEndDay.setEnabled(true);
                btnCancel.setVisibility(View.GONE);
                btnFindItem.setVisibility(View.VISIBLE);
                ArrayList<SpendingAccount> newData = myDatabase.getAllSpendingAccounts();
                spendingAccounts.clear();
                spendingAccounts.addAll(newData);
                adapter.notifyDataSetChanged();
            }
        });

        Util.getStartDayAndEndDay(fragment, btnStartDay, btnEndDay);

        return fragment;
    }
}
