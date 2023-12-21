package vn.edu.tdc.moneymanagement.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.adapter.SpendingAdapter;
import vn.edu.tdc.moneymanagement.database.MyDatabase;
import vn.edu.tdc.moneymanagement.model.SpendingAccount;
import vn.edu.tdc.moneymanagement.model.Util;

public class ExpensesFragment extends Fragment {


    public static String currentTitle = "Các khoản chi tiêu";
    public static String prevTitle = "Tài khoản";
    private ArrayList<SpendingAccount> spendingAccounts;
    private MyDatabase myDatabase;
    private SpendingAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(vn.edu.tdc.moneymanagement.R.layout.expense_fragment, container, false);

        //innit database
        myDatabase = new MyDatabase(getContext());

        spendingAccounts = new ArrayList<>();
        spendingAccounts = myDatabase.getAllSpendingAccounts();
        Log.d("test", spendingAccounts.size() + "");

        RecyclerView expenseRecyclerView = fragment.findViewById(R.id.recyclerViewExpense);
        AppCompatButton btnAdd = fragment.findViewById(R.id.btnAdd);
        TextView totalSpending = fragment.findViewById(R.id.totalSpending);
        TextView totalSurplus = fragment.findViewById(R.id.totalSurplus);


        LocalDate dateNow = LocalDate.now();
        long totalOfDay = myDatabase.getTotalSpendingForDay(dateNow);
        String totalSpendingInDay = Util.formatNumber(totalOfDay);
        totalSpending.setText(totalSpendingInDay + " đ");

        long total = myDatabase.getTotalMoneyForCurrentMonth() - myDatabase.getTotalFixedAccountForCurrentMonth() - myDatabase.getTotalSpendingMoneyForCurrentMonth();

        totalSurplus.setText(Util.formatNumber(total) + " đ");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddSpendingFragment fragment = new AddSpendingFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_view_tag, fragment);
                transaction.addToBackStack("fragment_spending_2");
                // Đặt lại tiêu đề của Toolbar trong Activity
                if (getActivity() != null) {
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(AddSpendingFragment.currentTitle);
                }
                transaction.commit();
            }
        });


        adapter = new SpendingAdapter(getContext(), spendingAccounts);
        expenseRecyclerView.setAdapter(adapter);


        return fragment;
    }
}
