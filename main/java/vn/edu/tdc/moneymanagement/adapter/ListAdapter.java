package vn.edu.tdc.moneymanagement.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.fragment.AddFixedAccount;
import vn.edu.tdc.moneymanagement.model.FixedAccount;


public class ListAdapter extends RecyclerView.Adapter {
    public static int selectedRow = -1;
    private final ArrayList<FixedAccount> items;
    private final LayoutInflater inflater;
    private int backColor;
    private View prev;

    public ListAdapter(Context context, ArrayList<FixedAccount> items) {
        this.inflater = LayoutInflater.from(context);
        this.items = items;
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

        FixedAccount fixed = items.get(position);
        String day = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            day = fixed.getDate().getDayOfMonth() + "";
        }
        String monthAndYear = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            monthAndYear = fixed.getDate().getMonthValue() + "/" + fixed.getDate().getYear();
        }
        holder1.lblDay.setText(day);
        holder1.lblMonthAndYear.setText(monthAndYear);
        holder1.lblContent.setText(fixed.getContent());

        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedNumber = decimalFormat.format(fixed.getMoney());

        holder1.lblMoney.setText(formattedNumber);
        int i = position;

        holder1.linearItem.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                FixedAccount fixed = items.get(i);
                if (selectedRow == -1) {
                    backColor = ((ColorDrawable) view.getBackground()).getColor();
                    view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.clicked));
                    selectedRow = i;
                    prev = view;
                    setFixedAccount(fixed);
                    AddFixedAccount.btnAdd.setEnabled(false);
                } else {
                    if (selectedRow == i) {
                        view.setBackgroundColor(backColor);
                        selectedRow = -1;
                        clearAll();
                        AddFixedAccount.btnAdd.setEnabled(true);
                    } else {
                        prev.setBackgroundColor(backColor);
                        view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.clicked));
                        prev = view;
                        selectedRow = i;
                        clearAll();
                        setFixedAccount(fixed);
                        AddFixedAccount.btnAdd.setEnabled(false);
                    }
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void clearAll() {
        AddFixedAccount.edtMoney.setText("");
        AddFixedAccount.edtContent.setText("");
        LocalDate date = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date = LocalDate.now();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AddFixedAccount.btnSelectDate.setText(date.getDayOfMonth() + "-" + date.getMonthValue() + "-" + date.getYear());
        }
    }

    private void setFixedAccount(FixedAccount fixed) {
        AddFixedAccount.edtMoney.setText(fixed.getMoney() + "");
        AddFixedAccount.edtContent.setText(fixed.getContent());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AddFixedAccount.btnSelectDate.setText(fixed.getDate().getDayOfMonth() + "-" + fixed.getDate().getMonthValue() + "-" + fixed.getDate().getYear());
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


