package vn.edu.tdc.moneymanagement.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.time.LocalDate;

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.database.MyDatabase;
import vn.edu.tdc.moneymanagement.model.Util;

public class HomeFragment extends Fragment {
    private MyDatabase myDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.home_fragment, container, false);
        myDatabase = new MyDatabase(getContext());


        TextView textPrice = fragment.findViewById(R.id.textPrice);
        TextView textPriceBl = fragment.findViewById(R.id.textPriceBl);
        TextView textPriceUsed = fragment.findViewById(R.id.textPriceUsed);
        TextView textDanger = fragment.findViewById(R.id.textDanger);
        TextView prevMoney = fragment.findViewById(R.id.prev_money);

        long trongNgay = myDatabase.getTotalSpendingForDay(LocalDate.now());
        long soDu = myDatabase.getTotalMoneyForCurrentMonth() - myDatabase.getTotalFixedAccountForCurrentMonth();
        long hanMuc = soDu / 30;
        textPriceBl.setText(Util.formatNumber(hanMuc));
        textPriceUsed.setText(Util.formatNumber(trongNgay));

        long tong = myDatabase.getBalanceForPreviousMonth() + soDu - myDatabase.getTotalSpendingMoneyForCurrentMonth();
        textPrice.setText(Util.formatNumber(tong));
        if (myDatabase.getBalanceForPreviousMonth() > 0) {
            prevMoney.setText("Số dư tháng trước: + " + Util.formatNumber(myDatabase.getBalanceForPreviousMonth()));
        } else {
            prevMoney.setText("Số dư tháng trước: 0");
        }


        if (trongNgay > hanMuc) {
            textDanger.setVisibility(View.VISIBLE);
            textDanger.setText(R.string.notify);
        } else {
            textDanger.setVisibility(View.VISIBLE);
            textDanger.setText(R.string.notify_ok);
        }

        return fragment;
    }
}
