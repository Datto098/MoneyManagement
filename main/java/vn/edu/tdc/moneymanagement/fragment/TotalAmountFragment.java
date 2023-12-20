package vn.edu.tdc.moneymanagement.fragment;

import android.os.Bundle;
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
import vn.edu.tdc.moneymanagement.adapter.MoneyAdapter;
import vn.edu.tdc.moneymanagement.database.MyDatabase;
import vn.edu.tdc.moneymanagement.model.FixedAccount;
import vn.edu.tdc.moneymanagement.model.TotalMoney;
import vn.edu.tdc.moneymanagement.model.Util;

public class TotalAmountFragment extends Fragment {

    public static String currentTitle = "Tổng tiền";
    public static String prevTitle = "Tài khoản";
    private MyDatabase myDatabase;
    private ArrayList<TotalMoney> totalMonies;
    private MoneyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.total_amount_fragment, container, false);


        //innit
        myDatabase = new MyDatabase(getContext());

        //Lay item từ layout
        RecyclerView recyclerView = fragment.findViewById(R.id.recyclerViewTotal);
        AppCompatButton btnAdd = fragment.findViewById(R.id.btnAdd);
        AppCompatButton btnCancel = fragment.findViewById(R.id.btnCancel);
        AppCompatButton btnFindItem = fragment.findViewById(R.id.btnFindItem);
        AppCompatButton btnStartDay = fragment.findViewById(R.id.btnStartDay);
        AppCompatButton btnEndDay = fragment.findViewById(R.id.btnEndDay);
        TextView totalMoney = fragment.findViewById(R.id.totalMoney);

        totalMoney.setText(AccountFragment.formatNumber(myDatabase.getTotalMoneyForCurrentMonth()) + "");

        totalMonies = myDatabase.getAllTotalMoney();
        adapter = new MoneyAdapter(getContext(), totalMonies);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterMoneyFragment fragment2 = new EnterMoneyFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_view_tag, fragment2);
                transaction.addToBackStack("fragment_enter_money");
                // Đặt lại tiêu đề của Toolbar trong Activity
                if (getActivity() != null) {
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(EnterMoneyFragment.currentTitle);
                }
                transaction.commit();

                adapter.notifyDataSetChanged();
            }
        });

        btnFindItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDate startDay = Util.convertStringToDate(btnStartDay.getText().toString());
                LocalDate endDay = Util.convertStringToDate(btnEndDay.getText().toString());
                btnAdd.setVisibility(View.GONE);
                btnCancel.setVisibility(View.VISIBLE);
                totalMonies.clear();
                ArrayList<TotalMoney> newData = myDatabase.getTotalMoneyInDateRange(startDay, endDay);
                totalMonies.addAll(newData);

                adapter.notifyDataSetChanged();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAdd.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.GONE);
                totalMonies.clear();
                ArrayList<TotalMoney> newData = myDatabase.getAllTotalMoney();
                totalMonies.addAll(newData);

                adapter.notifyDataSetChanged();
            }
        });

        Util.getStartDayAndEndDay(fragment, btnStartDay, btnEndDay);
        return fragment;
    }
}
