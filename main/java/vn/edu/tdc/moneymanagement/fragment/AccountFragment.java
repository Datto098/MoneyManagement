package vn.edu.tdc.moneymanagement.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.viewmodel.CreationExtras;

import java.text.DecimalFormat;
import java.util.Calendar;

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.database.MyDatabase;

public class AccountFragment extends Fragment {

    public static String prevTitle = "Trang chủ";
    public static String currentTitle = "Tài khoản";
    private View fragment;
    private MyDatabase myDatabase;

    public static String formatNumber(long number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedNumber = decimalFormat.format(number);
        return formattedNumber;
    }


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragment = inflater.inflate(R.layout.account_fragment, container, false);
        LinearLayout totalAmount = fragment.findViewById(R.id.total_amount);
        LinearLayout fixedAmount = fragment.findViewById(R.id.fixed_amount);
        LinearLayout spendingAmount = fragment.findViewById(R.id.spending_amount);

        //innit
        myDatabase = new MyDatabase(getContext());

        //Get the total money edit text
        TextView totalMoney = fragment.findViewById(R.id.totalMoney);
        TextView fixedMoney = fragment.findViewById(R.id.fixedMoney);
        TextView spendingMoney = fragment.findViewById(R.id.spendingMoney);

        // Get date
        TextView dateTotalMoney = fragment.findViewById(R.id.dateTotalMoney);
        TextView dateFixedAmount = fragment.findViewById(R.id.dateFixedAmount);
        TextView dateSpending = fragment.findViewById(R.id.dateSpendingAmount);

        // Set date
        Calendar calendar = Calendar.getInstance();

        // Lấy tháng và năm
        int currentMonth = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cộng thêm 1
        int currentYear = calendar.get(Calendar.YEAR);


        dateTotalMoney.setText(currentMonth + "/" + currentYear);
        dateFixedAmount.setText(currentMonth + "/" + currentYear);
        dateSpending.setText(currentMonth + "/" + currentYear);

        long total = myDatabase.getTotalMoneyForCurrentMonth();
        long fixed = myDatabase.getTotalFixedAccountForCurrentMonth();

        long balance = total - fixed;


        fixedMoney.setText(formatNumber(myDatabase.getTotalFixedAccountForCurrentMonth()) + " đ");
        totalMoney.setText(formatNumber(myDatabase.getTotalMoneyForCurrentMonth()) + " đ");

        spendingMoney.setText(formatNumber(balance) + " đ");


        totalAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TotalAmountFragment fragment2 = new TotalAmountFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_view_tag, fragment2);
                transaction.addToBackStack("fragment_enter_money");
                // Đặt lại tiêu đề của Toolbar trong Activity
                if (getActivity() != null) {
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(TotalAmountFragment.currentTitle);
                }
                transaction.commit();

            }
        });

        fixedAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FixedAccountFragment fragment = new FixedAccountFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_view_tag, fragment);
                transaction.addToBackStack("fragment_fixed_account");
                // Đặt lại tiêu đề của Toolbar trong Activity
                if (getActivity() != null) {
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(FixedAccountFragment.currentTitle);
                }
                transaction.commit();
            }
        });

        spendingAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpensesFragment fragment3 = new ExpensesFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_view_tag, fragment3);
                transaction.addToBackStack("fragment_spending");
                // Đặt lại tiêu đề của Toolbar trong Activity
                if (getActivity() != null) {
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(ExpensesFragment.currentTitle);
                }
                transaction.commit();
            }
        });
        return fragment;
    }


    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}
