package vn.edu.tdc.moneymanagement.adapter;

import android.annotation.SuppressLint;
import android.app.AppComponentFactory;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.fragment.EnterMoneyFragment;
import vn.edu.tdc.moneymanagement.model.TotalMoney;


public class MoneyAdapter extends RecyclerView.Adapter {
    Context context;
    private final ArrayList<TotalMoney> items;
    private final LayoutInflater inflater;

    public MoneyAdapter(Context context, ArrayList<TotalMoney> items) {
        this.inflater = LayoutInflater.from(context);
        this.items = items;
        this.context=context;
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
                FragmentTransaction transaction=((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_view_tag,fragment);
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
            EnterMoneyFragment.btnSelectDate.setText(date.getDayOfMonth() + "-" + date.getMonthValue() + "-" + date.getYear());
        }
    }

    private void setTotalMoney(TotalMoney totalMoney) {
        EnterMoneyFragment.edtMoney.setText(totalMoney.getMoney() + "");
        EnterMoneyFragment.edtContent.setText(totalMoney.getContent());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            EnterMoneyFragment.btnSelectDate.setText(totalMoney.getDate().getDayOfMonth() + "-" + totalMoney.getDate().getMonthValue() + "-" + totalMoney.getDate().getYear());
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


