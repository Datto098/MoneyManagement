package vn.edu.tdc.moneymanagement.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.adapter.ExpenseAdapter;

public class SpendingFragment extends Fragment {
    public static String currentTitle = "Các khoản chi tiêu";
    public static String prevTitle = "Tài khoản";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        List<String> fakeData = Arrays.asList("1000", "2000", "3000");

        View fragment = inflater.inflate(R.layout.expense_fragment, container, false);
        RecyclerView expenseRecyclerView = fragment.findViewById(R.id.recyclerViewExpense);
        // Create and set up your custom adapter
        ExpenseAdapter expenseAdapter = new ExpenseAdapter(fakeData);
        expenseRecyclerView.setAdapter(expenseAdapter);

        //Get Button add spending
        AppCompatButton btnAddSpending = fragment.findViewById(R.id.btnAdd);
        AppCompatButton btnDeleteSpending = fragment.findViewById(R.id.btnDelete);
        AppCompatButton btnUpdateSpending = fragment.findViewById(R.id.btnUpdate);

        btnAddSpending.setOnClickListener(new View.OnClickListener() {
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

        // Set the layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(fragment.getContext());
        expenseRecyclerView.setLayoutManager(layoutManager);

        return fragment;
    }
}
