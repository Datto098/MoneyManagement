package vn.edu.tdc.moneymanagement.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.adapter.ListAdapter;
import vn.edu.tdc.moneymanagement.adapter.MoneyAdapter;
import vn.edu.tdc.moneymanagement.database.MyDatabase;
import vn.edu.tdc.moneymanagement.model.FixedAccount;
import vn.edu.tdc.moneymanagement.model.Util;

public class FixedAccountFragment extends Fragment {

    private MyDatabase myDatabase;
    private ArrayList<FixedAccount> fixedAccounts;
    private ListAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragment = inflater.inflate(R.layout.fixed_amount_fragment, container, false);

        //innit
        myDatabase = new MyDatabase(getContext());


        fixedAccounts = new ArrayList<FixedAccount>();

        //Lay item từ layout
        RecyclerView recyclerView = fragment.findViewById(R.id.recyclerViewFixed);
        AppCompatButton btnAdd = fragment.findViewById(R.id.btnAdd);
        AppCompatButton btnStartDay = fragment.findViewById(R.id.btnStartDay);
        AppCompatButton btnEndDay = fragment.findViewById(R.id.btnEndDay);
        AppCompatButton btnFindItem = fragment.findViewById(R.id.btnFindItem);
        TextView fixedMoney = fragment.findViewById(R.id.fixedMoney);
        AppCompatButton btnCancel = fragment.findViewById(R.id.btnCancel);

        fixedMoney.setText(AccountFragment.formatNumber(myDatabase.getTotalFixedAccountForCurrentMonth()) + "");

        fixedAccounts = myDatabase.getAllFixedAccount();
        adapter = new ListAdapter(getContext(), fixedAccounts);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFixedAccount fragment = new AddFixedAccount();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_view_tag, fragment);
                transaction.addToBackStack("fragment_enter_money");
                transaction.commit();
            }
        });

        btnFindItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDate startDay = Util.convertStringToDate(btnStartDay.getText().toString());
                LocalDate endDay = Util.convertStringToDate(btnEndDay.getText().toString());

                btnAdd.setVisibility(View.GONE);
                btnCancel.setVisibility(View.VISIBLE);
                fixedAccounts.clear();
                ArrayList<FixedAccount> newData = myDatabase.getFixedAccountsInDateRange(startDay, endDay);
                fixedAccounts.addAll(newData);

                adapter.notifyDataSetChanged();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCancel.setVisibility(View.GONE);
                btnAdd.setVisibility(View.VISIBLE);
                fixedAccounts.clear();
                ArrayList<FixedAccount> newData = myDatabase.getAllFixedAccount();
                fixedAccounts.addAll(newData);
                adapter.notifyDataSetChanged();
            }
        });

        Util.getStartDayAndEndDay(fragment, btnStartDay, btnEndDay);
        return fragment;
    }
}
