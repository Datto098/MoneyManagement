package vn.edu.tdc.moneymanagement.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.tdc.moneymanagement.ListAdapter;
import vn.edu.tdc.moneymanagement.R;

public class AccountFragment extends Fragment {

    private View fragment;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        fragment = inflater.inflate(R.layout.account_fragment, container, false);
        LinearLayout totalAmount = fragment.findViewById(R.id.total_amount);
        LinearLayout fixedAmount = fragment.findViewById(R.id.fixed_amount);
        LinearLayout spendingAmount = fragment.findViewById(R.id.spending_amount);

        totalAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterMoneyFragment fragment2 = new EnterMoneyFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_view_tag, fragment2);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        fixedAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFixedAccount fragment2 = new AddFixedAccount();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_view_tag, fragment2);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        spendingAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFixedAccount fragment2 = new AddFixedAccount();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_view_tag, fragment2);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // Create and set up your custom adapter


        return fragment;
    }

//    public void onLinearLayoutClick(View view) {
//        Log.d("test", "onClick");
//    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}