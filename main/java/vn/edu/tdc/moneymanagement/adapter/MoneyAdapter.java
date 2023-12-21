package vn.edu.tdc.moneymanagement.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.fragment.EnterMoneyFragment;
import vn.edu.tdc.moneymanagement.model.TotalMoney;


public class MoneyAdapter extends RecyclerView.Adapter {

    private final ArrayList<TotalMoney> items;
    private final LayoutInflater inflater;
    Context context;

    public MoneyAdapter(Context context, ArrayList<TotalMoney> items) {
        this.inflater = LayoutInflater.from(context);
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 = (ViewHolder) holder;

        TotalMoney totalMoney = items.get(position);
        String day = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            day = totalMoney.getDate().getDayOfMonth() + "";
        }
        String monthAndYear = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            monthAndYear = totalMoney.getDate().getMonthValue() + "/" + totalMoney.getDate().getYear();
        }
        holder1.lblDay.setText(day);
        holder1.lblMonthAndYear.setText(monthAndYear);
        holder1.lblContent.setText(totalMoney.getContent());

        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedNumber = decimalFormat.format(totalMoney.getMoney());

        holder1.lblMoney.setText(formattedNumber);
        int i = position;
        Log.d("position", position + "");

        holder1.linearItem.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                EnterMoneyFragment fragment = new EnterMoneyFragment(totalMoney);
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_view_tag, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void clearAll() {
        EnterMoneyFragment.edtMoney.setText("");
        EnterMoneyFragment.edtContent.setText("");
        LocalDate date = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date = LocalDate.now();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int dayOfMonth = date.getDayOfMonth();
            int monthValue = date.getMonthValue();
            int year = date.getYear();

            // Kiểm tra và thêm số 0 nếu cần
            String dayString = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
            String monthString = (monthValue < 10) ? "0" + monthValue : String.valueOf(monthValue);

            // Sử dụng String.format để định dạng chuỗi
            String formattedDate = String.format("%s-%s-%d", dayString, monthString, year);

            // Đặt giá trị vào TextView
            EnterMoneyFragment.btnSelectDate.setText(formattedDate);
        }
    }

    private void setTotalMoney(TotalMoney totalMoney) {
        EnterMoneyFragment.edtMoney.setText(totalMoney.getMoney() + "");
        EnterMoneyFragment.edtContent.setText(totalMoney.getContent());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            LocalDate totalMoneyDate = totalMoney.getDate();
            int dayOfMonth = totalMoneyDate.getDayOfMonth();
            int monthValue = totalMoneyDate.getMonthValue();
            int year = totalMoneyDate.getYear();

            // Kiểm tra và thêm số 0 nếu cần
            String dayString = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
            String monthString = (monthValue < 10) ? "0" + monthValue : String.valueOf(monthValue);

            // Sử dụng String.format để định dạng chuỗi
            String formattedDate = String.format("%s-%s-%d", dayString, monthString, year);

            // Đặt giá trị vào TextView
            EnterMoneyFragment.btnSelectDate.setText(formattedDate);

        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearItem;
        TextView lblDay;
        TextView lblMonthAndYear;
        TextView lblContent;
        TextView lblMoney;

        public ViewHolder(View itemView) {
            super(itemView);
            lblDay = itemView.findViewById(R.id.lblDay);
            lblMonthAndYear = itemView.findViewById(R.id.lblMonthAndYear);
            lblContent = itemView.findViewById(R.id.lblContent);
            lblMoney = itemView.findViewById(R.id.lblMoney);
            linearItem = itemView.findViewById(R.id.linearItem);
        }
    }
}


