package vn.edu.tdc.moneymanagement.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.viewmodel.CreationExtras;

import java.text.DecimalFormat;

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.database.MyDatabase;

public class AccountFragment extends Fragment {

    private View fragment;
    private MyDatabase myDatabase;

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

        fixedMoney.setText(formatNumber(myDatabase.getTotalFixedAccountForCurrentMonth()) + " đ");
        totalMoney.setText(formatNumber(myDatabase.getTotalMoneyForCurrentMonth()) + " đ");


        totalAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TotalAmountFragment fragment2 = new TotalAmountFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_view_tag, fragment2);
                transaction.addToBackStack("fragment_enter_money");
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
                transaction.commit();
            }
        });

        spendingAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpendingFragment fragment3 = new SpendingFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_view_tag, fragment3);
                transaction.addToBackStack("fragment_spending");
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

//    private long getTotalMoneyOfThisMonth(){
//        LocalDate localDate = LocalDate.now();
//        long total = 0;
//        for(FixedAccount fixedAccount : fixedAccounts){
//            if((fixedAccount.getDate().getMonthValue() == localDate.getMonthValue()) && (fixedAccount.getDate().getYear() == localDate.getYear())){
//                total = total + fixedAccount.getMoney();
//            }
//        }
//
//        return total;
//    }

    public static String formatNumber(long number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedNumber = decimalFormat.format(number);
        return formattedNumber;
    }
}
