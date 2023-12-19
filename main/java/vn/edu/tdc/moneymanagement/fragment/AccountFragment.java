package vn.edu.tdc.moneymanagement.fragment;

import android.os.Bundle;
import android.util.Log;
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
import java.time.LocalDate;
import java.util.ArrayList;

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.database.MyDatabaseAPIs;
import vn.edu.tdc.moneymanagement.model.FixedAccount;

public class AccountFragment extends Fragment {

    private View fragment;
    private MyDatabaseAPIs databaseAPIs;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //innit
        databaseAPIs = new MyDatabaseAPIs(getContext());

        //Thuc hien  su ly uy quyen
        databaseAPIs.setCompleteListener(new MyDatabaseAPIs.CompleteListener() {
            @Override
            public void notifyToActivity(int notificationID) {
                if (notificationID == MyDatabaseAPIs.GET_ALL_DONE) {

                }
            }
        });

        fragment = inflater.inflate(R.layout.account_fragment, container, false);
        LinearLayout totalAmount = fragment.findViewById(R.id.total_amount);
        LinearLayout fixedAmount = fragment.findViewById(R.id.fixed_amount);
        LinearLayout spendingAmount = fragment.findViewById(R.id.spending_amount);

        //Get the total money edit text
        TextView totalMoney = fragment.findViewById(R.id.totalMoney);
        TextView fixedMoney = fragment.findViewById(R.id.fixedMoney);
        TextView spendingMoney = fragment.findViewById(R.id.spendingMoney);

        fixedMoney.setText(formatNumber(databaseAPIs.getTotalMoneyThisMonth()) + " đ");


        totalAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterMoneyFragment fragment2 = new EnterMoneyFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_view_tag, fragment2);
                transaction.addToBackStack("enter_money_fragment");
                transaction.commit();

            }
        });

        fixedAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFixedAccount fragment2 = new AddFixedAccount();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_view_tag, fragment2);
                transaction.addToBackStack("fixed_account_fragment");
                transaction.commit();
            }
        });

        spendingAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpendingFragment fragment3 = new SpendingFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_view_tag, fragment3);
                transaction.addToBackStack("spending_fragment");
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

    private String formatNumber(long number){
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedNumber = decimalFormat.format(number);
        return formattedNumber;
    }
}
